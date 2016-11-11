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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.session.Session;

/**
 * HashMap backed implementation of SessionMappingStorage.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 *
 */
public final class ShiroHashMapBackedSessionMappingStorage implements ShiroSessionMappingStorage {
	
    /**
     * Maps the ID from the CAS server to the Session.
     */
    private final Map<String,org.apache.shiro.session.Session> MANAGED_SESSIONS = new HashMap<String,org.apache.shiro.session.Session>();

    /**
     * Maps the Session ID to the key from the CAS Server.
     */
    private final Map<String,String> ID_TO_SESSION_KEY_MAPPING = new HashMap<String,String>();

    private final Log log = LogFactory.getLog(getClass());

	public synchronized void addSessionById(String mappingId, org.apache.shiro.session.Session session) {
        ID_TO_SESSION_KEY_MAPPING.put((String)session.getId(), mappingId);
        MANAGED_SESSIONS.put(mappingId, session);

	}                               

	public synchronized void removeBySessionById(String sessionId) {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to remove Session=[" + sessionId + "]");
        }

        final String key = ID_TO_SESSION_KEY_MAPPING.get(sessionId);

        if (log.isDebugEnabled()) {
            if (key != null) {
                log.debug("Found mapping for session.  Session Removed.");
            } else {
                log.debug("No mapping for session found.  Ignoring.");
            }
        }
        MANAGED_SESSIONS.remove(key);
        ID_TO_SESSION_KEY_MAPPING.remove(sessionId);
	}

	public synchronized org.apache.shiro.session.Session removeSessionByMappingId(String mappingId) {
		final org.apache.shiro.session.Session session = MANAGED_SESSIONS.get(mappingId);

        if (session != null) {
        	removeBySessionById((String)session.getId());
        }

        return session;
	}
}
