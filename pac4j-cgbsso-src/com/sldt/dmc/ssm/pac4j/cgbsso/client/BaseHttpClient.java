/*
  Copyright 2012 - 2013 zzg

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
package com.sldt.dmc.ssm.pac4j.cgbsso.client;

import org.pac4j.core.client.BaseClient;
import org.pac4j.core.client.Protocol;
import org.pac4j.core.util.CommonHelper;

import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.SsoUsernameAuthenticator;
import com.sldt.dmc.ssm.pac4j.cgbsso.credentials.SsoUsernameCredentials;
import com.sldt.dmc.ssm.pac4j.cgbsso.profile.ProfileCreator;
import com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile;
import com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoUsernameProfileCreator;

/**
 * This class is the base HTTP client to authenticate users through HTTP protocol.
 * <p />
 * The username and password inputs must be retrieved through HTTP protocol.
 * <p />
 * To validate credentials, an {@link SsoUsernameAuthenticator} must be defined through the
 * {@link #setUsernamePasswordAuthenticator(SsoUsernameAuthenticator)} method.
 * <p />
 * To create the profile, a {@link ProfileCreator} must be defined through the {@link #setProfileCreator(ProfileCreator)} method.
 * <p />
 * It returns a {@link com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile}.
 * 
 * @see com.sldt.dmc.ssm.pac4j.cgbsso.profile.SsoProfile
 * @author zzg
 * @since 1.4.0
 */
public abstract class BaseHttpClient extends BaseClient<SsoUsernameCredentials, SsoProfile> {
    protected SsoUsernameAuthenticator ssoUsernameAuthenticator;
    
    private ProfileCreator profileCreator = new SsoUsernameProfileCreator();
    
    @Override
    public BaseHttpClient clone() {
        final BaseHttpClient newClient = (BaseHttpClient) super.clone();
        newClient.setSsoUsernameAuthenticator(this.ssoUsernameAuthenticator);
        newClient.setProfileCreator(this.profileCreator);
        return newClient;
    }
    
    @Override
    protected void internalInit() {
        CommonHelper.assertNotNull("usernamePasswordAuthenticator", this.ssoUsernameAuthenticator);
        CommonHelper.assertNotNull("profileCreator", this.profileCreator);
    }
    
    @Override
    protected SsoProfile retrieveUserProfile(final SsoUsernameCredentials credentials) {
        // create user profile
        final SsoProfile profile = this.profileCreator.create(credentials.getUsername());
        logger.debug("profile : {}", profile);
        return profile;
    }
    
    public SsoUsernameAuthenticator getSsoUsernameAuthenticator() {
        return this.ssoUsernameAuthenticator;
    }
    
    public void setSsoUsernameAuthenticator(final SsoUsernameAuthenticator usernamePasswordAuthenticator) {
        this.ssoUsernameAuthenticator = usernamePasswordAuthenticator;
    }
    
    public ProfileCreator getProfileCreator() {
        return this.profileCreator;
    }
    
    public void setProfileCreator(final ProfileCreator profileCreator) {
        this.profileCreator = profileCreator;
    }
    
    @Override
    public Protocol getProtocol() {
        return Protocol.CGBSSO;
    }
}
