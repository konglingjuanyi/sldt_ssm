<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String context = request.getContextPath();
String hostContext = context.substring(0, context.lastIndexOf("/"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据管控平台-修改密码失败</title> 
	<link href="css/amend.css" rel="stylesheet" type="text/css" />
	<!--
	<script type="text/javascript" src="<%=context %>/common/base_parametes.js"></script>
	-->

</head>
<body>

<div class="iner_out"> 
   <div class="iner">
      	<div class="iner_head"><img src="images/amend_logo.jpg" border="0" /><div class="info"> <a href="javascript:backToModifyPasw()" >返回</a></div>
  		</div>
    	<div class="content_right">
	        <div class="wrong">
	            <div style="float:left; width:275px; height:32px;">
	            <%if(session.getAttribute("resultInfo")==null){
	            %>
	            <img src="images/wrong.png" width="275" height="32" />
	            <%
	            }else{ %>
	            <%=session.getAttribute("resultInfo")%>
	            <%} %><!-- <img src="images/wrong.png" width="275" height="32" /> --></div>
	            <div  class="login_wrong"><a href="javascript:backToModifyPasw()" class="go_on" onFocus=this.blur()></a></div>
	     	</div>          
      </div>
	</div>
</div>
</body>

<script language="javascript">

var baseAppPath = "<%=hostContext%>";

function backToModifyPasw(){
	window.location.href="modifyPasw.jsp";
}

//返回门户
function toPortal(){
	window.location.href=baseAppPath + "/bip/portal";
}
</script>
</html>
