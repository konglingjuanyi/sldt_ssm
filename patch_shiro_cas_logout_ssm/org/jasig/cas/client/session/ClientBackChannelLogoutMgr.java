package org.jasig.cas.client.session;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.logout.LogoutManager;
import org.jasig.cas.logout.LogoutRequest;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.pac4j.core.client.Clients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientBackChannelLogoutMgr {
	/**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ClientBackChannelLogoutMgr.class);
    
    
    
    @NotNull
    private final LogoutManager logoutManager;

    /** CookieGenerator for TGT Cookie. */
    @NotNull
    private final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    /** CookieGenerator for Warn Cookie. */
    @NotNull
    private final CookieRetrievingCookieGenerator warnCookieGenerator;


    @NotNull
    private final TicketRegistry ticketRegistry;

    /** New Ticket Registry for storing and retrieving services tickets. Can point to the same one as the ticketRegistry variable. */
    @NotNull
    private final TicketRegistry serviceTicketRegistry;

    
    @NotNull
    private final ServicesManager servicesManager;

   /**
     * The clients used for authentication.
     */
    @NotNull
    private final Clients clients;

    /**
     * The service for CAS authentication.
     */
    @NotNull
    private final CentralAuthenticationService centralAuthenticationService;


    public ClientBackChannelLogoutMgr(
    		final CentralAuthenticationService theCentralAuthenticationService,
            final Clients theClients,
            final CookieRetrievingCookieGenerator tgtCookieGenerator,
            final CookieRetrievingCookieGenerator warnCookieGenerator,
            final TicketRegistry ticketRegistry,
            final TicketRegistry serviceTicketRegistry,
            final ServicesManager servicesManager,
            final LogoutManager logoutManager
     		) {
        this.centralAuthenticationService = theCentralAuthenticationService;
        this.clients = theClients;
        this.ticketGrantingTicketCookieGenerator = tgtCookieGenerator;
        this.warnCookieGenerator = warnCookieGenerator;
        this.ticketRegistry = ticketRegistry;
        this.logoutManager = logoutManager;
        
        if (serviceTicketRegistry == null) {
            this.serviceTicketRegistry = ticketRegistry;
        } else {
            this.serviceTicketRegistry = serviceTicketRegistry;
        }
          this.servicesManager = servicesManager;
    }
  
    public void doCasClientLogout(final String token ){
  		
    	String clientName ="hzbankcas";
    	
    	logger.debug("destroy TGT with an external ST: "+token);  
		
		if (CommonUtils.isNotBlank(token)) {
            
			Collection<Ticket> ticketCollection = this.ticketRegistry.getTickets();
			logger.debug("CAS ticketCollection.size: "+ticketCollection.size());
			
			
			 
			    for (Ticket ticket : ticketCollection) {
			    	
			    	if( ticket instanceof TicketGrantingTicket){
			    		
			    		TicketGrantingTicket ticketGrantingTicket = (TicketGrantingTicket)ticket;
			    	   	String tgtId = ticketGrantingTicket.getId();
			    	   	logger.debug("check for ticket.id: "+tgtId );
			    	
			    	   	org.jasig.cas.authentication.Authentication authentication 
			    	   	= ticketGrantingTicket.getAuthentication();
			    	   	
			    	   		if(authentication!=null){
			    	   			if(authentication.getAttributes()!=null){
				    	   			String clientNameStored = safeGetAttribute(authentication,"clientName");
				    	   			String clientTokenStored = safeGetAttribute(authentication,"clientToken");// authentication.getAttributes().get("clientToken").toString();
				    	   			
				    	   			if(clientName.equals(clientNameStored)&&token.equals(clientTokenStored)){
				    	   				logger.debug("client confirmed: "+clientName );
				    	   				logger.debug("client token confirmed: "+clientTokenStored );
			                            logger.debug("token confirmed for tgtId: "+tgtId);
	                        			    //should do some LT validation from remote server ?
	                        				//destroy the TGT and all his ST  !!! NOT WORKING
	                        				List<LogoutRequest> logoutRequests = 
	                        						this.centralAuthenticationService.destroyTicketGrantingTicket(tgtId);                         				
	                          
		                        
				    	   			}//end of if(clientName.equals(clientNameStored)&&token.equals(clientTokenStored))
			    	   			}
			    	   		}//end ofif(authentication!=null)
			    	}//end of if( ticket instanceof TicketGrantingTicket)
			    }//end of  for (Ticket ticket : ticketCollection)
        }//end of if (CommonUtils.isNotBlank(token)) 
    }
    public String safeGetAttribute(org.jasig.cas.authentication.Authentication authentication,String atrName)
    {
    	String ret=null;
    	if(authentication!=null){
   			if(authentication.getAttributes()!=null){
   				Object at_obj=authentication.getAttributes().get(atrName);
	        	if(at_obj!=null){
	        		String atValue = at_obj.toString();
	        		ret=atValue;
	        	}else{
	        		ret=null;
	        	}
   			}
    	}
    	return ret;
    }
    /**
     * 获取Cookie值
     * @param request
     * @param cookieName
     * @return
     */
    public String retrieveCookieValue(final HttpServletRequest request,final String cookieName) {
        final Cookie cookie = org.springframework.web.util.WebUtils.getCookie(
                request, cookieName);

        return cookie == null ? null : cookie.getValue();
    }
    /**
     *在SSM模块的jsp中，可通过reqeust对象获取TGT，然后结合后台TGT的配置信息，分析当前登录用户的登录方式，default表示通过ssm登录，其他方式表示通过client方式登录
     * @param request
     * @return
     */
  public String getLoginType(final HttpServletRequest request ){
	    String loginType="unknown";
	    String cookieName="SSMTGC";
    	String tgtVal =retrieveCookieValue(request,cookieName);
    	
    	logger.debug("TGT: "+tgtVal);  
		
		if (CommonUtils.isNotBlank(tgtVal)) {
            
			Collection<Ticket> ticketCollection = this.ticketRegistry.getTickets();
			logger.debug("CAS ticketCollection.size: "+ticketCollection.size());
			
			Ticket ticket=this.ticketRegistry.getTicket(tgtVal);
			 
			    if (ticket!=null) {
			    	if( ticket instanceof TicketGrantingTicket){
			    		
			    		TicketGrantingTicket ticketGrantingTicket = (TicketGrantingTicket)ticket;
			    	   	String tgtId = ticketGrantingTicket.getId();
			    	   	logger.debug("check for tgt.id: "+tgtId );
			    	
			    	   	org.jasig.cas.authentication.Authentication authentication 
			    	   	= ticketGrantingTicket.getAuthentication();
			    	   	
			    	   		if(authentication!=null){
			    		        if(authentication.getAttributes()!=null){
			    		        	String clientNameStored=safeGetAttribute(authentication,"clientName");
			    		        	if(clientNameStored!=null){
					    	   		 	logger.debug("check for tgt.id clientName:"+clientNameStored );
					    	   			loginType=clientNameStored;
			    		        	}else{
			    		        		loginType="ssm";
			    		        	}
			    		        }
			    	   		}//end ofif(authentication!=null)
			    	}//end of if( ticket instanceof TicketGrantingTicket)
			    }//end of  for (Ticket ticket : ticketCollection)
        }//end of if (CommonUtils.isNotBlank(token)) 
		return loginType;
    }
  
  //org.jasig.cas.client.session.ClientBackChannelLogoutMgr.getSsmLoginType(request)
  /**
   * 通过request获得当前ssm登录方式
   * @param request
   * @return 未知方式(unknown)/SSM登录(ssm)/其他客户端登录(hzbankcas)
   */
   public static String getSsmLoginType(final HttpServletRequest request){
       String loginType="unknown";
		try{
                 org.jasig.cas.client.session.ClientBackChannelLogoutMgr clientBackChannelLogoutMgr = (org.jasig.cas.client.session.ClientBackChannelLogoutMgr)com.sldt.framework.common.SpringContextHelper.getBean("clientBackChannelLogoutMgr");
                 loginType=clientBackChannelLogoutMgr.getLoginType(request);
		}catch(Throwable tr){
			tr.printStackTrace();
		}
		return loginType;
   }
    
}
