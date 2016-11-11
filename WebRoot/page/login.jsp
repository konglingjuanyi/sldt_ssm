<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/login/css/login.css"/>
<title>欢迎登陆  数据管控平台</title>
<style type="text/css"> 

ul, li{
	margin:0;
	padding:0;
}
input{
	border:1px solid #908f8f;
	height:30px;
	padding: 0 5px;
	border-radius: 5px;
}
.inputover{
	border:1px solid #4792b9;
	background-color:#fff;
}
.inputout{
	border:1px solid #908f8f;
}

</style>

<script>
    function check() {
        if (myform.loginId.value == "") {
            alert("用户帐号不能为空!");
            return  false;
        }
        if (myform.password.value == "") {
            alert("登陆密码不能为空!");
            return  false;
        }
        myform.submit();        
    }
    
    function toReset() {
        myform.reset();
    }
    
  //判断当前窗口是否有顶级窗口，如果有就让当前的窗口的地址栏发生变化
	function loadTopWindow(){ 
		if (window.top!=null && window.top.document.URL!=document.URL){ 
			window.top.location= document.URL; 
		} 
	} 
</script>

</head>
<body onload="loadTopWindow()">
<FORM name=myform method="post" action="<%=request.getContextPath()%>/smlogin.do" onkeydown=if(event.keyCode==13)check();>
<div id="contaniner">
	<div id="login-logo">
		<a href="/page/login.jsp"><img src="public/login/img/login-logo.png" /></a>
	</div>
	<div id="main-content">
    	<div class="centertable">
			<table width="100%" cellpadding="0" cellspacing="0" class="content" align="center">
			  <tr>
				<td align="right">用户名：</td>
				<td align="left"> 
					<input type="text" name="loginId" style="width: 190px;" value="" id="loginId" onMouseOver="this.className='inputover'" onMouseOut="this.className='inputout'"/></td>
			  </tr>
			  <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td align="right">密&nbsp;&nbsp;&nbsp;码：</td>
				<td align="left">
					<input type="password" name="password" style="width: 190px;" id="password" onMouseOver="this.className='inputover'" onMouseOut="this.className='inputout'"/></td>
			  </tr>
			  <tr height="50">
				<td>&nbsp;</td>
				<td align="left"><span><input type="checkbox" id="remember_me" name="rememberMe"/>&nbsp;记住密码</span></td>
			  
			  </tr>
			  <tr>
			  <td>&nbsp;</td>
			  <td align="left"><span>&nbsp;&nbsp;<font color=red >${requestScope.message}</font></span></td>
			  </tr>
			  <tr height="40">
				  <td>&nbsp;</td>
				  <td align="left">
					  <input type="button"  onmouseover="this.className='btn-over'" onMouseOut="this.className='btn'" class="btn" value="登 录" onclick="check()"/>
				  </td>						
			  </tr>
			 
			</table>
		</div>
	</div>
</div>
</FORM>
<div id="footer">版权所有：深圳长亮科技股份有限公司© 2015  建议使用IE8及以上版本浏览器和1024*768以上屏幕分辨率</div>
</body>
</html> 