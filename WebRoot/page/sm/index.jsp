<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<!--[if IE]>
<style type="text/css"> 
	@font-face {
	font-family:"FontAwesome";
	src:url("<%=request.getContextPath()%>/public/fontAwesome/fonts/fontawesome-webfont.eot?v=4.2.0");
	font-weight:normal;
	font-style:normal}
</style>
<![endif]-->
<%@ include file="/page/sm/common/head.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/main.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	//判断当前窗口是否有顶级窗口，如果有就让当前的窗口的地址栏发生变化
	function loadTopWindow(){ 
		if (window.top!=null && window.top.document.URL!=document.URL){ 
			window.top.location= document.URL; 
		} 
	}
</script>		
</head>
<shiro:hasPermission name="priv_menu_dmc_05">
<body onload="loadTopWindow()">
	<div class="row-fluid">
		<div class="span12">
			 <iframe  id="mainArea" name="mainArea" src="<%=request.getContextPath()%>/home.jsp" width="100%"  
			 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" 
			 allowtransparency="yes"  onLoad="iFrameHeight('mainArea')">
			 </iframe>
        </div>
        
	</div>
	<div id="slModal"></div>
</body>
</shiro:hasPermission>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/main.js"></script>
</html>
<shiro:lacksPermission name="priv_menu_dmc_05">
您没有该模块操作权限
</shiro:lacksPermission>