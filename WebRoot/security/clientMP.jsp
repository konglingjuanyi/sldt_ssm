<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String context = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据管控平台-修改密码</title> 

<script language="javascript">
alert("密码已经过期(或系统初使化密码),请立刻修改密码!");
window.location.href="<%=context %>/security/modifyPasw.jsp";
</script>

</head>
<body>

</body>

</html>
