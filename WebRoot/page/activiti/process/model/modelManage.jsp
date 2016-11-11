<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管控应用门户-我的变更申报</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/tree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/slwin.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/sltab.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/main.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryLayout/jquery.layout-latest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/fwk.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/common/js/heigthUtil.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/page/activiti/process/model/modelManage.js"></script>
<!-- 调整表头样式 --->
<style type="text/css">
.defbtn{
width:60px;
height:28px;
border-radius:5px;
border:none;
-webkit-border-radius:5px;
color:#FFF;
background-color:red;
}

input[type=button]{
margin:0 10px 10px 0;
}

ul > li{
list-style:none;
}
</style>
</head>

<body style="background-color: #F5F5F5;">
	<div class="gf_all">
			<div class="alert alert-info">
				<form name="searchForm" class="form-inline">
						<label for="name_label">模型名称：</label> <input type="text" id="name" name="name" class="input-small span3" placeholder="请输入模型名称...">
					 <button class="button button-info ipt_button " type="button" id="seachModelButton"><i class="fa fa-search icolor"></i> 查 询</button>
				</form>
			</div>
			<table id="grid-table-model"></table>
			<div id="grid-pager-model"></div>
		
	</div>
	
	<!-- unuse -->
	<div class="modal hide fade" id="addModal" tabindex="-1" role="dialog">
		<div class="modal-header"><button class="close" data-dismiss="modal">x</button>
			<h3 id="addModalLabel">添加模型</h3>
		</div>
		<form id="add-modal-form" action="<%=request.getContextPath()%>/activiti/processModel.do?method=addProcessModel" method="post">
		<div class="modal-body">
	  	  	<div id="editDiv">
				<div class="form-group">
					<div class="input-group">
						<ul class="list-group">
						<li class="list-group-item">
							<strong>名称(不支持中文)：</strong>
							<input type="text" class="form-control" style="width:380" name="name" id="name">
							<span style="color:red;" id="name-span"></span>
						</li>
						<li class="list-group-item">
							<strong>key(不支持中文)：</strong>
							<input type="text" class="form-control" style="width:380" name="key" id="key">
							<span style="color:red;" id="key-span"></span>
						</li>
						<li class="list-group-item">
							<strong>描述：</strong>
							<textarea id="description" style="width:400px;" class="form-control" name="description"></textarea>
							<span style="color:red;" id="des-span"></span>
						</li>
						<ul>
						</ul>
				</div>
			</div>
			
			<div>
		  	  	<input tyle="button" style="margin-left:32%" type="button" value="提交" id="saveModelButton" class="btn button-info ipt_button">
		  	  	<input tyle="button" type="button" value="重置" id="resetModelButton" class="btn button-info ipt_button">
		  	  	<input tyle="button" type="button" value="撤销" id="backModelButton" class="btn button-info ipt_button">
			</div>
		
		</div>
	</div>
	</form>
	</div>
</body>
</html>	