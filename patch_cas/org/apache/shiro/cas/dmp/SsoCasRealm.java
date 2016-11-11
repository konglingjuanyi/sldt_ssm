package org.apache.shiro.cas.dmp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sldt.mds.dmc.sm.entity.ActiveUser;
import com.sldt.mds.dmc.sm.entity.TSmMenu;
import com.sldt.mds.dmc.sm.entity.TSmPriv;
import com.sldt.mds.dmc.sm.entity.TSmUser;
import com.sldt.mds.dmc.sm.external.SMExternalSvc;
import com.sldt.mds.dmc.sm.service.PrivlgService;
import com.sldt.mds.dmc.sm.service.UserService;
import com.sldt.mds.dmc.sm.vo.MenuVo;


/**
 * shiro权限处理
 * @author 
 *
 */
public class SsoCasRealm extends CasRealm  {
	
	private static Logger log = LoggerFactory.getLogger(SsoCasRealm.class);
	
	public UserService userService;
	public PrivlgService privlgService;
	private SMExternalSvc smExternalSvc;
	
	public UserService getUserService(){
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public PrivlgService getPrivlgService() {
		return privlgService;
	}

	public void setPrivlgService(PrivlgService privlgService) {
		this.privlgService = privlgService;
	}

	public SMExternalSvc getSmExternalSvc() {
		return smExternalSvc;
	}

	public void setSmExternalSvc(SMExternalSvc smExternalSvc) {
		this.smExternalSvc = smExternalSvc;
	}

	//设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("ssoCasRealm");
	}

	/** 
     * 为当前登录的Subject授予角色和权限 
     * @see  该方法的调用时机为需授权资源被访问时 
     * @see  并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
    	
    	//从 principals获取主身份信息
		//将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		
		//获取当前用户的用户账号
		String userId = (String) principals.getPrimaryPrincipal();
		//根据身份信息获取权限
		List<TSmPriv> userPrivs = privlgService.findPrivByAppIdAndUserId("ssm",userId);
		
		/*//从数据库获取到权限数据,这里先固定传sm作为系统标志参数  按钮唯一标识暂定为：上级菜单：按钮编号
		List<String> btnPermissionList = userService.findBtnListByUserId(userId, "mds");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();	
		simpleAuthorizationInfo.addStringPermissions(btnPermissionList);
		MenuVo rootMenus = userService.findMenuListByPMenuId(userId,"mds","","1");
		List<TSmMenu> rootMc = rootMenus.getChilds();
		List<String> rootMp = new ArrayList<String>();
		if(rootMc!=null){
			for(int i=0;i<rootMc.size();i++){
				rootMp.add("rootMenu:"+rootMc.get(i).getMenuId());
			}
		}
		simpleAuthorizationInfo.addStringPermissions(rootMp);*/
		//获取当前用户的权限码
		List<String> privs = this.getCurrUserPrivs(userPrivs);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(privs);
		return simpleAuthorizationInfo;
		 
    }  
    
    private List<String> getCurrUserPrivs(List<TSmPriv> userPrivs) {
    	List<String> result = new ArrayList<String>();;
        if(userPrivs!=null && userPrivs.size()>0){
        	for(TSmPriv sp:userPrivs){
        		 result.add(sp.getPrivId());
        	}
        }
		return result;
	}

	/**
     * Authenticates a user and retrieves its information.
     * 
     * @param token the authentication token
     * @throws AuthenticationException if there is an error during authentication.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }
        
        String ticket = (String)casToken.getCredentials();
        if (!StringUtils.hasText(ticket)) {
            return null;
        }
        
        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
                    ticket, getCasServerUrlPrefix(), userId
            });

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(userId, attributes);
            
            
          /*
            ////////////////////////////////////////////////////////////////////////
      
    		//第二步：根据用户输入的userCode从数据库查询
    		TSmUser tSmUser = null;
    		tSmUser = userService.findUserByUserId(userId);
    		
    		// 如果查询不到返回null
    		if(tSmUser==null){
    			//异常处理，用户不存在
    			throw new UnknownAccountException();
    		}
    		
    		//判定用户状态，如果状态不是正常的，暂时统一返回用户被锁定异常
    		if(!("0".equals(tSmUser.getUserStat()))) {
    			//1：未启用 2：作废 5：暂时停用 6：锁定
    			throw new LockedAccountException();
    		}
    		//根据用户ID查询菜单信息
    		//activeUser当前登录用户的信息，包括菜单,权限信息
    		ActiveUser activeUser = new ActiveUser();
    		
    		activeUser.setUsercode(userId);
    		activeUser.setUsername(tSmUser.getName());
    		
    		//根据用户id取出菜单
    		//List<MenudataInfo> menudataInfoList  = null;
    		//menudataInfoList = userService.findMenuListByUserId(userCode,"mds");
    		//activeUser.setMenudataInfoList(menudataInfoList);
    	   
    		MenuVo rootMenus = userService.findMenuListByPMenuId(userId,"mds","","1");
    		MenuVo sysMenus = userService.findMenuListByPMenuId(userId,"mds","mds_07","3");
    		MenuVo dataObjMenus = userService.findMenuListByPMenuId(userId,"mds","mds_05","3");
    		MenuVo scMenus = userService.findMenuListByPMenuId(userId,"mds","mds_04","3");
    		MenuVo saMenus = userService.findMenuListByPMenuId(userId,"mds","mds_06","3");
    		
    		setSession("activeUser",activeUser);
    		setSession("rootMenus",rootMenus);
    		setSession("sysMenus",sysMenus);
    		setSession("scMenus",scMenus);
    		setSession("saMenus",saMenus);
    		setSession("dataObjMenus",dataObjMenus);
    		//////////////////////////////////////////////////////////////////////////////
    	*/
            TSmUser tSmUser = null;
    		tSmUser = userService.findUserByUserId(userId);
    		setSession("userName",tSmUser.getName());
    		
    		
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException e) { 
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }
    
    
      
       
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    @SuppressWarnings("unused")
	private void setSession(String key, Object value){  
    	WebSubject webSubject = (WebSubject)SecurityUtils.getSubject();
    	if(webSubject!=null){
        	ServletRequest request = webSubject.getServletRequest();   
    		HttpSession httpSession = ((HttpServletRequest)request).getSession(); 
    		if(httpSession!=null){
    			httpSession.setAttribute(key, value);  
    		}	
    	} 
       /* Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }*/  
    }  
    
    //清除缓存
    //当有权限变更的时候的，亦即是用户权限实时运行表T_SM_USER_PRIVLG_RT有数据更新时可以调用下这个方法，来清除下
    //缓存，这样用户可以不用重新登录权限就可以起作用
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
