/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.support.pac4j.authentication;

import org.jasig.cas.authentication.AuthenticationBuilder;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.support.pac4j.authentication.principal.ClientCredential;
import org.pac4j.cas.credentials.CasCredentials;
import org.pac4j.core.credentials.Credentials;

/**
 * This class is a meta data populator for authentication. The client name associated to the authentication is added
 * to the authentication attributes.
 * zzg 20160810 
 * 修改cas-server-support-pac4j-4.0.0.jar中的
 * org.jasig.cas.support.pac4j.authentication.
 * ClientAuthenticationMetaDataPopulator类，支持cas客户端后台退出
 * @author Jerome Leleu
 * @since 3.5.0
 */
public final class ClientAuthenticationMetaDataPopulator implements AuthenticationMetaDataPopulator {

    /***
     * The name of the client used to perform the authentication.
     */
    public static final String CLIENT_NAME = "clientName";
    //zzg 20160810
    public static final String CLIENT_TOKEN = "clientToken";

    /**
     * {@inheritDoc}
     */
    @Override
    public void populateAttributes(final AuthenticationBuilder builder, final Credential credential) {
        if (credential instanceof ClientCredential) {
            final ClientCredential clientCredential = (ClientCredential) credential;
            builder.addAttribute(CLIENT_NAME, clientCredential.getCredentials().getClientName());
            //zzg 20160810
            Credentials c =clientCredential.getCredentials();
            if(c instanceof CasCredentials){
            	 builder.addAttribute(CLIENT_TOKEN, ((CasCredentials)c).getServiceTicket());
            }
        }
    }
}
