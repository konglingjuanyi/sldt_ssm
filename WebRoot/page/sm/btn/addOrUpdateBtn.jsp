<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
          <form id="btnInfoFrom" class="form-horizontal">
                 <input type="text" style="display: none;" id="if_btnId" name="btnId" value=""/>
                 <input type="text" style="display: none;" id="if_menuId" name="menuId" value=""/>
                  <div class="control-group">
                      <label class="control-label" for="inputEmail">按钮名称<font color="red">*</font>：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_btnText" name="btnText" type="text" id="inputEmail" placeholder="请输入按钮名称">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >别名：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_btnNickname" name="btnNickname" value="" type="text" placeholder="请输入别名">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >按钮描述：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_btnDesc" name="btnDesc" value="" type="text" placeholder="请输入按钮描述">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >按钮类型：</label>
                        <div class="controls">
                            <select id="if_btnType" name="btnType" class="span2">
                                <option value="1">新增</option>
                                <option value="2">删除</option>
                                <option value="3">修改</option>
                                <option value="4">查询</option>
                                <option value="5">上传</option>
                                <option value="6">下载</option>
                                <option value="7">导入</option>
                                <option value="8">打印</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >图标：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_btnIcon" name="btnIcon" value="" type="text" placeholder="请输入图标">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >操作动作：</label>
                        <div class="controls">
                            <input class="input-xlarge" id ="if_btnAct" name="btnAct" value="" type="text" placeholder="请输入操作动作">
                        </div>
                    </div>
                </form>
          <div>
            <div style="height: 50px"></div>
            <div class="form-actions" align="center">
                <button id="saveBtn" class="button button-info ipt_button" onclick="rtSlModal()"><i class="fa fa-save icolor"></i> 确 定</button>
                <button id="cancelBtn" class="button button-info ipt_button" data-dismiss="modal" onclick="closWindow()"><i class="fa fa-close icolor"></i> 取 消</button>
            </div>
          </div>
</body>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var context='<%=request.getContextPath()%>';
var btnId = "<%=request.getParameter("btnId")== null ? "" :new String(request.getParameter("btnId")) %>";
var menuId = "<%=request.getParameter("menuId")== null ? "" :new String(request.getParameter("menuId")) %>";
var btnText = "<%=request.getParameter("btnText")==null?"":URLDecoder.decode(request.getParameter("btnText"), "UTF-8")%>";
var btnNickname = "<%=request.getParameter("btnNickname")==null?"":URLDecoder.decode(request.getParameter("btnNickname"), "UTF-8")%>";
var btnDesc = "<%=request.getParameter("btnDesc")==null?"":URLDecoder.decode(request.getParameter("btnDesc"), "UTF-8")%>";
var btnType = "<%=request.getParameter("btnType")==null?"":URLDecoder.decode(request.getParameter("btnType"), "UTF-8")%>";
var btnIcon = "<%=request.getParameter("btnIcon")==null?"":URLDecoder.decode(request.getParameter("btnIcon"), "UTF-8")%>";
var btnAct = "<%=request.getParameter("btnAct")==null?"":URLDecoder.decode(request.getParameter("btnAct"), "UTF-8")%>";

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/btn/js/addOrUpdateBtn.js"></script>   

</html>