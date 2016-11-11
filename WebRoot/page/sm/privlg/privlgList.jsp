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
		<div id="privlgcatetree" class="ztree"></div>
	</DIV>
	<div id="ui-layout-center" class="ui-layout-center" style="overflow: hidden;border: 0px;">
		<div id="managerContent1">
			<%-- <iframe id="innerHtml" frameborder='0' height='95%' scrolling='yes' width='100%' src='<%=request.getContextPath()%>/page/dqs/welcome.jsp'></iframe> --%>
			<div class="alert">
				<form name="searchForm" class="form-inline">
					<div class="control-group controls-row">
						<label class="span1">权限名称：</label> <input class="span2"
							type="text" id="privname_id" placeholder="请输入权限名称...">
							 <label class="span1">状态：</label>
							<select id="privState_id" class="span2" style="width: 120px;">
							<option value="" selected="1">请选择...</option>
							<option value="0">下架</option>
							<option value="1">上架</option>
							</select>
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
	<!-- 用户权限配置 -->
	<div id="cfgPrivlgModal">
		<div id="userPrivlgModal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>
					<span id="userPrivlgTitle"></span>
				</h3>
			</div>
			<div class="modal-body">
				<iframe id="userPrivlgWin" name="userPrivlgWin"
					src="<%=request.getContextPath()%>/page/sm/user/userPrivlg.jsp"
					width="95%" height="300" frameborder="no" border="0"
					marginwidth="0" marginheight="0" scrolling="yes"
					allowtransparency="yes"> </iframe>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0)" id="userPrivlgBtn" class="btn btn-info"
					onclick="cfgPrivlgModalFn()"><i class="fa fa-save"></i> 确 定</a> <a
					href="javascript:void(0)" class="btn btn-info" data-dismiss="modal"><i
					class="fa fa-close"></i> 取 消</a>
			</div>
		</div>
	</div>
	<!-- 用户角色配置 -->
	<div id="cfgRoleModal">
		<div id="userRoleModal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>
					<span id="userRoleTitle"></span>
				</h3>
			</div>
			<div class="modal-body">
				<iframe id="userRoleWin" name="userRoleWin"
					src="<%=request.getContextPath()%>/page/sm/user/userRole.jsp"
					width="95%" height="300" frameborder="no" border="0"
					marginwidth="0" marginheight="0" scrolling="yes"
					allowtransparency="yes"> </iframe>
			</div>
			<div class="modal-footer">
				<a href="javascript:void(0)" id="userRoleBtn" class="btn btn-info"
					onclick="cfgRoleModalFn()"><i class="fa fa-save"></i> 确 定</a> <a
					href="javascript:void(0)" class="btn btn-info" data-dismiss="modal"><i
					class="fa fa-close"></i> 取 消</a>
			</div>
		</div>
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
	src="<%=request.getContextPath()%>/page/sm/privlg/privlgList.js"></script>
</html>