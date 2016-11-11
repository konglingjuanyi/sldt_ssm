package com.sldt.mds.dmc.sm.rest.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sldt.mds.dmc.sm.entity.TSmBtn;
import com.sldt.mds.dmc.sm.entity.TSmMetaPrivCfg;
import com.sldt.mds.dmc.sm.entity.TSmPriv;
import com.sldt.mds.dmc.sm.extend.entity.TSmKnoCats;
import com.sldt.mds.dmc.sm.extend.entity.TSmKnoContents;
import com.sldt.mds.dmc.sm.extend.entity.TSmNotice;
import com.sldt.mds.dmc.sm.rest.vo.TSmKnoContentsPageVo;
import com.sldt.mds.dmc.sm.rest.vo.TSmNoticePageVo;
import com.sldt.mds.dmc.sm.vo.BtnVo;
import com.sldt.mds.dmc.sm.vo.MenuVo;

/**
 * REST系统管理—— 权限查询
 * 
 * @author chenbo
 * 
 */
public interface ISsmRestService {

	/**
	 * 获取系统的一级菜单
	 * 
	 * @param appId
	 * @return
	 */
	@GET
	@Path("/getTopMenu/{appId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public MenuVo getTopMenu(@PathParam("appId") String appId);

	/**
	 * 获取某菜单的子菜单
	 * 
	 * @param appId
	 * @return
	 */
	@GET
	@Path("/getChildMenu/{pMenuId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public MenuVo getChildMenu(@PathParam("pMenuId") String pMenuId);

	/**
	 * 根据父菜单编号与子层级获取子集菜单集合
	 * 
	 * @param appId
	 * @param pMenuId
	 * @param menuLevel
	 * @return
	 */
	@GET
	@Path("/getMenu/{appId}/{pMenuId}/{menuLevel}")
	@Produces({ MediaType.APPLICATION_JSON })
	public MenuVo getMenuByPMenuAndLevel(@PathParam("appId") String appId,
			@PathParam("pMenuId") String pMenuId,
			@PathParam("menuLevel") String menuLevel);

	/**
	 * 获取系统的所有按钮
	 * 
	 * @param appId
	 * @return
	 */
	@GET
	@Path("/getAllBtn/{appId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<BtnVo> getAllBtn(@PathParam("appId") String appId);

	/**
	 * 获取某个菜单下的所有按钮
	 * 
	 * @param menuId
	 * @return
	 */
	@GET
	@Path("/getBtnUnderMenuId/{menuId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public BtnVo getBtnUnderMenuId(@PathParam("menuId") String menuId);

	/**
	 * 获取某个菜单下的直接归属按钮
	 * 
	 * @param menuId
	 * @return
	 */
	@GET
	@Path("/getOwnBtnByMenuId/{menuId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public BtnVo getOwnBtnByMenuId(@PathParam("menuId") String menuId);

	/**
	 * 获取某个菜单下的直接归属按钮
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getBtnInfo/{btnId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TSmBtn getBtnInfo(@PathParam("btnId") String btnId);

	/**
	 * 获取某个元数据下的权限,东莞农商银行获取用户有问题，修改接口直接将用户作参数传递20160405
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getMetaPriv/{instId}/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TSmMetaPrivCfg> getMetaPriv(@PathParam("instId") String instId,
			@PathParam("userId") String userId,
			@QueryParam("namespace") String namespace);

	/**
	 * 获取某个元数据下的权限
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getMetaPriv/{instId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TSmMetaPrivCfg> getMetaPriv(@PathParam("instId") String instId,
			@QueryParam("namespace") String namespace);

	/**
	 * 获取用户的资源权限
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getPriv/{appId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TSmPriv> getUserPrivIds(@PathParam("appId") String appId);

	/**
	 * 获取用户的资源权限
	 * 
	 * @param btnId
	 * @return http://ip:port/ssm/rest/api/ssm/getServiPriv/dqs/chenbo
	 */
	@GET
	@Path("/getServiPriv/{appId}/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TSmPriv> getServiPriv(@PathParam("appId") String appId,
			@PathParam("userId") String userId);

	/**
	 * 公告列表
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getNoticeListPageVo/{pageSize}/{curPage}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TSmNoticePageVo getNoticeListPageVo(
			@PathParam("pageSize") String pageSize,
			@PathParam("curPage") String curPage);
	
	
	/**
	 * 跨域获取公告列表信息
	 * @param deptId
	 */
	@GET
	@Path("/getNoticeListPage")
	@Produces({ MediaType.APPLICATION_JSON})
	public String getNoticeListPage(@QueryParam("fromPage") String fromPage,@QueryParam("searchUser") String searchUser,@QueryParam("startTime") String startTime,@QueryParam("endTime") String endTime,@QueryParam("searchTitle") String searchTitle,@QueryParam("currentPage") String currentPage,@QueryParam("pageSize") String pageSize,@QueryParam("callback") String callback);
	

	/**
	 * 公告详情
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getNotice/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TSmNotice getNotice(@PathParam("id") String id);
	

	/**
	 * 跨域获取公告详情信息
	 * @param deptId
	 */
	@GET
	@Path("/getNoticeDetail/{noticeId}")
	@Produces({ MediaType.APPLICATION_JSON})
	public String getNoticeDetail(@PathParam("noticeId") String noticeId,@QueryParam("callback") String callback);
	

	/**
	 * 获取知识库大类
	 * 
	 * @return
	 * @author chensongwei
	 * @datetime 2016年2月1日 下午2:14:35
	 */
	@GET
	@Path("/getKnoCatsList")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<TSmKnoCats> getKnoCatsList();

	/**
	 * 公告列表
	 * 
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getKnoContentsListPageVo/{pageSize}/{curPage}/{catId}/{keyword}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TSmKnoContentsPageVo getKnoContentsListPageVo(
			@PathParam("pageSize") String pageSize,
			@PathParam("curPage") String curPage,
			@PathParam("catId") String catId,
			@PathParam("keyword") String keyword);

	
	/**
	 * 公告详情
	 * @param btnId
	 * @return
	 */
	@GET
	@Path("/getKnoContents/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TSmKnoContents getKnoContents(@PathParam("id") String id);
	

	/**
	 * 用户原始密码验证
	 */
	@GET
	@Path("/getoldPass/{userid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getoldPass(@PathParam("userid") String userid);

	/**
	 * 用户修改密码的接口
	 */
	@GET
	@Path("/getUpdatePass/{userid}/{pass}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getUpdatePass(@PathParam("userid") String userid,
			@PathParam("pass") String pass);
	
	/**
	 * 获取当前用户姓名
	 */
	@GET
	@Path("/getUserName/{userid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getUserName(@PathParam("userid") String userid);
	/**
	 * 查询所有用户信息 
	 */
	@GET
	@Path("/queryUser")
	@Produces({ MediaType.APPLICATION_JSON })
	public String queryUser(@QueryParam("userid") String userid,@QueryParam("username") String username);
	/** 
	 * 通过用户编号查询用户姓名
	 */
	@GET
	@Path("/queryUserName")
	@Produces({ MediaType.APPLICATION_JSON })
	public String queryUserName(@QueryParam("userid") String userid);
	
	/**
	 * dap  得到dept
	 * @return
	 */
	@GET
	@Path("/getDapDept")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getDapDept(@QueryParam("callback") String callback);
}
