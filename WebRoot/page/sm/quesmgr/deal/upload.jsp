<%@ page language="java" 	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryForm/jquery.form.js"></script>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
</head>
<body>
	<div class="container">
		<form id="fileuploadForm" name = "fileuploadForm" class="form-horizontal" enctype="multipart/form-data" method="post" >
			<div class="control-group">
				<label class="control-label" for="inputEmail">文件名称<font color="red">*</font>：
				</label>
				<div class="controls">
					<input class="input-xlarge" type="text" id="file_Name" name="fileName" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">上传文件<font color="red">*</font>：
				</label>
				<div class="controls">
					<input class="input-xlarge" type='file' id='file_Path' name="filePath" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注：</label>
				<div class="controls">
					<textarea class="input-xlarge" name="fileRemark" id="file_Remark" cols="50" rows="5"></textarea>
				</div>
			</div>
			<div class="control-group"></div>
		</form>
	</div>
	<div class="form-actions" align="center" style="margin-top: 158px">
		<button id="saveBtn" class="button button-info" onclick="save()"><i class="fa fa-save icolor"></i> 提 交	</button>
		<button id="cancelBtn" class="button button-info" onclick="closWindow()"><i class="fa fa-close icolor"></i> 关 闭	</button>
	</div>
</body>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryForm/jquery.form.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/deal/js/upload.js"></script>   
</html>