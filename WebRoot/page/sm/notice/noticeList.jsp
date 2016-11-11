<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
</head>
<body>
	<ul class="breadcrumb">
  		<li><a href="#"> <i class="fa fa-globe icolor"></i> 公告管理</a> <span class="divider">/</span></li>
  		<li class="active"> <i class="fa fa-th-large icolor"></i> 公告维护</li>
	</ul>
	
	<div class="alert alert-info">
    	<form name="searchForm" class="form-inline">
        	<div class="span10">
				<label for="shTitle">标题：</label> <input type="text" id="shTitle" name="shTitle" class="input-small span4" placeholder="请输入菜单名称...">
				<label for="shState">状态：</label> 
				<select  id="shState" name="shState" class="input-small span4" >
					<option value="" >所有</option>
					<option value="1" >草稿</option>
					<option value="2" >发布</option>
					<option value="3" >废弃</option>
				</select>
			</div>
			<button class="button button-primary" type="button" onclick="doSearch()"><i class="fa fa-search icolor"></i> 查 询</button>
			<button type="reset" class="button button-info"><i class="fa fa-undo icolor"></i> 重置</button>
		</form>
	</div>	
		
	<table id="grid-table"></table>
	<div id="grid-pager"></div>

</body>
<script type="text/javascript">
	var root = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/notice/js/noticeList.js"></script>	
</html>