/*
  Copyright 2012 - 2013 Jerome Leleu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.hzbanksso.client;

import java.util.Timer;
import java.util.TimerTask;

import com.hzbank.sso.client.proxy.CleanUpTimerTask;
import com.hzbank.sso.client.proxy.ProxyGrantingTokenStorage;
import com.hzbank.sso.client.proxy.ProxyGrantingTokenStorageImpl;
import com.hzbank.sso.client.util.CommonUtils;
import org.pac4j.hzbanksso.credentials.CasCredentials;
import org.pac4j.hzbanksso.profile.CasProfile;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Protocol;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the CAS proxy receptor.
 * <p />
 * The url of the proxy receptor must be defined through the {@link #setCallbackUrl(String)} method, it's the <code>proxyReceptorUrl</code>
 * concept of the Jasig CAS client.
 * <p />
 * The proxy granting tickets and associations are stored by default in a {@link ProxyGrantingTokenStorageImpl} class, which can be
 * overriden by using the {@link #setProxyGrantingTokenStorage(ProxyGrantingTokenStorage)} method.
 * <p />
 * By default, the tickets and associations are cleaned every minute. The <code>millisBetweenCleanUps</code> property can be defined through
 * the {@link #setMillisBetweenCleanUps(int)} method (0 means no cleanup, greater than 0 means a cleanup every
 * <code>millisBetweenCleanUps</code> milli-seconds).
 * 
 * @author Jerome Leleu
 * @since 1.4.0
 */
public final class CasProxyReceptor extends BaseClient<CasCredentials, CasProfile> {
    
    private static final Logger logger = LoggerFactory.getLogger(CasProxyReceptor.class);
    
    private ProxyGrantingTokenStorage proxyGrantingTokenStorage = new ProxyGrantingTokenStorageImpl();
    
    public static final String PARAM_PROXY_GRANTING_TICKET_IOU = "pgtIou";
    
    public static final String PARAM_PROXY_GRANTING_TICKET = "pgtId";
    
    private int millisBetweenCleanUps = 60000;
    
    private Timer timer;
    
    private TimerTask timerTask;
    
    @Override
    protected BaseClient<CasCredentials, CasProfile> newClient() {
        final CasProxyReceptor casProxyReceptor = new CasProxyReceptor();
        casProxyReceptor.setProxyGrantingTokenStorage(this.proxyGrantingTokenStorage);
        casProxyReceptor.setMillisBetweenCleanUps(this.millisBetweenCleanUps);
        return casProxyReceptor;
    }
    
    @Override
    protected void internalInit() {
        CommonHelper.assertNotBlank("callbackUrl", this.callbackUrl);
        CommonHelper.assertNotNull("proxyGrantingTokenStorage", this.proxyGrantingTokenStorage);
        // timer to clean proxyGrantingTokenStorage
        if (this.millisBetweenCleanUps > 0) {
            if (this.timer == null) {
                this.timer = new Timer(true);
            }
            
            if (this.timerTask == null) {
                this.timerTask = new CleanUpTimerTask(this.proxyGrantingTokenStorage);
            }
            this.timer.schedule(this.timerTask, this.millisBetweenCleanUps, this.millisBetweenCleanUps);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CasCredentials retrieveCredentials(final WebContext context) throws
        RequiresHttpAction {
        
        // like CommonUtils.readAndRespondToProxyReceptorRequest in CAS client
        final String proxyGrantingTokenIou = context.getRequestParameter(PARAM_PROXY_GRANTING_TICKET_IOU);
        logger.debug("proxyGrantingTokenIou : {}", proxyGrantingTokenIou);
        final String proxyGrantingToken = context.getRequestParameter(PARAM_PROXY_GRANTING_TICKET);
        logger.debug("proxyGrantingToken : {}", proxyGrantingToken);
        
        if (CommonUtils.isBlank(proxyGrantingToken) || CommonUtils.isBlank(proxyGrantingTokenIou)) {
            context.writeResponseContent("");
            final String message = "Missing proxyGrantingToken or proxyGrantingTokenIou";
            logger.error(message);
            throw RequiresHttpAction.ok(message, context);
        }
        
        this.proxyGrantingTokenStorage.save(proxyGrantingTokenIou, proxyGrantingToken);
        
        context.writeResponseContent("<?xml version=\"1.0\"?>");
        context.writeResponseContent("<casClient:proxySuccess xmlns:casClient=\"http://www.yale.edu/tp/casClient\" />");
        
        final String message = "No credential for CAS proxy receptor -> returns ok";
        logger.debug(message);
        throw RequiresHttpAction.ok(message, context);
    }
    
    public ProxyGrantingTokenStorage getProxyGrantingTokenStorage() {
        return this.proxyGrantingTokenStorage;
    }
    
    public void setProxyGrantingTokenStorage(final ProxyGrantingTokenStorage proxyGrantingTokenStorage) {
        this.proxyGrantingTokenStorage = proxyGrantingTokenStorage;
    }
    
    public int getMillisBetweenCleanUps() {
        return this.millisBetweenCleanUps;
    }
    
    public void setMillisBetweenCleanUps(final int millisBetweenCleanUps) {
        this.millisBetweenCleanUps = millisBetweenCleanUps;
    }
    
    @Override
    public String toString() {
        return CommonHelper.toString(this.getClass(), "callbackUrl", this.callbackUrl, "proxyGrantingTokenStorage",
                                     this.proxyGrantingTokenStorage, "millisBetweenCleanUps",
                                     this.millisBetweenCleanUps);
    }
    
    /**
     * {@inheritDoc}
     */
    protected String retrieveRedirectionUrl(final WebContext context) {
        throw new TechnicalException("Not supported by the CAS proxy receptor");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CasProfile retrieveUserProfile(final CasCredentials credentials) {
        throw new TechnicalException("Not supported by the CAS proxy receptor");
    }
    
    @Override
    protected boolean isDirectRedirection() {
        return true;
    }
    
    @Override
    public Protocol getProtocol() {
        return Protocol.CAS;
    }
}
