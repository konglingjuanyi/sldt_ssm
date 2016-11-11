<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
		  window.location.href="${hzbankcasUrl}";
		  </script>	
<%
 }else{
%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="Shortcut Icon" href="favicon.ico"> 
<title>数据管控平台-登录</title>
<link href="css/sso.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="public/jquery/jquery.min.js"></script>
 <script type="text/javascript" src="public/jqueryLayout/js/jquery-ui-1.10.2.min.js"></script>
<SCRIPT src="js/bjLogin.js" type=text/javascript></SCRIPT>
<script language="JavaScript"> 
	  var loginedUsername = new Array();
        //如果iframe子窗口为当前登录界面，则将整个界面定位为登录界面，修正超时情况下子窗口显示为登录界面的情况
		if(top.location.href!=self.location.href){
			     top.location.href=self.location.href;
			     top.location.href="http://dmp.hz.cn:8080/ssm/login";
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
			 
			 
			   /**
				 * 用户登录
				 **/
				function userLogin(){
					var username = document.getElementById("username").value;
					var password = document.getElementById("password").value;
					if(username=="" || password==""){
					alert("用户和密码必须输入！");
					return;
					}
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
				
	
	
	function getBasePath(){
	    var n=""+window.document.location;
	    var schemaCharIdx=n.indexOf("//");
	    if(schemaCharIdx!=-1){
	    	schemaCharIdx+=2;
	    }
	    var webpath=n.substring(0,n.indexOf("/",schemaCharIdx));
        return webpath;
    }
    //得到pas根路径
   var basePath =  getBasePath()+"/pas";
  
   //最新公告获取
   function getNewAnnounces(){
   // alert(basePath+"/statics/announceRelStatiServlet");
   /*  $.post(  
      basePath+"/statics/announceRelStatiServlet",  
      {},  
      function (data){   //回调函数
        //alert(data.al.length);
        if(data && data.al){
         var al = data.al;
         var marqueedivHtml = "<ul>";
            if(al.length>3){
	            for(var i=0;i<3;i++){
	            marqueedivHtml += "<li><a href='javascript:void(0)' onclick= displayAnnounceItem('"+al[i].id+"')>"+al[i].title+"</a></li>";
	            }
            }else{
              for(var i=0;i<al.length;i++){
               //alert(al[i].title);
                marqueedivHtml += "<li><a  href='javascript:void(0)' onclick= displayAnnounceItem('"+al[i].id+"')>"+al[i].title+"</a></li>";
               }
            }
            marqueedivHtml += "</ul>";
           $("#marqueediv").html(marqueedivHtml);
           new Marquee("marqueediv",0,1,260,118,30,3000,500,80);
        }
       
    });	 */
   }
			
	function displayAnnounceItem(announceId){
	       //alert(announceId);
	       var basePath =  getBasePath()+"/pms";
           window.open(basePath+"/bieeAC/usermgr/announceShow.jsp?announceId="+announceId);
           }
				
		</script>
</head>

<body onload="getNewAnnounces()">

<div id="loginMain">
  <div id="loginMid">
    <div id="member">
     <form:form method="post" id="fm1" name="memberLogin"  commandName="${commandName}" htmlEscape="true" onkeydown="if(event.keyCode==13)userLogin();">
                 <div id="errorMsg" class="texttop">&nbsp;
						<form:errors path="*" id="msg" cssClass="errors_msg" element="span" />
						&nbsp;
					</div>
					 <input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
					<div class="user">
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" class="text" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>

						<c:if test="${empty sessionScope.openIdLocalId}">
						<form:input cssClass="txtBox1" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
				   </div>
					
				  <div class="pass">
						<form:password cssClass="txtBox2" cssErrorClass="error" id="password" size="25"  tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
				   </div>
         
		   <div style="float:left; width:245px;">
          <p class="btnLogin"><a href="javascript:userLogin()"  onFocus=this.blur()></a></p>
          <p class="btnReturn"><a href="javascript:reset()"  onFocus=this.blur()></a></p>

		 <%
		 if(!isAutoLoginbyUas){
		 %> 
		   <a href="${hzbankcasUrl}">通过第三方SSO登录</a> <br/>  
		 <%}%>
        </div>
      </form:form>
      <!-- <div class="info">
        <div style="margin-top:50px;">
          <div id=marqueediv>
           
          </div>
        </div>
      </div> -->
    </div> 
    
    
   <!--  <div id="contactInfo">支持电话：资总(13928656364)、开水(13822165220)、陈波(13480278372)</div>  --> 
   
  </div>
</div>
</body>
</html>
<%}%>