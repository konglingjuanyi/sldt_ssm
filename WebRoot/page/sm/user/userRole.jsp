<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="/page/sm/commonLinkScript.jsp"%>   
	
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>"; 
	var userId = "<%=request.getParameter("userId")%>";
</script>

</head>
<body>
	<div>
 		<ul id="userRoleTree" class="ztree"></ul>
 	</div>
  <div>
 	<div style="height: 50px"></div>
 	<div class="form-actions">
         <button id="userRoleBtn" class="button button-info ipt_button" onclick="rtCfgModal()"><i class="fa fa-save icolor"></i> 确 定</button>
         <button class="button button-info ipt_button" data-dismiss="modal" onclick="closWindow()"><i class="fa fa-close icolor"></i> 取 消</button>
    </div>
  </div>	
</body>
	<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/user/js/userRole.js"></script>
</html>