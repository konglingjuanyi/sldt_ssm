package com.sldt.mds.dmc.sm.rest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.PrincipalCollection;

import com.sldt.framework.common.PageResults;
import com.sldt.mds.dmc.sm.entity.ActiveUser;
import com.sldt.mds.dmc.sm.entity.TSmBtn;
import com.sldt.mds.dmc.sm.entity.TSmMetaPrivCfg;
import com.sldt.mds.dmc.sm.entity.TSmPriv;
import com.sldt.mds.dmc.sm.entity.TSmUser;
import com.sldt.mds.dmc.sm.extend.constant.ExtendConstant;
import com.sldt.mds.dmc.sm.extend.entity.TSmKnoCats;
import com.sldt.mds.dmc.sm.extend.entity.TSmKnoContents;
import com.sldt.mds.dmc.sm.extend.entity.TSmNotice;
import com.sldt.mds.dmc.sm.extend.service.KnoService;
import com.sldt.mds.dmc.sm.extend.service.NoticeService;
import com.sldt.mds.dmc.sm.rest.dao.ISsmRestDao;
import com.sldt.mds.dmc.sm.rest.service.ISsmRestService;
import com.sldt.mds.dmc.sm.rest.vo.TSmKnoContentsPageVo;
import com.sldt.mds.dmc.sm.rest.vo.TSmNoticePageVo;
import com.sldt.mds.dmc.sm.service.BtnService;
import com.sldt.mds.dmc.sm.service.PrivlgService;
import com.sldt.mds.dmc.sm.service.UserService;
import com.sldt.mds.dmc.sm.vo.BtnVo;
import com.sldt.mds.dmc.sm.vo.MenuVo;

public class SsmRestServiceImpl implements ISsmRestService {

	@Resource(name = "userService")
	public UserService userService;

	@Resource(name = "btnService")
	public BtnService btnService;

	@Resource(name = "privlgService")
	public PrivlgService privlgService;

	@Resource(name = "noticeService")
	public NoticeService noticeService;

	@Resource(name = "knoService")
	public KnoService knoService;
	
	@Resource(name = "cacheManager")
	private EhCacheManager cacheManager;
	
	@Resource(name = "ssmRestDao")
	  public ISsmRestDao restDao;
	
	@Override
	public MenuVo getTopMenu(String appId) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return new MenuVo();
		}
		MenuVo rootMenus = userService.findMenuListByPMenuId(userCode, appId,
				"", "1");
		return rootMenus;
	}

	@Override
	public MenuVo getMenuByPMenuAndLevel(String appId, String pMenuId,
			String menuLevel) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return new MenuVo();
		}
		MenuVo mv = userService.findMenuListByPMenuId(userCode, appId, pMenuId,
				menuLevel);
		return mv;
	}

	@Override
	public List<BtnVo> getAllBtn(String appId) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return null;
		}
		return userService.findPrivlgBtns(userCode, appId);
	}

	@Override
	public BtnVo getBtnUnderMenuId(String menuId) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return new BtnVo();
		}
		BtnVo bv = userService.findPrivlgBtns(userCode, menuId, false);
		return bv;
	}

	@Override
	public BtnVo getOwnBtnByMenuId(String menuId) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return new BtnVo();
		}
		BtnVo bv = userService.findPrivlgBtns(userCode, menuId, true);
		return bv;
	}

	/**
	 * 获取当前系统已登录用户编号
	 * 
	 * @return
	 */
	private String getUserCode() {
		String userCode = null;
		try {
			PrincipalCollection principals = SecurityUtils.getSubject()
					.getPrincipals();
			ActiveUser activeUser = (ActiveUser) principals
					.getPrimaryPrincipal();
			userCode = activeUser.getUsercode();
		} catch (Exception e) {
			Object principal = SecurityUtils.getSubject().getPrincipal();
			if (principal != null) {
				userCode = principal.toString();
			} else {
				userCode = null;
			}
			e.printStackTrace();
		}
		return userCode;
	}

	@Override
	public TSmBtn getBtnInfo(String btnId) {
		TSmBtn btnInfo = btnService.findBtnInfo(btnId);
		return btnInfo;
	}

	@Override
	public List<TSmMetaPrivCfg> getMetaPriv(String instId, String userId,
			String namespace) {
		List<TSmMetaPrivCfg> result = new ArrayList<TSmMetaPrivCfg>();
		// 获取用户编号
		/* String userId = getUserCode(); */
		if (userId != null) {// 如果获取当前登录用户为空，则返回空对象
			result = privlgService.getMetaPrivCfgByUserId(userId, instId,
					namespace);
		}
		return result;
	}

	@Override
	public List<TSmMetaPrivCfg> getMetaPriv(String instId, String namespace) {
		List<TSmMetaPrivCfg> result = new ArrayList<TSmMetaPrivCfg>();
		// 获取用户编号
		String userId = getUserCode();
		if (userId != null) {// 如果获取当前登录用户为空，则返回空对象
			result = privlgService.getMetaPrivCfgByUserId(userId, instId,
					namespace);
		}
		return result;
	}

	@Override
	public MenuVo getChildMenu(String pMenuId) {
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return new MenuVo();
		}
		MenuVo childMenus = userService.findMenuListByPMenuId(userCode, null,
				pMenuId, null);
		return childMenus;
	}

	@Override
	public List<TSmPriv> getUserPrivIds(String appId) {
		List<TSmPriv> result = new ArrayList<TSmPriv>();
		// 获取用户编号
		String userCode = getUserCode();
		if (userCode == null) {// 如果获取当前登录用户为空，则返回空对象
			return result;
		}
		result = privlgService.findPrivByAppIdAndUserId(appId, userCode);
		return result;
	}

	@Override
	public List<TSmPriv> getServiPriv(String appId, String userId) {
		List<TSmPriv> result = new ArrayList<TSmPriv>();
		if (StringUtils.isNotEmpty(userId)) {
			result = privlgService.findPrivByAppIdAndUserId(appId, userId);
		}
		return result;
	}

	@Override
	public TSmNoticePageVo getNoticeListPageVo(String pageSize, String curPage) {
		PageResults page = new PageResults(Integer.parseInt(curPage),
				Integer.parseInt(pageSize));
		page.setStartIndex((Integer.parseInt(curPage) - 1)
				* Integer.parseInt(pageSize));
		page.setParameter("state", ExtendConstant.NOTICE_STATE_PUBLISH);
		List<Map<String, String>> list = noticeService.noticeListByPage(page);
		List<TSmNotice> rtList = new ArrayList<TSmNotice>();
		for (Map<String, String> map : list) {
			TSmNotice vo = new TSmNotice();
			vo.setNoticeId(map.get("noticeId"));
			vo.setTitle(map.get("title"));
			vo.setPublishTime(map.get("publishTime"));
			vo.setPublishUser(map.get("publishUser"));
			vo.setState(map.get("state"));
			rtList.add(vo);
		}
		TSmNoticePageVo pv = new TSmNoticePageVo(page.getPageSize(),
				page.getCurrPage(), page.getTotalRecs());
		pv.setRows(rtList);
		return pv;
	}
	
	@Override
	public String getNoticeListPage(String fromPage,String searchUser,String startTime,String endTime,String searchTitle,String currentPage, String pageSize,
			String callback) {
		String jsonStr = null;
		/**
		 * 如果来源与门户首页，则实现查询对象的缓存机制
		 * modify by chenbo 2016-09-13
		 */
		if("home".equals(fromPage)){
			/*if ( (cacheManager.getCache("sldt_cache") != null)){
			       cacheManager.getCache("sldt_cache").remove("dap_rs_getNoticeListPage");
			}
			
			Object ob = cacheManager.getCache("sldt_cache").get("dap_rs_getNoticeListPage");*/
			//if(ob==null){
				JSONObject json = getNoticesByPage(searchUser, startTime, endTime, searchTitle,
						currentPage, pageSize);
				jsonStr = json.toString();
				cacheManager.getCache("sldt_cache").put("dap_rs_getNoticeListPage",jsonStr);
			/*}else{
				jsonStr = (String)ob;
			}*/
		}else{
			JSONObject json = getNoticesByPage(searchUser, startTime, endTime, searchTitle,
					currentPage, pageSize);
			jsonStr = json.toString();
		}
		return callback+"("+jsonStr+")";
	}

	private JSONObject getNoticesByPage(String searchUser, String startTime,
			String endTime, String searchTitle, String currentPage,
			String pageSize) {
		JSONObject json;
		PageResults page = new PageResults(Integer.parseInt(currentPage),
				Integer.parseInt(pageSize));
		page.setStartIndex((Integer.parseInt(currentPage) - 1)
				* Integer.parseInt(pageSize));
		page.setParameter("state", ExtendConstant.NOTICE_STATE_PUBLISH);
		page.setParameter("searchUser", searchUser);
		page.setParameter("startTime",startTime);
		page.setParameter("endTime",endTime);
		page.setParameter("searchTitle",searchTitle);
		
		
		List<Map<String, String>> list = noticeService.getNoticeListByPageSql(page);
		List<TSmNotice> rtList = new ArrayList<TSmNotice>();
		for (Map<String, String> map : list) {
			TSmNotice vo = new TSmNotice();
			vo.setNoticeId((String) map.get("NOTICE_ID"));
			vo.setTitle((String) map.get("TITLE"));
			vo.setPublishTime((String) map.get("PUBLISH_TIME"));
			vo.setPublishUser(map.get("PUBLISH_USER_NAME"));
			vo.setState(map.get("STATE"));
			rtList.add(vo);
		}
		TSmNoticePageVo pv = new TSmNoticePageVo(page.getPageSize(),
				page.getCurrPage(), page.getTotalRecs());
		pv.setRows(rtList);
		json = JSONObject.fromObject(pv);
		return json;
	}

	@Override
	public TSmNotice getNotice(String id) {
		TSmNotice notice = noticeService.getTSmNoticeById(id);
		if (notice != null
				&& ExtendConstant.NOTICE_STATE_PUBLISH
						.equals(notice.getState())) {
			return notice;
		} else {
			return new TSmNotice();
		}

	}
	
	@Override
	public String getNoticeDetail(String noticeId, String callback) {
		TSmNotice tsn = this.getNotice(noticeId);
		JSONObject json = JSONObject.fromObject(tsn);
		return callback+"("+json.toString()+")";
	}

	@Override
	public List<TSmKnoCats> getKnoCatsList() {
		return knoService.getKncatList();
	}

	@Override
	public TSmKnoContentsPageVo getKnoContentsListPageVo(String pageSize,
			String curPage, String catId, String keyword) {
		PageResults page = new PageResults(Integer.parseInt(curPage),
				Integer.parseInt(pageSize));
		page.setStartIndex((Integer.parseInt(curPage) - 1)
				* Integer.parseInt(pageSize));
		if (catId != null && !"$".equals(catId)) {
			page.setParameter("kncatId", catId);
		}
		if (keyword != null && !"$".equals(keyword)) {
			page.setParameter("allField", "%" + keyword + "%");
		}

		List<Map<String, Object>> list = knoService.knoContentsList(page);
		List<TSmKnoContents> rtList = new ArrayList<TSmKnoContents>();
		for (Map<String, Object> map : list) {
			TSmKnoContents vo = new TSmKnoContents();
			vo.setKncntId((String) map.get("kncntId"));
			vo.setKncntTitle((String) map.get("kncntTitle"));
			vo.setPlanTitle((String) map.get("planTitle"));
			vo.setKncatName((String) map.get("kncatName"));
			vo.setKncntKey((String) map.get("kncntKey"));
			rtList.add(vo);
		}
		TSmKnoContentsPageVo pv = new TSmKnoContentsPageVo(page.getPageSize(),
				page.getCurrPage(), page.getTotalRecs());
		pv.setRows(rtList);
		return pv;
	}
	

	@Override
	public TSmKnoContents getKnoContents(String id) {
		return knoService.getKnoContentById(id);
	}

	@Override
	public String getUpdatePass(String userid, String pass) {
		// 查询用户对象
		TSmUser ts = userService.findUserById(userid);
		String message = "";
		/*String pwd = DmpEncryptUtil.getEncPassw(pass);
		ts.setLogonPwd(pwd);*/
		try {
			userService.updateUser(ts);
			message = "修改成功";
		} catch (Exception e) {
			message = "修改异常";
		}
		return message;
	}

	@Override
	public String getoldPass(String userid) {
		// 查询用户对象
		TSmUser ts = userService.findUserById(userid);
		return ts.getLogonPwd();
	}

	@Override
	public String getUserName(String userid) {
		TSmUser ts = userService.findUserById(userid);
		return ts.getName();
	}

	@Override
	public String queryUser(String userid, String username) {
		String userId=null;
		String userName=null;
		if(userid==""){
			userId=" ";
		}else{
			userId=userid;
		}
		if(username==""){
			userName=" ";
		}else
		{
			userName=username;
		}
		List<TSmUser> tsmUserList=userService.queryUser(userId, userName);
		JSONArray jsonarray=JSONArray.fromObject(tsmUserList);
		return jsonarray.toString();
	}
	public String queryUserName(String userid) {
		String userId=null;
		String userName=null;
		if(userid==""){
			userId=" ";
		}else{
			userId=userid;
		}
		List<TSmUser> tsmUserList=userService.queryUser(userId, userName);
		String name=tsmUserList.get(0).getName();
		return name;
	}

	
	@Override
	public String getDapDept(String callback) {
	   String list = restDao.getDapDept();
	   return  callback+"("+list+")";
	}
	
}
