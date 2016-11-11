<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
String context = request.getContextPath();
String hostContext = context.substring(0, context.lastIndexOf("/"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据管控平台-密码修改成功</title> 
	<link href="css/amend.css" rel="stylesheet" type="text/css" />
	<!-- 
    <script type="text/javascript" src="<%=context %>/common/base_parametes.js"></script>
    -->
</head>
<body>

<div class="iner_out"> 
          <div class="iner">
          <div class="iner_head"><img src="images/amend_logo.jpg" border="0" /><div class="info"><a href="javascript:toPortal()">返回</a></div></div>
          <div class="content_right">
           <div class="right">
            <img src="images/right_text.png" width="161" height="32" /></div>
          
          </div>

</div>
       </div>
</body>
<script language="javascript">
var baseAppPath = "<%=hostContext%>";
//返回门户
function toPortal(){
	window.location.href=baseAppPath + "/bip/portal";
}
</script>
</html>
