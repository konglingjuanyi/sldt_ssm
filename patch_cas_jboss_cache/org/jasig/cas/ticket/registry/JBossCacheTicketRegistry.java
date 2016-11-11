/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.ticket.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jasig.cas.ticket.Ticket;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheException;
import org.jboss.cache.Node;

import javax.validation.constraints.NotNull;

/**
 * Implementation of TicketRegistry that is backed by a JBoss TreeCache.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 *
 */
public final class JBossCacheTicketRegistry extends AbstractDistributedTicketRegistry {
    
    /** Indicator of what tree branch to put tickets in. */
    private static final String FQN_TICKET = "ticket";

    /** Instance of JBoss TreeCache. */
    @NotNull
    private Cache<String, Ticket> cache;
    //zzg 2013-11-15 cme错误重试次数
    private static final int cmeRetryCnt=3;
    
    //zzg 2013-11-15
    //cas 颁发ST的时候，可能遇到并发错误，导致集群模式写入错误，需要重试3次兼容该类错误。
    protected void updateTicket(Ticket ticket) {
    
    	for(int i=0;i<cmeRetryCnt;i++){
    		boolean isNeedRetry=false;
	    	try{
	    		_updateTicket(ticket);
	    		isNeedRetry=false;
	    	}catch(java.util.ConcurrentModificationException cme){//zzg 增加对并发冲突的错误扑获和重试逻辑实现
	        	cme.printStackTrace();
	        	logger.info("[ssm_bug_cme_20131115_001_updateTicket]:"+i+"/"+cmeRetryCnt+":"+cme.getMessage());
	        	isNeedRetry=true;
	        }
	    	if(!isNeedRetry){//无需重试,则跳出循环
	    		break;
	    	}else{
	    		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
    	}
    }
    //zzg 2013-11-15
    //cas 并发ST的时候，可能遇到并发错误，导致集群模式写入错误，需要重试3次兼容该类错误。
    public void addTicket(final Ticket ticket) {
    	for(int i=0;i<cmeRetryCnt;i++){
    		boolean isNeedRetry=false;
	    	try{
	    		_addTicket(ticket);
	    		isNeedRetry=false;
	    	}catch(java.util.ConcurrentModificationException cme){//zzg 增加对并发冲突的错误扑获和重试逻辑实现
	        	cme.printStackTrace();
	        	logger.info("[ssm_bug_cme_20131115_001_addTicket]:"+i+"/"+cmeRetryCnt+":"+cme.getMessage());
	        	isNeedRetry=true;
	        }
	    	if(!isNeedRetry){//无需重试,则跳出循环
	    		break;
	    	}else{
	    		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
    	}
    }
    //zzg 2013-11-15
    //cas 颁发ST的时候，可能遇到并发错误，导致集群模式写入错误，需要重试3次兼容该类错误。
    public boolean deleteTicket(final String ticketId) {
    	boolean retVal=false;
    	for(int i=0;i<cmeRetryCnt;i++){
    		boolean isNeedRetry=false;
	    	try{
	    		retVal=_deleteTicket(ticketId);
	    		isNeedRetry=false;
	    	}catch(java.util.ConcurrentModificationException cme){//zzg 增加对并发冲突的错误扑获和重试逻辑实现
	        	cme.printStackTrace();
	        	logger.info("[ssm_bug_cme_20131115_001_deleteTicket]:"+i+"/"+cmeRetryCnt+":"+cme.getMessage());
	        	isNeedRetry=true;
	        }
	    	if(!isNeedRetry){//无需重试,则跳出循环
	    		break;
	    	}else{
	    		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
    	}
    	return retVal;
    }
    //zzg 2013-11-15 保留原声方法实现
    protected void _updateTicket(Ticket ticket) {
        try {
            this.cache.put(FQN_TICKET, ticket.getId(), ticket);
        } catch (final CacheException e) {
            throw new RuntimeException(e);
        } 
    }

    public void _addTicket(final Ticket ticket) {
        try {
            if (logger.isDebugEnabled()){
                logger.debug("Adding ticket to registry for: " + ticket.getId());
            }
            this.cache.put(FQN_TICKET, ticket.getId(), ticket);
        } catch (final CacheException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public boolean _deleteTicket(final String ticketId) {
        try {
            if (logger.isDebugEnabled()){
                logger.debug("Removing ticket from registry for: " + ticketId);
            }
            return this.cache.remove(FQN_TICKET, ticketId) != null;
        } catch (final CacheException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Returns a proxied instance.
     * 
     * @see org.jasig.cas.ticket.registry.TicketRegistry#getTicket(java.lang.String)
     */
    public Ticket getTicket(final String ticketId) {
        try {
            if (logger.isDebugEnabled()){
                logger.debug("Retrieving ticket from registry for: " + ticketId);
            }
            return getProxiedTicketInstance(this.cache.get(FQN_TICKET, ticketId));
        } catch (final CacheException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Collection<Ticket> getTickets() {
        try {
            final Node<String, Ticket> node = this.cache.getNode(FQN_TICKET);

            if (node == null) {
                return Collections.emptyList();
            }
            
            final Set<String> keys = node.getKeys();
            final List<Ticket> list = new ArrayList<Ticket>();

            for (final String key : keys) {
                list.add(node.get(key));
            }

            return list;
        } catch (final CacheException e) {
            return Collections.emptyList();
        }
    }

    public void setCache(final Cache<String, Ticket> cache) {
        this.cache = cache;
    }

    @Override
    protected boolean needsCallback() {
        return true;
    }
}
