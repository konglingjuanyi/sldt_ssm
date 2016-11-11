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
		
 <div class="ui-layout-center">
 	<ul class="breadcrumb">
  		<li class="active">查询/过滤</li>
	</ul>
		<div class="alert alert-info">		
         <form class="form-inline">
               <div  class="span10">
                       <label>质量问题编号:</label>
                        <input type="text" id="quesId"  name="quesId" placeholder="请输入质量问题编号...">
                        <label>问题发布状态:</label>                     
                        	<select id="quesFbState" name="quesFbState">
                        		<option value="">请选择</option>
                        	<!-- 	<option value="已提交">已提交
                        		<option value="待审核">待审核
                        		<option value="审核未通过">审核未通过 -->
                        		<option value="已发布">已发布                       		
                        	</select>                  
                </div>
                 <div class="control-group" align="center" >
                <button type="button" class="button button-info" style="margin-top: 20px" onclick="doSearch()" ><i class="fa fa-search icolor"></i> 查询</button>
                <button type="reset" class="button button-info"  style="margin-top: 20px" onclick="clearNowValue()"><i class="fa fa-undo icolor"></i> 重置</button>
                </div>
                
              </form>
           </div>   
               
          
   		
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
	</div>
	
 


<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/announce/js/announceNew.js"></script>
</body>
</html>