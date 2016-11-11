<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告展示</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
</head>
<style>
.noticeTitle{
	font-size: 28px;
	font-weight: bold;
	color: black;
	text-align: center;
	width: 100%;
	margin-top: 50px;
}
.publishDiv{
	font-size: 14px;
	margin-top: 20px;
	margin-bottom:20px;
	color: gray;
	text-align: center;
	width: 100%;
}
</style>
<body>
	<input type="hidden" value="${notice.noticeId }" />
	<div class ="noticeTitle">${notice.title }</div>
	<div class ="publishDiv">发布人：${notice.publishUser}  发布时间：${notice.publishTime}</div>
	<hr/>
	<div>${notice.content }</div>
</body>

</html>