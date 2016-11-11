<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title>数据管控平台</title> 
	<link href="css/biee_css.css" rel="stylesheet" type="text/css" />
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
	<script language="JavaScript"> 
	  var loginedUsername = new Array();
        //如果iframe子窗口为当前登录界面，则将整个界面定位为登录界面，修正超时情况下子窗口显示为登录界面的情况
		if(top.location.href!=self.location.href){
			     //top.location.href=self.location.href;
			    // top.location.href="http://dmp.hz.cn:8080/ssm/login";
			 }
		</script>
	</head>
    <body >
<script>
  /**
 * 用户登录
 **/
function userLogin(){
	var username = document.getElementById("username").value;
    var expire = "";   
    expire = new Date((new Date()).getTime() +60 * 24 * 3600000); //cookie值保存60天
    expire = "; expires=" + expire.toGMTString();   
    //alert(expire);
	var name = "teller_username"+(new Date()).getTime();
	var lastLoginUser="teller_lastusername";
    document.cookie = name + "=" + escape(username) + expire; 
    document.cookie = lastLoginUser + "=" + escape(username) + expire; 
    initUserInfo();
    
    document.all["fm1"].submit();
}
window.onload = initUserInfo;

function initUserInfo(){
	
        var cookieStr = document.cookie;
		//alert("1111:  "+cookieStr);
        var per_name = "teller_username";
        var teller_lastusername = "";
		var params = cookieStr.split(";") 
		for(var i=0;i<params.length;i++){ 
			var subParams = params[i].split("="); 
			//alert(subParams[0]+">>>"+subParams[1]);
			var offset = subParams[0].indexOf(per_name);
			if(offset!=-1){
			   addUsername(unescape(subParams[1]));
			}else if(subParams[0].indexOf('teller_lastusername')!=-1){
			  teller_lastusername = unescape(subParams[1]);
			}
		}  
		if($("#username" )){
			$("#username" ).autocomplete({source:loginedUsername}); 
		}
		document.getElementById("username").value = teller_lastusername;
}

function  addUsername(username){
	var falg = true;
	//如果已经存在的账号，则不添加
	for(var i=0;i<loginedUsername.length;i++){
	  if(username == loginedUsername[i]){
		  falg = false;
		  break;
	  }
	}
	if(falg){
		//alert("2222:  "+ username);
        loginedUsername.push(username);
	}
	//alert("changdu:  "+loginedUsername.length);
}

/**
 * 重新设置登录输入信息
 *
 */
function reset(){
	//$("#errorMsg").html("请重新输入用户和密码");
	 document.getElementById("password").value="";
	 document.getElementById("username").value="";
}

</script>

      <!--  <div id="outer">
  <div id="middle"> -->
       <div class="iner_out"> 
          <div class="iner">
             <div class="login">
              

				 <form:form method="post" id="fm1"   commandName="${commandName}" htmlEscape="true" onkeydown="if(event.keyCode==13)userLogin();">
                 
                   <div id="errorMsg" class="texttop">
					  
						<form:errors path="*" id="msg" cssClass="errors" element="div" />
					</div>
                   <div class="user">
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" class="text" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>

						<c:if test="${empty sessionScope.openIdLocalId}">
						<form:input cssClass="text" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
				   
				   </div>
                   <div class="pass">
						
						<form:password cssClass="text" cssErrorClass="error" id="password" size="25"  tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
				   </div>
                   <div class="login_button">
				   		<input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
						
                      <ul class="menu">
                      <li><a href="javascript:userLogin()"  class="on" onFocus=this.blur()></a></li>
                      <li><a href="javascript:reset()"  class="repeat" onFocus=this.blur()></a></li>
                      </ul>
                   </div>
               </form:form>
             </div>
	     </div>
     </div>
    </body>
</html>