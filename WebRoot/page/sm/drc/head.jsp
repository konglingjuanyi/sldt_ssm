<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/page/sm/common/css/common.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/jqueryLayout/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/sunlineUI/js/appClient.js"></script>
	
<style>
.nav>.nav-in ul{
	font-family: "宋体";
	font-size: 14px;
	font-weight: 600;
	text-align: center;
}
.nav-in ul li {
    line-height: 32px;
    width: 120px;
}
</style>
<!-- head 页面内容  -->
<div class="header">
	<div class="header-in">
		<div class="header-left fl">
			<h1>
				<a href="javacript:void(0)"><img
					src="<%=request.getContextPath()%>/public/sunlineUI/img/logo.png"
					alt="" /></a>
			</h1>
		</div>
		<div class="header-right fr">
			<div class="Hright-top clear">
				<ul class="fr">
                        <li>（<shiro:principal/>） 你好，欢迎来到数据管控平台！</li>
                        <li>|</li>
                        <li><a class="modify" href="javascript:void(0)" onclick="openDap();">访问数据之家</a></li>
                        <li>|</li>
                        <li><a class="modify" href="javascript:void(0)" onclick="openDataSourceDetail('修改密码','','');">修改密码</a></li>
                        <li><a class="cancellation" href="/../ssm/logout">注销</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="nav">
	<div class="nav-in">
		<ul>
			<shiro:hasPermission name="priv_menu_dmc_02"><li><a class="" href="/../mds/mdsFrame.do?method=mdsMain" url="">元数据管理</a></li></shiro:hasPermission>
			<shiro:hasPermission name="priv_menu_dmc_06"><li style="width: 5px;margin-left: 2px;margin-right: 4px;color: #CFE4F3;">|</li><li><a class=""  href="/../mds/devFrame.do?method=devMain" url="">元数据设计</a></li></shiro:hasPermission>
            <shiro:hasPermission name="priv_menu_dmc_03"><li style="width: 5px;margin-left: 2px;margin-right: 4px;color: #CFE4F3;">|</li><li><a class="" href="/../dsm" url="">数据标准管理</a></li></shiro:hasPermission>
            <shiro:hasPermission name="priv_menu_dmc_04"><li style="width: 5px;margin-left: 2px;margin-right: 4px;color: #CFE4F3;">|</li><li><a class="" href="/../dqs/dataSource.do?method=index" url="">数据质量管理</a></li></shiro:hasPermission>
            <shiro:hasPermission name="priv_menu_dmc_01"><li style="width: 5px;margin-left: 2px;margin-right: 4px;color: #CFE4F3;">|</li><li><a class="" href="/../psm/taskMain.jsp" url="">调度管理</a></li></shiro:hasPermission>
			<shiro:hasPermission name="priv_menu_dmc_05"><li style="width: 5px;margin-left: 2px;margin-right: 4px;color: #CFE4F3;">|</li><li><a class="cur" href="/../ssm/smFrame.do?method=sysMgrMain" url="">系统管理</a></li></shiro:hasPermission>
			
		</ul>
	</div>
</div>
<script type="text/javascript">
<!--
function openDap(){
	window.open("/dap/");
}
//-->
</script>
