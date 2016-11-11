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
<style type="text/css">
#middleButton button{
 width: 40px;
}
</style>
</head>
<body>
	
	<div class="ui-layout-center" style="float:left; width:700px;" >
		<ul class="breadcrumb">
  		    <li class="active" id="managerContent1-top" > 查询/过滤</li>
	   </ul>
	   <div class="alert alert-info">		
	    	<form name="searchForm" class="form-inline">
	    			<lable>质量问题编号：</lable>
	    			 <input type="text" id="quesId" class="" placeholder="请输入质量问题编号...">
	    			<lable>问题解决状态：</lable>
	    				<select id="quesJjState"  name="quesJjState" class="">
	    					<option value="">所有状态</option>	 
	    						<option value="未解决">未解决  					
                        		<option value="解决中">解决中
                        		<option value="解决方案待审核">解决方案待审核
                        		<option value="解决方案未通过">解决方案未通过
                        		<option value="已解决">已解决  
                        		<option value="已解决待审核">已解决待审核
                        		<option value="已解决审核未通过">已解决审核未通过
                        		<option value="无效">无效	    					
			    		</select>
				<div class="control-group" align="center" style="margin-top: 20px">
				<button type="button" class="button button-info" onclick="doSearch()" ><i class="fa fa-search icolor"></i> 查询</button>
				<button type="reset" class="button button-info"><i class="fa fa-undo icolor"></i> 重置</button>
				</div>
			</form>		
		</div>
		
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
		
	</div>
	
	 <div id="middleButton" style="float:left; width:50px;margin-left: 10px;margin-top: 150px;">
	 	 
		<button type="button" class="button button-info" onclick="sendRight()" ><i class="fa fa-arrow-right icolor"></i></button>
		</br>
		</br>
		<button type="button" class="button button-info" onclick="del()" ><i class="fa fa-arrow-left icolor"></i></button>
		</br>
		</br>
		<button type="button" class="button button-info" onclick="sendAll()" ><i class="fa fa-random icolor"></i></button>
		</br>
		</br>
		<button type="button" class="button button-info" onclick="sendCurPage()" ><i class="fa fa-angle-double-right icolor"></i></button>
		</br>
		</br>		 
		<button type="button" class="button button-info" onclick="delAll()" ><i class="fa fa-angle-double-left icolor"></i></button>
	
	</div>
	
	<div style="float:left; width:280px;">
		<ul class="breadcrumb">
  		    <li class="active" id="managerContent1-top">数据质量重大问题解决流程管理</li>
	   </ul>
	  <!--  <div>
	   		<button type="button" class="button button-info" onclick="addNodeFieldInfo()" ><i class="fa fa-external-link-square icolor"></i> 方案发布</button>
		  	<button type="button" class="button button-info" onclick="changeState()" ><i class="fa fa-exchange icolor"></i> 解决状态变更</button>
	   </div> -->
		<table id="grid2-table"></table>		
	</div> 
	
<!--  <script type="text/javascript">
	$(document).ready(function(){
		$('body').layout({ applyDemoStyles: true });
	});
</script> -->
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/deal/js/dealList.js"></script>
</body>
</html>