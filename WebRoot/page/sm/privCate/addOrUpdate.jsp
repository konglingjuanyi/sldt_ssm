<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
      <form id="privCateInfoFrom" class="form-horizontal">
          <input type="text" style="display: none;" id="if_privCateId" name="privCateId" value=""/>
           <div class="control-group">
                <label class="control-label" for="inputEmail">类别名称<font color="red">*</font>：</label>
                  <div class="controls">
                        <input class="input-xlarge" id ="if_privCateName" name="privCateName" type="text" id="inputEmail" placeholder="请输入类别名称">
                   </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" >类型：</label>
                        <div class="controls">
                            <select id="if_checkType" name="checkType" class="span2">
                                <option value="0">单选</option>
                                <option value="1">多选</option>
                            </select>
                        </div>
                    </div>
                </form>
            <div class="form-actions" align="center" style="margin-top: 300px">
                <button id="saveBtn" class="button button-info ipt_button" onclick="rtSlModal()"><i class="fa fa-save icolor"></i> 确 定</button>
                <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" data-dismiss="modal"><i class="fa fa-close icolor"></i> 取 消</button>
            </div>
</body>
<script type="text/javascript">
var context='<%=request.getContextPath()%>';
var type = '<%=request.getParameter("type")%>';
var privCateId = "<%=request.getParameter("privCateId")== null ? "" :new String(request.getParameter("privCateId")) %>";
var privCateName = "<%=request.getParameter("privCateName")==null?"":URLDecoder.decode(request.getParameter("privCateName"), "UTF-8")%>";
var checkType = "<%=request.getParameter("checkType")==null?"":URLDecoder.decode(request.getParameter("checkType"), "UTF-8")%>";

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/privCate/js/addOrUpdate.js"></script>   

</html>