
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>数据管控平台</title> 
	<link href="css/biee_css.css" rel="stylesheet" type="text/css" />
	</head>

    <body>
	<script>
	/**
 * 重新定向到登录界面
 *
 */
function reLogin(){
	 window.location.href="./login";
}

	</script>
       <div class="iner_out"> 
          <div class="iner_leave">
             <div class="login_leave">
                   <div class="icon" > <a href="javascript:reLogin()" class="on_leave" onFocus=this.blur()></a></div>
                      
                 
             </div>
           </div>
     </div>
		<div id="msg" class="success">
			<h2><spring:message code="screen.logout.header" /></h2>

			<p><spring:message code="screen.logout.success" /></p>
			<p><spring:message code="screen.logout.security" /></p>
		</div>
    </body>
</html>

