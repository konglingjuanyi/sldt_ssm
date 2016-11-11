<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String context = request.getContextPath();
     org.pac4j.hzbanksso.client.CasClient hzbankcasClientObj = (org.pac4j.hzbanksso.client.CasClient)com.sldt.framework.common.SpringContextHelper.getBean("hzbankcas");
		  // web context
        final org.pac4j.core.context.WebContext webContext = new org.pac4j.core.context.J2EContext(request, response);
		
		 final String casLogoutUrl =hzbankcasClientObj.getCasLogoutUrl();
	String hzbankcasLoginUrl=casLogoutUrl;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="js/jquery.js"></script>
<script>
		  	 var baseAppPath = "<%=context%>";
		  	 function clearSSO(){
               clearSession();//清理服务器session信息
             }

//清理服务器session信息
function clearSession(){
      $.post(  
		 baseAppPath+"/jsapi/logoutServlet",  
		 {type:'ssoLogOut'},                                    
		 function (data){   //回调函数
		    if(afterCleanSession){
			   afterCleanSession();
			}
		 }
	  );
}

//清理浏览器cookie信息
function clearCookies(){

	 var cookieStr = document.cookie;
	 //alert("清理前："+cookieStr);
     var params = cookieStr.split(";") 
     var expire = ""; 
     expire = new Date((new Date()).getTime() -1000); //设置一个超时的cookie日期
     expire = "; expires=" + expire.toGMTString();   
     for(var i=0;i<params.length;i++){ 
		var subParams = params[i].split("="); 
		var curCookie = subParams[0] + "=" + expire; 
		//alert(curCookie);
		document.cookie =curCookie;
	  } 
  var cookieStr = document.cookie;
  //alert("清理后:  "+cookieStr);
}
</script>




<%
 		    boolean isAutoLoginbyUas=false;
		 	String isLoginPas=request.getParameter("isLoginPas");
		 	com.sldt.dmc.ssm.cgbsso.web.CgbssoConfigMgr ssoCfgMgr=(com.sldt.dmc.ssm.cgbsso.web.CgbssoConfigMgr)com.sldt.dmc.ssm.web.init.BeanUtils.getBean("cgbssoConfigMgr");
		 	if(!"true".equalsIgnoreCase(isLoginPas)&&ssoCfgMgr.isAllwaysRedirectToSSO()){
		 	  isAutoLoginbyUas=true;
		    }
 %>   
 
 <%
 if(isAutoLoginbyUas){
%>

		  <script>
        function afterCleanSession(){
           clearCookies();//清理浏览器cookie信息
           doAutoLoginbyUas();
        }
		function doAutoLoginbyUas(){ 
		  window.close();
		  var newUrl="${hzbankcasUrl}";
		  if(newUrl!=null&&newUrl.length>0){
		   window.location.href=newUrl;
		  }else{
		   window.location.href="<%=hzbankcasLoginUrl%>";
		  }
	    }
	     clearSSO();
		  </script>	
<%
 }else{
%> 
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据管控平台-注销</title>
<link href="css/sso.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

<SCRIPT src="js/bjLogin.js" type=text/javascript></SCRIPT>

<script>

/**
* 重新定向到登录界面
*
*/
function reLogin(){
	     <%
	      if(!isAutoLoginbyUas){
		 %> 
		     window.location.href="./login";
		 <%}else{%> 
		     window.location.href="<%=hzbankcasLoginUrl%>";
		 <%}%>
}
</script>

		  <script>
        function afterCleanSession(){
           clearCookies();//清理浏览器cookie信息
        }
		  </script>	
		  
		  
</head>

<body onload="clearSSO()">
<div id="loginMain">
  <div id="loginoutMid">
    <div id="reTurn">
     
      
        <div style="float:left; width:245px;">
          <p class="btnLoginout"><a href="javascript:reLogin()" onFocus=this.blur()></a></p>
        
        </div>
    
      
    </div>
  </div>
</div>
</body>
</html>

<%}%>