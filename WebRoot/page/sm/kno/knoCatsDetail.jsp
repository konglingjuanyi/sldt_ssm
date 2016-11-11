<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/fwk.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">
var context = "<%=request.getContextPath()%>";
var option = '<%=request.getParameter("option")%>';//update,add
</script>
</head>
<body>
		           
	<!-- 模态框 -->
	<div id="dsInfoModal" >

		<div style="margin-right: 10px;margin-left: 10px;">
			<form role="form"  class="form-horizontal">
				<input type="hidden" id="kncatId" name="kncatId" value="${vo.kncatId }" >
				 <div class="control-group" >
				 	<label for="kncatName" class="control-label">
				 		分类名称<font color="red">*</font>:
				 	</label>
				 	<input class="input-xlarge" id="kncatName"  name="kncatName" type="text" value="${vo.kncatName }" >
				 </div>
				 <div class="control-group" >
				 	<label for="kncatName" class="control-label">
				 		分类描述:
				 	</label>
				 	<textarea class="input-xlarge" rows="3" id="kncatDesp" name="kncatDesp">${vo.kncatDesp }</textarea>
				 </div>
			</form>
		</div>
		<div class="form-actions" style="margin-top: 75px;">
			<button onclick="saveKnoCat();" id="saveUpdateBtn"  ><i class="fa fa-save icolor"></i> 保存</button>
			<button onclick="closWindow();" id="cancelBtn" ><i class="fa fa-undo icolor"></i> 取 消</button>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/kno/js/knoCatsDetail.js"></script>
</html>