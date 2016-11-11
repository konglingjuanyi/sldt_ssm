<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
<style type="text/css">

</style>
</head>
<body>
        <form id="add-modal-form" action="<%=request.getContextPath()%>/activiti/processModel.do?method=addProcessModel" method="post" class="form-horizontal">
          <input type="text" style="display: none;" id="if_setupDt" name="setupDt" value=""/>
          <div id="if_roleId_div" class="control-group">
            <label class="control-label">名称<font color="red">*</font>：</label>
            <div class="controls">
              <input class="input-xlarge" id ="if_name" name="name" type="text" placeholder="请输入模型名称">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">Key值(不支持中文)<font color="red">*</font>：</label>
            <div class="controls">
              <input class="input-xlarge" id ="if_key" name="key" type="text" id="key" placeholder="请输入key值">
            </div>
          </div>
         
          <div class="control-group">
            <label class="control-label" >描述：</label>
            <div class="controls">
              <textarea rows="4" style="width: 280px;" id="if_description" name="description"  placeholder="请输入描述"></textarea>
            </div>
         </div>
        </form>
        <div>
    <div style="height: 50px"></div>
      <div class="form-actions" align="center">
        <button id="saveModelButton" class="button button-info ipt_button"><i class="fa fa-save icolor"></i> 确 定</button>
        <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
      </div>
    </div>
</body>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/activiti/process/model/addModel.js"></script>   
</html>