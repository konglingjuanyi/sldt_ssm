<%@page import="java.util.UUID"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>检核指标管理</title>
<%@ include file="/page/sm/commonLinkScript.jsp"%>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	var classId = "";
	var now_nodeId = "";
	var now_levId = "";
	var now_sysId = "";
</script>
</head>
<body>
	<DIV class="ui-layout-west">
		<div id="orgTree" class="ztree"></div>
	</DIV>
	<div id="ui-layout-center" class="ui-layout-center" style="overflow: hidden;border: 0px;">
		<div id="managerContent1">
			<div class="alert alert-info">
				<form name="searchForm" class="form-inline">
					<div class="control-group controls-row">
						<label class="span1">用户编号：</label> 
						<input class="span2" type="text" id="searchUserId" placeholder="请输入用户编号..."> 
						<label class="span1">姓名：</label> 
						<input class="span2" type="text" id="searchUserNm" placeholder="请输入姓名...">
						<button type="button" class="button button-info ipt_button" onclick="doSearch();">
							<i class="fa fa-search icolor"></i> 查询
						</button>
					</div>
				</form>
			</div>
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
		</div>
	</div>
</body>
<script type="text/javascript"src="<%=request.getContextPath()%>/page/sm/user/js/user.js"></script>
</html>