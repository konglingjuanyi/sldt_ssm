<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
</script>
<meta http-equiv="Content-Type" content="te	xt/html; charset=UTF-8">
<title>数据管控应用门户-流程定义管理</title>
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

<script type="text/javascript" src="<%=request.getContextPath()%>/page/activiti/process/definition/definitionManage.js"></script>

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

li{
list-style-type:none;
}

.colUl li{
float:left;
display:block;
width:32%;
color: #fff;
line-height:1px;
}

.colUl_ li{
float:left;
display:block;
width:32%;
}

.ui-jqgrid .ui-jqgrid-hdiv{
height:65px;
}

.ui-jqgrid-sortable{
top:0px;
}
</style>
</head>

<body style="background-color: #F5F5F5;">
	<div class="gf_all">
			<div class="alert alert-info">
				<form name="searchForm" class="form-inline">
						<label for="procDefName_label">流程配置名称：</label> <input type="text" id="procDefName" name="procDefName" class="input-small span3" placeholder="请输入流程名称...">
					 <button class="button button-info ipt_button" type="button" id="seachDefinitionButton"><i class="fa fa-search icolor"></i> 查 询</button>
				</form>
			</div>
			
			<table id="grid-table-definition"></table>
			<div id="grid-pager-definition"></div>
	</div>
</body>
</html>	