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
	var taskInstId='<%=request.getParameter("taskInstId")%>';
	var actTaskInstId ='<%=request.getParameter("actTaskInstId")%>';
	var flag ='<%=request.getParameter("flag")%>';
</script>
<style type="text/css">
	body {
	 	min-height: 350px;
	 	height:100%;
	    padding-top: 5px;
	    background-color: #F5F5F5;
	}
	.button_div{
		text-align: center;
	}
</style>
</head>
<body>
	 <div class="layout_div">
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
	</div>
	<div class="button_div">
		<button type="button" class="btn btn-primary seachbtn" onclick="delegatUser();" >指派</button>
	</div> 
</body>
<script type="text/javascript"src="<%=request.getContextPath()%>/page/activiti/process/task/delegateUser.js"></script>
</html>