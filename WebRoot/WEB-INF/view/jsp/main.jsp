<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>

<%@ include file="/page/sm/drc/head.jsp"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
			
</head>
<body>

	<div class="row-fluid">
        
		<div class="span12">
			 <iframe  id="mainArea" name="mainArea" src="<%=request.getContextPath()%>/page/sm/sysmgr/smContent.jsp" width="100%"  
			 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" 
			 allowtransparency="yes"  onLoad="iFrameHeight('mainArea')">
			 </iframe>
        </div>
        
	</div>
	
	<div id="slModal"></div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/main.js"></script>
</html>