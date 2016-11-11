<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/jquery-validation/css/screen.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/json2/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.all-3.5.min.js"></script>

<style type="text/css">
.tb_center{

}
.tb_center tr td{
padding-left:5px;
}
.block_span{
dispaly:inline-block;
width:50%;
text-align: center;
}
.edit_tb td{border:solid #add9c0; border-width:0px 1px 1px 0px;}
.edit_tb th{border:solid #add9c0; border-width:0px 1px 1px 0px;font-size:13px;background-color:#D7D7D7;}
.edit_tb{border:solid #add9c0; border-width:1px 0px 0px 1px;}



.form-actions {
    padding: 10px 10px 0px;
    margin-top: 20px;
   /*  margin-bottom: 20px; */
    background-color: #f5f5f5;
    border-top: 1px solid #e5e5e5;
}
</style>
</head>
<body>
        <form id="add-definition-form" action="<%=request.getContextPath()%>/activiti/processModel.do?method=addProcessModel" method="post" class="form-horizontal">
        	<input type="hidden" id="version">
        	<div style='margin:auto;width:95%'>
	          	<table class="tb_center" width="90%" align='center' height="100%" border="0" cellspacing="0" cellpadding="0">
	          		<tr>
	          			<td align='right'><strong>部署模型</strong></td>
	          			<td align='left'>
	          				<select id="developedModel" style='margin-bottom:4px;'>
	          					<option value="">请选择...</option>
	          				</select>
	          			</td>
	          			<td align='right'><strong>流程定义Id</strong></td>
	          			<td align='left'><input type="text" id="definitionId" style='margin-bottom:4px;' disabled='disabled'></td>
	       			</tr>
	          		
	          		<tr>
	          			<td align='right'><strong>部署Id</strong></td><td align='left'><input type="text" id="deploymentId" style='margin-bottom:4px;' disabled='disabled'></td>
	          			<td align='right'><strong>流程名称</strong></td><td align='left'><input type="text" id="definitionName" style='margin-bottom:4px;' disabled='disabled'></td>
	          		</tr>
	          		
	          		<tr>
	          			<td align='right'><strong>定义Key</strong></td><td align='left'><input type="text" id="definitionKey" disabled='disabled'></td>
	          			<td align='right'><strong>流程配置名称</strong></td><td align='left'><input type="text" id="procName"></td>
	          		</tr>
	          	</table>
          	
          	
          	
	          	<table class="edit_tb" style="margin-top:20px;boder:1px solid" width="100%" height="100%"  cellspacing="0" cellpadding="0">
	          		<th algin='center' width='30%'>步骤名称</th>
	          		<th algin='center' width='20%'>竞签/会签</th>
	<!--           		<th algin='center' width='16%'>类型</th> -->
						<th algin='center' width='16%'>用户角色</th> 
	          		<!--           		<th algin='center' width='35%'>用户</th>-->
<!-- 	          		<th algin='center' width='15%'>操作</th> -->
	          	
	          	
	          		<tr align='center'>
	          			<td>
	          				<select id="taskNodes" style="width:80%;">
	          					
	          				</select>
	          			</td>
	          			<td>
	          				<select id="operType" style="width:80%;">
	          					<option value="0">竞签</option>
	          					<option value="1">会签</option>
	          				</select>
	          			</td>
	          			<td>
	          				 <!-- <label for="user"></label>
	  						 <input type="text" id="userName" style='margin-bottom: 4px;'>
	  						 <input type="hidden" id="userId"> -->
	  						 <label for="userRole"></label>
	  						 <input type="text" id="userRoleName" style='margin-bottom: 4px;'>
	  						 <input type="hidden" id="userRoleId">
	          			</td>
<!-- 	          			<td> -->
<!-- 	          			<button class='btn btn-success btn-small' id="addProcessConfig" type='button' ><i class='icon-plus icolor'></i> 添加</button> -->
<!-- 	          			</td> -->
	       			</tr>
	          	</table>
	          	
	          	<table class="edit_tb" style="margin-top:20px;overflow-y: scroll" width="100%" height="100%" border="1" cellspacing="0" cellpadding="0">
		          	<thead>
		          		<th algin='center' width='30%'>步骤名称</th>
		          		<th algin='center' width='20%'>竞签/会签</th>
	<!-- 	          		<th algin='center' width='16%'>类型</th> -->
	<!-- 	          		<th algin='center' width='16%'>用户角色</th> -->
		          		<th algin='center' width='35%'>用户角色</th>
		          		<th algin='center' width='15%'>操作</th>
		          	</thead>
	          	
	          		<tbody id="procConfigTbody">
		          		
	       			</tbody>
	          	</table>
          	</div>
        </form>
        
        <div>
    	<div style="height: 50px"></div>
	      <div class="form-actions" align="center">
	        <button id="saveModelButton" class="button button-info ipt_button"><i class="fa fa-save icolor"></i> 确 定</button>
	        <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
	      </div>
    	</div>
    	
	   	<div id="ztree_01" style="display:none;min-height:500px;">
	   	<div>
	 		<ul id="userRoleTree" class="ztree"></ul>
	 	</div>
	 	<div class="form-actions" align="center" style="width:90%">
	        <button id="saveSelect" onclick="addUserRole();" class="button button-info ipt_button"><i class="fa fa-save icolor"></i> 确 定</button>
	        <button id="cancelBtn" class="button button-info ipt_button" onclick="closChileWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
	     </div>
	 		
	 	</div>
</body>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var procId = '<%=request.getParameter("procId")%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/activiti/process/definition/addDefinition.js"></script>   
</html>