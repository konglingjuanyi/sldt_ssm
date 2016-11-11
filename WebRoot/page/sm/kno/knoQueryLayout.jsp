<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

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
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";

</script>

</head>
<body>
	<div class="ui-layout-center">
		<div class="alert">
		<div id="managerContent1">
	    	<form name="searchForm" class="form-inline icolor">
		    	<div class="control-group controls-row">
		    		<label class="span2">内容标题：</label>
		    		<input class="span4" type="text" id="kncntTitle" placeholder="请输入质量问题编号...">
		    		<label class="span2">知识库分类：</label>
		    		<select class="span4" id="kncatId"  name="kncatId">
		    			<option value="">请选择</option>
				    </select>
		    	</div>
		    	<div class="control-group controls-row">
		    		<label class="span2">关键字：</label>
		    		<input class="span4" type="text" id="kncntKey" placeholder="请输入关键字...">
		    		<label class="span2">全字段查询：</label>
		    		<input class="span4" type="text" id="allField" >
		    	</div>
		    	<div class="control-group controls-row" align="center">
		    		<button type="button" class="button button-info" onclick="doSearch()" ><i class="fa fa-search icolor"></i> 查询</button>
					<button type="reset" class="button button-info"><i class="fa fa-undo icolor"></i> 重置</button>
		    	</div>
			</form>
		</div>	
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
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/kno/js/knoQueryLayout.js"></script>
</body>
</html>