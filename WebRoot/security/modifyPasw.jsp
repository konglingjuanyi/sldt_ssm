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
	<title>数据管控平台-修改密码</title> 
	<link href="css/amend.css" rel="stylesheet" type="text/css" />
<!-- extjs 基本脚本引入 -->
  <!--  <script type="text/javascript" src="<%=context %>/common/base_parametes.js"></script>
  -->
 	<script type="text/javascript" src="<%=context %>/common/extjs/js/ext-base.js"></script>
    <script type="text/javascript" src="<%=context %>/common/extjs/js/ext-all.js"></script>
    <script type="text/javascript" src="<%=context %>/common/extjs/shared/sharecomm.js"></script>

<script language="javascript">
var baseAppPath = "<%=hostContext%>";

//新旧密码比较
function comparePwdOldNew(){
	var pwdbefore=document.getElementById("password_before").value;
	var pwdnew=document.getElementById("password_new").value;
	if(pwdbefore==pwdnew){
		var span_tipmsg=document.getElementById("tipmsg");
		span_tipmsg.innerHTML ="<font color='#ba1021'>新密码不得与原密码相同</font>";
		return false;
	}
	return true;

}


//检查用户编号
function checkuserid(){
	var uid=document.getElementById("userid").value;
	var span_userid=document.getElementById("span_userid");
	if(uid==""){
		span_userid.innerHTML =  "<font color='#ba1021'>请输入用户编号</font>";
		return false;
	}else{
		span_userid.innerHTML =  "<font color='green'>用户编号已输入</font>";
		return true;
	}
}

//检查原密码
function checkpwdold(){
	var s=document.getElementById("password_before").value;
	var span_pwdold=document.getElementById("span_pwdold");
	if(s==""){
		span_pwdold.innerHTML =  "<font color='#ba1021'>请输入原密码</font>";
		return false;
	}else{
		span_pwdold.innerHTML =  "<font color='green'>原密码已输入</font>";
		checkpwd();
		return true;
	}

}

//显示密码强弱信息 0:初始1:弱2:中3:强
function showPwdStrongInfo(type){
	var pwd_low=document.getElementById("pwd_low");
	var pwd_mid=document.getElementById("pwd_mid");
	var pwd_high=document.getElementById("pwd_high");
	if(type==0){
		pwd_low.style.background = "#D2D2D2";//灰
		pwd_mid.style.background = "#D2D2D2";
		pwd_high.style.background = "#D2D2D2";
	}else if(type==1){
		pwd_low.style.background = "#CD0027";//红
		pwd_mid.style.background = "#D2D2D2";
		pwd_high.style.background = "#D2D2D2";
	}else if(type==2){
		pwd_low.style.background = "#F0BF22";//黄
		pwd_mid.style.background = "#F0BF22";
		pwd_high.style.background = "#D2D2D2";
	}else if(type==3){
		pwd_low.style.background = "#49B545";//绿
		pwd_mid.style.background = "#49B545";
		pwd_high.style.background = "#49B545";
	}
}

//用户密码检查
function checkpwd(){
var s=document.getElementById("password_new").value;
var span_tipmsg=document.getElementById("tipmsg");
var userid = document.getElementById("userid").value;
checkpwdnew();
//长度校验
if(s.length==0){
	showPwdStrongInfo(0);
	span_tipmsg.innerHTML = "8-20位，须同时包含大写字母、小写字母、数字和特殊字符中的三种";
    return false;
}else if(s.length <8){
	showPwdStrongInfo(0);
	span_tipmsg.innerHTML = "<font color='#ba1021'>密码长度不对：小于8位</font>";
    return false;
}else if(s.length >20){
	showPwdStrongInfo(2);
	span_tipmsg.innerHTML = "<font color='#ba1021'>密码长度不对：大于20位</font>";
    return false;
}
//复杂度校验
var ls = 0;
if (s.match(/[a-z]/g)){
	ls++;
}
if (s.match(/[0-9]/g)){
	ls++;
}
if (s.match(/[A-Z]/g)){
	ls++;
}
if (s.match(/(.[^a-z0-9])/ig)){
	ls++;
}

if(ls<3){
	if(s.length<=20){
			showPwdStrongInfo(ls);
	}
	span_tipmsg.innerHTML = "<font color='#ba1021'>须同时包含大写字母、小写字母、数字和特殊字符中的三种</font>";
	return false;
}
if(!comparePwdOldNew()){
	return false;
}
	if(userid==s){
		span_tipmsg.innerHTML = "<font color='#ba1021'>新密码不得与用户账号相同</font>";
		return false;
	}

	showPwdStrongInfo(3);
	span_tipmsg.innerHTML = "<font color='green'>密码格式正确</font>";
return true;
}

//重置
function reset(){
	document.getElementById("userid").value="";
	document.getElementById("password_before").value="";
	document.getElementById("password_new").value="";
	document.getElementById("password_newrepeat").value="";
	checkuserid();
	checkpwdold();
	checkpwd();
	checkpwdnew();
}


//检查新密码
function checkpwdnew(){
	var newrepeat = document.getElementById("password_newrepeat").value;//确认密码
	var span_newrepeat = document.getElementById("newrepeat_msg");
	if(newrepeat==""){
		span_newrepeat.innerHTML = "<font color='#ba1021'>请输入确认密码</font>";
		return false;
	}
	var newpwd = document.getElementById("password_new").value;
	if(newrepeat == newpwd){
		span_newrepeat.innerHTML = "<font color='green'>确认密码与新密码一致</font>";
		return true;
	}else{
		span_newrepeat.innerHTML = "<font color='#ba1021'>确认密码与新密码不一致</font>";
		return false;
	}
}

//页面提交检查
function changePwd(){
	if(checkuserid()&&checkpwdold()&&checkpwd()&&checkpwdnew()){
		var userId = document.getElementById("userid").value;
		var old_password = document.getElementById("password_before").value;
		var new_password = document.getElementById("password_new").value;
		//alert("满足提交条件->"+userId+"---"+old_password+"---"+new_password);
		Ext.Ajax.request({
			url : baseAppPath + '/pms/teller/tellerUpdatePaswServlet',
			params : {
				tellerno:userId,
				tellerPwd:old_password,
				tellerNewPwd:new_password
			},
			success : function(resp, opts) {
			   var respText = Ext.util.JSON.decode(resp.responseText);
			  // alert(respText.code);
				if(respText.code=='0000'){
					//alert("密码修改成功！");
					window.location.href="modifyPasw_sure.jsp";
				}else{
					window.location.href="modifyPasw_wrong.jsp";
				}
			},
			failure : function(res) {
				alert('服务器出现错误请稍后再试');
			}
		});
	}else{
		//alert("请检查输入");
	}

}



//返回门户
function toPortal(){
	window.location.href=baseAppPath + "/bip/portal";
}

</script>

</head>
<body>

<div class="iner_out"> 
          <div class="iner">
          <div class="iner_head"><img src="images/amend_logo.jpg" border="0" /><div class="info"></div></div>
          <div class="content">
           <div class="login">
          <div id="errorMsg" class="texttop"><input onkeyup="this.value=this.value.replace(/[^\w\u4E00-\u9FA5]/g,'');" type="text" class="text" id="userid" name="userid" onKeyUp="checkuserid()" /><span id="span_userid"><font color='#ba1021'> 请输入用户编号</font></span></div>
          <div class="pass_before"><input onkeyup="this.value=this.value.replace(/[^\w\u4E00-\u9FA5]/g,'');" id="password_before"  name="password_before" type="password" class="text" onKeyUp="checkpwdold()" /><span id="span_pwdold"><font color='#ba1021'> 请输入原密码</font></span></div>
          <div class="pass_new"><input onkeyup="this.value=this.value.replace(/[^\w\u4E00-\u9FA5]/g,'');" id="password_new"  name="password_new" type="password" class="text" onKeyUp="checkpwd()"/><div class="power">
  <div style="border: 0px solid black; ">
		<span style="margin-right:2px;float:left;height:7px;display:inline-block;font-size:10pt;color:#CD0027">弱</span>
		<span id="pwd_low" style="margin-right:1px;margin-top:6px;float:left;background: #D2D2D2; width:30px;height:7px;display:inline-block;"></span>
		<span id="pwd_mid" style="margin-left:2px;margin-top:6px;float:left;background: #D2D2D2;width:30px;height:7px;display:inline-block;"></span>
		<span id="pwd_high" style="margin-left:2px;margin-top:6px;float:left;background: #D2D2D2; width:30px;height:7px;display:inline-block;"></span>
		<span style="margin-left:2px;float:left; height:7px;display:inline-block;font-size:10pt;color:#49B545">强</span>
</div>
		  
		  </div>
          <!-- <span class="cue">密码比较简单，安全等级低，建议更换更复杂的密码</span>-->
          <span id="tipmsg" class="black">8-20位，须同时包含大写字母、小写字母、数字和特殊字符中的三种</span></div>
          <div class="pass_newrepeat"><input onkeyup="this.value=this.value.replace(/[^\w\u4E00-\u9FA5]/g,'');" id="password_newrepeat"  name="password_newrepeat" type="password" class="text" onKeyUp="checkpwdnew();" /><span id="newrepeat_msg"><font color='#ba1021'>请输入确认密码</font></span></div>
<div class="login_button">
                      <ul class="menu">
                      <li><a href="javascript:changePwd()" class="on" onFocus=this.blur()></a></li>
                      <li><a href="javascript:reset()" class="repeat" onFocus=this.blur()></a></li>
                      <li><a href="javascript:toPortal()" class="back1" onFocus=this.blur()></a></li>
                      </ul>
           </div>
           </div>
          
          </div>

</div>
       </div>
</body>

</html>
