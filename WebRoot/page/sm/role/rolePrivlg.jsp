<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>  	
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	var roleId = "<%=request.getParameter("roleId")%>";
</script>

</head>
<body>
	<div>
 		<ul id="rolePrivlgTree" class="ztree"></ul>
 	</div>	
 	<div>
 	  <div style="height: 80px"></div> 
      <div class="form-actions" >
        <button id="rolePrivlgBtn" class="button button-info ipt_button" onclick="rtCfgModal()"><i class="fa fa-save icolor"></i> 确 定</button>
        <button class="button button-info ipt_button" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
      </div>
    </div>
</body>
	<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/role/js/rolePrivlg.js"></script>
</html>