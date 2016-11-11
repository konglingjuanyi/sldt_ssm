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
 * This class is the user profile for sites using SSO protocol.<br />
 * It is returned by the {@link com.sldt.dmc.ssm.pac4j.cgbsso.client.CgbssoClient} or the {@link com.pactera.ap.pas.pac4j.cgbsso.client.BasicAuthClient}.
 * <p />
 * <table border="1" cellspacing="2px">
 * <tr>
 * <th>Method :</th>
 * <th>From the JSON profile response :</th>
 * </tr>
 * <tr>
 * <th colspan="2">The attributes of the {@link org.pac4j.core.profile.CommonProfile}</th>
 * </tr>
 * <tr>
 * <td>String getEmail()</td>
 * <td>the <i>email</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getFirstName()</td>
 * <td>the <i>first_name</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getFamilyName()</td>
 * <td>the <i>last_name</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getDisplayName()</td>
 * <td>the <i>display_name</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getUsername()</td>
 * <td>the <i>username</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Gender getGender()</td>
 * <td>the <i>gender</i> attribute</td>
 * </tr>
 * <tr>
 * <td>Locale getLocale()</td>
 * <td>the <i>locale</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getPictureUrl()</td>
 * <td>the <i>picture_url</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getProfileUrl()</td>
 * <td>the <i>profile_url</i> attribute</td>
 * </tr>
 * <tr>
 * <td>String getLocation()</td>
 * <td>the <i>location</i> attribute</td>
 * </tr>
 * </table>
 * <p />
 * All other attributes must be retrieved using the {@link #getAttributes()} method.
 * 
 * @see com.sldt.dmc.ssm.pac4j.cgbsso.client.CgbssoClient
 * @see com.pactera.ap.pas.pac4j.cgbsso.client.BasicAuthClient
 * @author zzg
 * @since 1.4.0
 */
public class SsoProfile extends CommonProfile {
    
    private static final long serialVersionUID = -6034670698976243101L;
    
    private String id;
    
    public void setId(Object id)
    {
      super.setId(id);
      if (id != null)
      {
        String sId = id.toString();
        logger.debug("identifier : {}", sId);
        this.id = sId;
      }
    }
    public String getId()
    {
      return this.id;
    }
    /*
     * 调整默认实现，直接将id返回，
     * 不输出getClass().getSimpleName() + "#" + this.id;
     * @see org.pac4j.core.profile.UserProfile#getTypedId()
     */
    public String getTypedId()
    {
      return  this.id;
    }
    
}
