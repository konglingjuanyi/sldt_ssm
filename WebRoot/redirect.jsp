<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
//禁止浏览器缓存
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);

String redirectURL =(String)request.getSession().getAttribute("CGB_SSO_RedirectURL");
if(redirectURL!=null&&redirectURL.length()>0){//有值，取到后清空，避免相互影响
  request.getSession().removeAttribute("CGB_SSO_RedirectURL");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
  <head>
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
    <script type="text/javascript" language="javascript">
      <!--  -->
        window.location.replace ("<%=redirectURL%>");
    </script>
    <title>重定向</title>
  </head>
  <body></body>
</html>