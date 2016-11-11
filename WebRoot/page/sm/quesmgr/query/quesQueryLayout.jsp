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

<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";

</script>

</head>
<body>
 <div class="ui-layout-center" >
 	<ul class="breadcrumb">
  		<li class="active">查询/过滤</li>
	</ul>
		<div class="alert alert-info" >
		    <form class="form-inline" > 
		    		<div align="center">               
			    		<lable>质量问题编号:</lable>
			    		<input type="text" id="quesId" class="span4" placeholder="请输入质量问题编号...">
			    		<lable>问题发布状态:</lable>
			    		<select id="quesFbState"  name="quesFbState" class="span4">
			    					<option value="">请选择</option>
			    					<option value="已提交">已提交</option>
			    					<option value="待审核">待审核</option>
			    					<option value="审核未通过">审核未通过</option>
			    					<option value="已发布">已发布</option>
					    </select>
					  </div>
					  <div align="center" style="margin-top: 15px">  
			    		<lable>解决方案状态:</lable>
			    		<select id="quesSolutionState"  name="quesSolutionState" class="span4">
			    					<option value="">请选择</option>
			    					<option value="已提交">已提交</option>
			    					<option value="待审核">待审核</option>
			    					<option value="审核未通过">审核未通过</option>
			    					<option value="已发布">已发布</option>
					    </select>
					   <lable>问题解决状态:</lable>
					    <select id="quesJjState"  name="quesJjState" class="span4">
			    					<option value="">请选择</option>
			    					<option value="未解决">未解决</option>
			    					<option value="解决方案待审核">解决方案待审核</option>
			    					<option value="解决中">解决中</option>
			    					<option value="解决方案未通过">解决方案未通过</option>
			    					<option value="已解决待审核">已解决待审核</option>
			    					<option value="已解决">已解决</option>
			    					<option value="已解决审核未通过">已解决审核未通过</option>
			    					<option value="无效">无效</option>
					    </select>
			    </div>
			    	<div class="control-group controls-row" align="center" style="margin-top: 15px">
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
	
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/query/js/quesQueryLayout.js"></script>
</body>
</html>