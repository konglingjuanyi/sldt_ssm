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
package com.sldt.dmc.ssm.pac4j.cgbsso.profile;

import org.pac4j.core.profile.CommonProfile;

/**
 * This class is a profile creator which creates a HTTP profile with username set.
 * 
 * @author zzg
 * @since 1.4.0
 */
public class SsoUsernameProfileCreator implements ProfileCreator {
    
    /**
     * Create a SSO profile.
     * 
     * @param username
     * @return the created profile
     */
    public SsoProfile create(final String username) {
        final SsoProfile profile = new SsoProfile();
        profile.setId(username);
        profile.addAttribute(CommonProfile.USERNAME, username);
        return profile;
    }
}
