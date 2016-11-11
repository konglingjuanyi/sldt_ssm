/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.client.session.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Listener to detect when an HTTP session is destroyed and remove it from the map of
 * managed sessions.  Also allows for the programmatic removal of sessions.
 * <p>
 * Enables the CAS Single Sign out feature.
 *
 * Scott Battaglia
 * @version $Revision$ Date$
 * @since 3.1
 */
public final class ShiroSingleSignOutHttpSessionListener implements SessionListener {

	private ShiroSessionMappingStorage sessionMappingStorage;
	/**
     * Obtains a {@link SessionMappingStorage} object. Assumes this method will always return the same
     * instance of the object.  It assumes this because it generally lazily calls the method.
     * 
     * @return the SessionMappingStorage
     */
    protected static ShiroSessionMappingStorage getSessionMappingStorage() {
    	return ShiroSingleSignOutFilter.getSingleSignOutHandler().getSessionMappingStorage();
    }

    
    
    
    //   public void sessionCreated(final HttpSessionEvent event) {
	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		
	}

	// public void sessionDestroyed(final HttpSessionEvent event) {
	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
    	if (sessionMappingStorage == null) {
    	    sessionMappingStorage = getSessionMappingStorage();
    	}
    	if(session!=null){
         sessionMappingStorage.removeBySessionById((String)session.getId());
    	}
	}

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		
	}
}
