<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<link rel="shortcut icon" href="#" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/sidebar-menu.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqueryLayout/css/layout-default.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/main.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/icheck/skins/all.css" type="text/css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/public/sunlineUI/js/appClient.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/jqueryLayout/js/jquery-ui-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/jqueryLayout/js/jquery.layout.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>

<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
			
</head>
<body>
       <div class="ui-layout-west">
           <!-- 高级功能 -->
             <div id="af_calg" class="calgList">
             	<div>
				   <!--  <ul id="menuTree" class="ztree"></ul> -->
				    
				    <div id="af_menu" class="accordion sidebar-menu">
				     <!-- 
				       <c:if test="${sysMenus.childs!=null }">
				                 <c:forEach items="${sysMenus.childs}" var="menu">
				                     <div class="accordion-group">
				                       <a href="#${menu.menuId}" class="nav-header menu-first" data-toggle="collapse" data-parent="#af_menu"><i class="fa fa-rebel icolor"></i> ${menu.menuName}</a>
				                            <c:if test="${menu.childs!=null }">
				                               <ul id="${menu.menuId}" class="nav nav-list menu-second collapse in" style="height:auto;">
				                                  <c:forEach items="${menu.childs}" var="cmenu">
				                                     <li><a href="javascript:void(0)" onclick="addTabPg('${cmenu.menuId}','${cmenu.menuUrl}','${cmenu.menuName}','${cmenu.menuIcon}')"><i class="${cmenu.menuIcon} icolor"></i> ${cmenu.menuName}</a></li>
				                                  </c:forEach> 
				                                </ul>
				                            </c:if>
				                       </div>
				                    </c:forEach>
				         </c:if> 
				       -->
				       <div class="accordion-group">
				           <shiro:hasPermission name="priv_menu_ssm_01"> <a href="#smMgr" class="nav-header menu-first" data-toggle="collapse" data-parent="#af_menu"><i class="fa fa-rebel icolor"></i> 权限管理</a>
                            <ul id="smMgr" class="nav nav-list menu-second collapse in" style="height:auto;"></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_01"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_01','page/sm/org/org.jsp','机构管理','fa fa-group')"><i class="fa fa-group icolor"></i> 机构管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_02"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_02','page/sm/user/userList.jsp','人员管理','fa fa-user')"><i class="fa fa-user icolor"></i> 人员管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_03"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_03','page/sm/role/roleList.jsp','角色管理','fa fa-male')"><i class="fa fa-male icolor"></i> 角色管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_04"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_04','page/sm/privCate/privCate.jsp','权限类别管理','fa fa-list-ul')"><i class="fa fa-list-ul icolor"></i> 权限类别管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_05"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_05','page/sm/privlg/privlgList.jsp','权限管理','fa fa-empire')"><i class="fa fa-empire icolor"></i> 权限管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_06"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_06','page/sm/menu/menuList.jsp','菜单管理','fa fa-th-large')"><i class="fa fa-th-large icolor"></i> 菜单管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_07"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_07','page/sm/btn/btnList.jsp','按钮管理','fa fa-btc')"><i class="fa fa-btc icolor"></i> 按钮管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_08"><li><a href="javascript:void(0)" onclick="addTabPg('mds_07_09_08','page/sm/notice/noticeList.jsp','公告管理','fa fa-volume-up')"><i class="fa fa-volume-up icolor"></i> 公告管理</a></li></shiro:hasPermission>
                            <shiro:hasPermission name="priv_menu_ssm_01"></ul></shiro:hasPermission>
                        </div>
                        
                        <div class="accordion-group">
				           <shiro:hasPermission name="priv_menu_ssm_01"> <a href="#smMgrWf" class="nav-header menu-first" data-toggle="collapse" data-parent="#af_menu"><i class="fa fa-rebel icolor"></i> 流程管理</a>
                            <ul id="smMgrWf" class="nav nav-list menu-second collapse in" style="height:auto;"></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_01"><li><a href="javascript:void(0)" onclick="addTabPg('act_07_09_01','page/activiti/process/model/modelManage.jsp','模型管理','fa fa-group')"><i class="fa fa-group icolor"></i> 模型管理</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_02"><li><a href="javascript:void(0)" onclick="addTabPg('act_07_09_02','page/activiti/process/definition/definitionManage.jsp','流程定义','fa fa-empire')"><i class="fa fa-empire icolor"></i> 流程定义</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_03"><li><a href="javascript:void(0)" onclick="addTabPg('act_07_09_03','page/activiti/process/instance/instanceManage.jsp','流程实例跟踪','fa fa-list-ul')"><i class="fa fa-list-ul icolor"></i> 流程实例跟踪</a></li></shiro:hasPermission>
                                <shiro:hasPermission name="priv_menu_ssm_01_04"><li><a href="javascript:void(0)" onclick="addTabPg('act_07_09_04','page/activiti/process/task/taskManage.jsp','待办任务查询','fa fa-btc')"><i class="fa fa-btc icolor"></i> 待办任务查询</a></li></shiro:hasPermission>
                            <shiro:hasPermission name="priv_menu_ssm_01"></ul></shiro:hasPermission>
                        </div>
                        
					</div>
					
				 </div>
             </div>
   </div>
   
    <div class="ui-layout-center" id="contentArea" style="overflow: hidden;border: 0px;">
             <ul class="nav nav-tabs marginBottom" id="homeTab">
		          <li class="active always-visible"><a href="#home" data-toggle="tab"><i class="fa fa-home"></i> 首页</a></li>
		             
		     </ul>
		    <div class="tab-content">
		            <div class="tab-pane active" id="home">
		               <!--
		                <div class="row-fluid">
		                      <div class="span9 offset3">
		                        <img src="<%=request.getContextPath()%>/page/mb/common/images/data.jpg"/>
		                    </div>  
		                </div>  
		                <div style="height:100px"></div>
		                -->
		                <div style='tab_top'>
			                <iframe frameborder='0' id="home_f" scrolling='yes' width='100%' scr='' marginwidth="0" marginheight="0" scrolling="no">
			                </iframe>
		                </div>
		                
		            </div>
		    </div>
        </div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/sunlineUI/js/sunlineTab.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/sysmgr/js/smMain.js"></script>
</html>