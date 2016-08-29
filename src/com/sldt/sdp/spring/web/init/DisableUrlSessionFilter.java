package com.sldt.sdp.spring.web.init;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
public class DisableUrlSessionFilter implements Filter{
	 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        // skip non-http requests
	        if (!(request instanceof HttpServletRequest)) {
	        	System.out.println("===skip non-http requests====");
	             chain.doFilter(request, response);
	            return;
	         }
	 
	         HttpServletRequest httpRequest = (HttpServletRequest) request;
	         HttpServletResponse httpResponse = (HttpServletResponse) response;
	 
	        String uri = httpRequest.getRequestURI();
	 		String queryStr = httpRequest.getQueryString();
	 		String newRequestUrl = null;
	        // clear session if session id in URL
	        if (httpRequest.isRequestedSessionIdFromURL()) {
	        	System.out.println("=====clear session if session id in URL======");
	        	int idx_key = uri.indexOf("/login;JSESSIONID=");
	        	if(idx_key != -1) {
	        		int idx_key_2 = uri.indexOf(";JSESSIONID=");
	    			String newUri = uri.substring(0, idx_key_2);
	    			newRequestUrl = newUri + "?" + queryStr;
	    			if(queryStr != null){
	    				newRequestUrl = newUri + "?" + queryStr;
	    			}else{
	    				newRequestUrl = newUri;
	    			}
	    			System.out.println("zzg---debug---20160413---newRequestUrl:" 
	    					+ newRequestUrl);
	        	}else {
	        		 HttpSession session = httpRequest.getSession();
	 	            if (session != null) session.invalidate();
	        	}
	            
	        }
	        // wrap response to remove URL encoding
	         HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
	             @Override
	            public String encodeRedirectUrl(String url) {
	                return url;
	             }
	 
	          
	            public String encodeRedirectURL(String url) {
	                return url;
	             }
	 
	            
	            public String encodeUrl(String url) {
	                return url;
	             }
	 
	            
	            public String encodeURL(String url) {
	                return url;
	             }
	         };
	         if(newRequestUrl != null) {
	        	 wrappedResponse.sendRedirect(newRequestUrl);
	         }else {
	        	// process next request in chain
		         System.out.println("=======doFilter===");
		         chain.doFilter(request, wrappedResponse);
	         }
	     }
	 
	    /**
	      * Unused.
	     */
	    public void init(FilterConfig config) throws ServletException {
	     }
	 
	    /**
	      * Unused.
	     */
	    public void destroy() {
	     }
}
