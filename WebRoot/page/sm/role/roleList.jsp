<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/page/sm/commonLinkScript.jsp"%>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>

</head>
<body   class="row-fluid">
	<div class="alert alert-info">
            <form name="searchForm" class="form-inline">
            <div class="control-group controls-row">
			  <label class="span2" for="roleName">角色名称：</label> 
			  <input type="text" id="searchRoleNm" class="input-small span4" placeholder="请输入角色名称...">
			 <button class="button button-info ipt_button" type="button" onclick="doSearch()"><i class="fa fa-search icolor"></i> 查 询</button>
			</div>
			</form>
	</div>	
		
	<table id="grid-table"></table>
	<div id="grid-pager"></div>
</body>
	<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/role/js/roleList.js"></script>
</html>