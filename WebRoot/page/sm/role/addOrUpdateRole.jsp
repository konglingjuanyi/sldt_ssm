<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/jquery-validation/css/screen.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
</head>
<body>
        <form id="roleInfoFrom" class="form-horizontal">
          <input type="text" style="display: none;" id="if_setupDt" name="setupDt" value=""/>
          <div id="if_roleId_div" class="control-group">
            <label class="control-label" for="inputEmail">角色编号<font color="red">*</font>：</label>
            <div class="controls">
              <input class="input-xlarge" id ="if_roleId" name="roleId" type="text" placeholder="请输入角色编号">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="inputEmail">角色名称<font color="red">*</font>：</label>
            <div class="controls">
              <input class="input-xlarge" id ="if_roleName" name="roleName" type="text" id="inputEmail" placeholder="请输入角色名称">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" >角色描述：</label>
            <div class="controls">
              <input class="input-xlarge" id ="if_roleDesc" name="roleDesc" value="lslslsls" type="text" placeholder="请输入角色描述">
            </div>
          </div>
         <div class="control-group">
            <label class="control-label" >子系统：</label>
            <div class="controls">
              <select id="if_appId" name="appId" class="span2">
                <option value="ssm">ssm</option>
                <option value="mds">mds</option>
                <option value="dsm">dsm</option>
                <option value="dqs">dqs</option>
               <!--  <option value="ahq">ahq</option>
                <option value="biee">biee</option>
                <option value="cognos">cognos</option> -->
              </select>
            </div>
         </div>
         
          <div class="control-group">
            <label class="control-label" >备 注：</label>
            <div class="controls">
              <textarea rows="4" style="width: 280px;" id="if_memo" name="memo"  placeholder="请输入备注"></textarea>
            </div>
         </div>
          
        </form>
        <div>
    <div style="height: 50px"></div>
      <div class="form-actions" align="center">
        <button id="saveUpdateBtn" class="button button-info ipt_button" onclick="slModalFn()"><i class="fa fa-save icolor"></i> 确 定</button>
        <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
      </div>
    </div>
</body>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var context = "<%=request.getContextPath()%>";
var roleId = "<%=request.getParameter("roleId")== null ? "" :new String(request.getParameter("roleId")) %>";
var roleName = "<%=request.getParameter("roleName")==null?"":URLDecoder.decode(request.getParameter("roleName"), "UTF-8")%>";
var setupDt = "<%=request.getParameter("setupDt")==null?"":URLDecoder.decode(request.getParameter("setupDt"), "UTF-8")%>";
var roleDesc = "<%=request.getParameter("roleDesc")==null?"":URLDecoder.decode(request.getParameter("roleDesc"), "UTF-8")%>";
var memo = "<%=request.getParameter("memo")==null?"":URLDecoder.decode(request.getParameter("memo"), "UTF-8")%>";
var appId = "<%=request.getParameter("appId")== null ? "" :new String(request.getParameter("appId")) %>";

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/role/js/addOrUpdateRole.js"></script>   
</html>