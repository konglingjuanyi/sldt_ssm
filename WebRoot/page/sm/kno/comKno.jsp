<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>知识库</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryLayout/jquery.layout-latest.js"></script>
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
</script>
<style type="text/css">
	.show{position:relative ;}
	.hidden{
		position: absolute;
		top: 20px;left: 0;
		background-color:#eee;
		border:1px solid #000;
	}
	.hidden a{display: inline-block;color: #ff0099;}
	.nav {
	  font-size: 13px;
	  font-family: inherit;
	}
	.dropdown-menu {
	  min-width: 120px;
	}	
</style>
</head>
<body>

<div class="ui-layout-west">
	      <ul class="nav nav-pills" style="float:left;width: 80px;">
			<li class="dropdown all-camera-dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="fa fa-database icolor"></i>操作
					<b class="caret"></b>
				</a>
			 <ul class="dropdown-menu" >
				<li>
					<a href="#" onclick='openKonCatsDetail("新增","add");'><i class="fa fa-plus icolor"></i> 新增分类</a>
				</li>
				<li>
					<a href="#" onclick='openKonCatsDetail("修改","update");'><i class="fa fa-pencil icolor"></i> 修改分类</a>
				</li>
				<li>
					<a href="#" onclick="deteleKonCats();"><i class="fa fa-trash-o icolor"></i> 删除分类</a>
				</li>
			</ul>
		</ul>
		<ul class="nav nav-pills" >
				<li>						
					<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick='reflash();'><i class="fa fa-refresh icolor"></i> 刷新</a>
				</li>
		</ul>
		
		<ul id="tree" class="ztree"></ul>
</div>
	
<div   class="ui-layout-center">
		<div id="content">
			<form  id="searchForm" name="searchForm" class="form-inline icolor">
		    	<div class="control-group controls-row">
		    		<label class="span2">内容标题：</label>
		    		<input class="span3" type="text" id="kncntTitle" placeholder="请输入内容标题...">

		    		<label class="span2">关键字：</label>
		    		<input class="span3" type="text" id="kncntKey" placeholder="请输入关键字...">
		    		</div>
		    	<div class="control-group controls-row">
		    		<label class="span2">全字段查询：</label>
		    		<input class="span3" type="text" id="allField" placeholder="请输入全字段...">
		    	</div>
		    	<div class="control-group controls-row" align="center">
		    		<button type="button" class="button button-info" onclick="doSearch()" ><i class="fa fa-search icolor"></i> 查询</button>
					<button type="reset" class="button button-info"><i class="fa fa-undo icolor"></i> 重置</button>
		    	</div>
			</form>
	</div>
	<div>
		<table id="grid-table"></table>
		<div id="grid-pager"></div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$('body').layout({ applyDemoStyles: true });
	});
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/kno/js/comKno.js"></script>	
</body>

</html>
