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
</script>
</head>
<body>
	<DIV class="ui-layout-west">
		<div id="menuTree" class="ztree"></div>
	</DIV>
	<div id="ui-layout-center" class="ui-layout-center" style="overflow: hidden;border: 0px;">
		<div id="managerContent1">
			<%-- <iframe id="innerHtml" frameborder='0' height='95%' scrolling='yes' width='100%' src='<%=request.getContextPath()%>/page/dqs/welcome.jsp'></iframe> --%>
			<div class="alert">
				<form name="searchForm" class="form-inline">
					<div class="control-group controls-row">
					<label for="btnText" class="span2">按钮名称：</label> 
					<input type="text" class="span2" id="searchBtnText" placeholder="请输入按钮名称...">
						<div class="span3">
							<button type="button" class="button button-info ipt_button"
								onclick="doSearch();">
								<i class="fa fa-search icolor"></i> 查询
							</button>
						</div>
					</div>
				</form>
			</div>
			<table id="grid-table"></table>
			<div id="grid-pager"></div>
		</div>
		<!-- 	<div id="managerContent2" >welcome.</div> -->
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			myLayout = $('body').layout({
				west__minSize:210,	
				west__resizable:true
			});
		});

		var classId = "";
		var now_nodeId = "";
		var now_levId = "";
		var now_sysId = "";
	</script>
</body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/page/sm/btn/js/btnList.js"></script>
</html>