package org.jasig.cas.client.session.shiro;



import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.session.SingleSignOutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 基于ShiroFilter实现CAS的单点登出机制
 * @author zizhiguo
 *
 */
public class ShiroSingleSignOutFilter extends AbstractShiroConfigurationFilter {
	    private static Logger logger = LoggerFactory.getLogger(ShiroSingleSignOutFilter.class);
	    private static final ShiroSingleSignOutHandler handler = new ShiroSingleSignOutHandler();
	    //是否需要处理HttpSession，只有在使用Shiro的Session管理器的时候，需要增加多传统httpSession的处理逻辑，否则SSO单点退出之后，可能某些HttpSession没有销毁
	    private boolean bNeedDoHttpSession=false;
	    
	    private String needDoHttpSession="false"; 

		public String getNeedDoHttpSession() {
			return needDoHttpSession;
		}

		public void setNeedDoHttpSession(String needDoHttpSession) {
			if("yes".equalsIgnoreCase(needDoHttpSession)
			||"1".equalsIgnoreCase(needDoHttpSession)
			||"true".equalsIgnoreCase(needDoHttpSession)
			||"t".equalsIgnoreCase(needDoHttpSession)
			  ){
				bNeedDoHttpSession=true;
			}
			this.needDoHttpSession=needDoHttpSession;
		}


		
	
		
		protected static ShiroSingleSignOutHandler getSingleSignOutHandler() {
	        return handler;
	    }
	    protected void onFilterConfigSet() throws Exception {
	    	 if (!isIgnoreInitConfiguration()) {
		            handler.setArtifactParameterName(getPropertyFromInitParams("artifactParameterName", "ticket"));
		            handler.setLogoutParameterName(getPropertyFromInitParams("logoutParameterName", "logoutRequest"));
		        }
		        handler.init();
	    }
	    
	    public void setArtifactParameterName(final String name) {
	        handler.setArtifactParameterName(name);
	    }
	    
	    public void setLogoutParameterName(final String name) {
	        handler.setLogoutParameterName(name);
	    }

	    public void setSessionMappingStorage(final ShiroSessionMappingStorage storage) {
	        handler.setSessionMappingStorage(storage);
	    }
	    //传统HttpSession的处理器
	    private static final SingleSignOutHandler casSSOHandler = new SingleSignOutHandler();
	    /**
	     * 
	     */
	    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
	    	final HttpServletRequest httpRequest = (HttpServletRequest) request;
	        if (handler.isTokenRequest(httpRequest)) {
	            handler.recordSession(httpRequest);
	            if(bNeedDoHttpSession){
	            	casSSOHandler.recordSession(httpRequest);
	            }
	        } else if (handler.isLogoutRequest(httpRequest)) {
	            handler.destroySession(httpRequest);
	            if(bNeedDoHttpSession){
	            	casSSOHandler.destroySession(httpRequest);
	            }
	           // org.jasig.cas.support.pac4j.web.flow.ClientBackChannelAction(); 
	            // Do not continue up filter chain
	            return false;//返回false，指示无需执行后续内容
	        } else {
	        	logger.trace("Ignoring URI " + httpRequest.getRequestURI());
	        }
	        return true;
	    }
}
