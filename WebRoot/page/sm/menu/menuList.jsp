<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/page/sm/commonLinkScript.jsp"%>
</head>
<body>
	<div class="alert alert-info">
    	<form name="searchForm" class="form-inline">
    	   <div class="control-group controls-row">
				<label class="span2 formLabel" for="menuName">菜单名称：</label> 
				<input type="text" id="searchMenuNm" class="input-small span3 formInput" placeholder="请输入菜单名称...">
				
				<label class="span2 formLabel">菜单类型：</label>
				<select id="searchMenuType" class="span3 formInput" style="width: 120px;">
					<option value="" selected>全部</option>
					<option value="01">系统菜单</option>
					<option value="02">功能菜单</option>
					<option value="03">业务菜单</option>
				</select>
			</div>
        	<div class="control-group controls-row">
				<label class="span2 formLabel" for="searchPMenuNm">父级菜单名称：</label> 
				<input type="text" id="searchPMenuNm" class="input-small span3 formInput" placeholder="请输入父级菜单名称...">
				
				<label class="span2 formLabel">归属子系统：</label>
				<select id="appId" class="span3 formInput" style="width: 120px;">
					
				</select>
				<button class="button button-info ipt_button" type="button" onclick="doSearch()"><i class="fa fa-search icolor"></i> 查 询</button>
			</div>
			
		</form>
	</div>	
		
	<table id="grid-table"></table>
	<div id="grid-pager"></div>
</body>
<script type="text/javascript">
	var root = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/menu/js/menuList.js"></script>	
</html>