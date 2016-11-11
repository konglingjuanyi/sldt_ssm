<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/page/sm/commonLinkScript.jsp"%>
</head>
<body>
    <form id="userInfoFrom" class="form-horizontal">
         <div id="if_userId_div" class="control-group">
              <label class="control-label" >用户编号<font color="red">*</font>：</label>
                   <div class="controls">
                       <input class="input-xlarge" id ="if_userId" name="userId" value="" type="text" placeholder="请输入用户编号">
                   </div>
                   </div>
                   <div class="control-group hide">
                    <label class="control-label">机构编号：</label>
                    <div class="controls">
                     <input class="input-xlarge" id ="if_orgId" name="orgId" value="" type="text" placeholder="请输入机构编号">
                        </div>
                    </div>
                    <div class="control-group hide">
                        <label class="control-label" >所属机构：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_orgNum" name="orgNum" value="" type="text" placeholder="请输入所属机构">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >姓名<font color="red">*</font>：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_userName" name="userName" value="" type="text" placeholder="请输入姓名">
                        </div>
                    </div>
                    <div id="if_logonPwd_div" class="control-group">
                        <label class="control-label" >登录密码<font color="red">*</font>：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_logonPwd" name="logonPwd" value="" type="password" placeholder="请输入登录密码">
                        </div>
                    </div>
                    <div id="if_logonPwd_div_" class="control-group">
                        <label class="control-label" >确认密码<font color="red">*</font>：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_logonPwd_" name="logonPwd_" value="" type="password" placeholder="请再次输入登录密码">
                        </div>
                    </div>
                   <!--  <div class="control-group">
                        <label class="control-label" >证件类型：</label>
                        <div class="controls">
                            <select id="if_certType" name="certType" class="span2">
                                <option value="0">身份证</option>
                                <option value="1">户口本</option>
                                <option value="2">护照</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >证件号码：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_certNum" name="certNum" value="" type="text" placeholder="请输入证件号码">
                        </div>
                    </div> -->
                    <div class="control-group">
                        <label class="control-label" >用户状态：</label>
                        <div class="controls">
                            <select id="if_userStat" name="userStat" class="span2">
                                <option value="1">未启用</option>
                                <option value="0">已启用</option>
                                <option value="2">作废</option>
                                <option value="5">暂时停用</option>
                                <option value="6">锁定</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >联系电话：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_contTel" name="contTel" value="" type="text" placeholder="请输入联系电话">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >邮箱地址：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_contEmail" name="contEmail" value="" type="text" placeholder="请输入邮箱地址">
                        </div>
                    </div>
                </form>
           <div>
                <div style="height: 50px"></div>
               <div class="form-actions" align="center" style="margin-top: 100px">
                  <button id="saveBtn" class="button button-info ipt_button" onclick="rtSlModal()"><i class="fa fa-save icolor"></i> 确 定</button>
                  <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
               </div>
           </div>
</body>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var context = "<%=request.getContextPath()%>";
var userId = "<%=request.getParameter("userId")== null ? "" :new String(request.getParameter("userId")) %>";
var userName = "<%=request.getParameter("userName")==null?"":URLDecoder.decode(request.getParameter("userName"), "UTF-8")%>";
var certType = "<%=request.getParameter("certType")==null?"":URLDecoder.decode(request.getParameter("certType"), "UTF-8")%>";
var certNum = "<%=request.getParameter("certNum")==null?"":URLDecoder.decode(request.getParameter("certNum"), "UTF-8")%>";
var userStat = "<%=request.getParameter("userStat")==null?"":URLDecoder.decode(request.getParameter("userStat"), "UTF-8")%>";
var contTel = "<%=request.getParameter("contTel")== null ? "" :new String(request.getParameter("contTel")) %>";
var contEmail = "<%=request.getParameter("contEmail")== null ? "" :new String(request.getParameter("contEmail")) %>";
var orgId = "<%=request.getParameter("orgId")== null ? "" :new String(request.getParameter("orgId")) %>";
var orgName = "<%=request.getParameter("orgName")== null ? "" :URLDecoder.decode(request.getParameter("orgName"), "UTF-8") %>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/user/js/addOrUpdateUser.js"></script>   
</html>