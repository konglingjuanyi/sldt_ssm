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
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>

<style type="text/css">
	form {
 	 margin: 0 0 0px;
	}
	.form-horizontal .control-group {
	  margin-bottom: 6px;
	  vertical-align: middle;
	  font-size: 12px;
	}
</style>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	//禁用表单
	$(function(){
		$("select").attr("disabled","disabled");
		$("input").attr("readonly","readonly");
		 $("textarea").attr("readonly","readonly");
})
</script>

</head>
<body>
	<div class="baseInfo">
		<div class="basetitle">
   		 	<div class="bftitle">基本信息</div>
   		 </div>
   		 <div class="infoContent">	
   			<form role="form" id="knoCatsForm" class="form-horizontal">
   				 <div class="control-group" >
   				 	<label for="kncntId" class="control-label">
				 		内容编号：
				 	</label>
				 	<input id="kncntId" class="input-xlarge" name="kncntId" type="text" value="${vo.kncntId }" >
   				 </div>
   				 <div class="control-group" >
   				 	<label for="kncntId" class="control-label">
				 		内容标题：
				 	</label>
				 	<input id="kncntTitle" class="input-xlarge" name="kncntTitle" type="text" value="${vo.kncntTitle }" >
   				</div>
   				<div class="control-group" >
   				 	<label for="kncntId" class="control-label">
				 		问题描述：
				 	</label>
				 	<textarea class="input-xlarge" id="kncntDesc" name="kncntDesc">${vo.kncntDesc }</textarea>
   				 </div>
   			</form>
   		</div>
	</div>
	<div class="baseInfo">
		<div class="basetitle">
   		 	<div class="bftitle">解决方案</div>
   		 </div>
   		  <div class="infoContent">
   		  	<form role="form" id="knoCatsForm" class="form-horizontal">
   		  		 <div class="control-group" >
				 	<label for="planTitle" class="control-label">
				 		方案名称：
				 	</label>
				 	<input id="planTitle" class="input-xlarge" name="planTitle" type="text" value="${vo.planTitle }" >
				 </div>
				 <div class="control-group" >
				 	<label for="planProc" class="control-label">
				 		处理办法：
				 	</label>
				 	<textarea class="input-xlarge" id="planProc" name="planProc">${vo.planProc }</textarea>
				 </div>
				 <div class="control-group" >
				 	<label for="quesProc" class="control-label">
				 		问题处理：
				 	</label>
				 	<textarea class="input-xlarge" id="quesProc" name="quesProc">${vo.quesProc }</textarea>
				 </div>
				 <div class="control-group" >
				 	<label for="quesProc" class="control-label">
				 		知识库分类：
				 	</label>
				 	<input id="kncatName" class="input-xlarge"  type="text" value="${kncatName }" >
				 </div>
				 <div class="control-group" >
				 	<label for="quesProc" class="control-label">
				 		关键字：
				 	</label>
				 	<input id="kncntKey" class="input-xlarge"  type="text" value="${vo.kncntKey }" >
				 </div>
   		  	</form>
   		  </div>
	</div>
	<div class="form-actions" style="margin-top: 1px;">
		<button onclick="closWindow();" id="cancelBtn" ><i class="fa fa-undo icolor"></i> 取 消</button>
	</div>
<script type="text/javascript">
	
	 // 关闭窗口
	 
	function closWindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.search();
		parent.layer.close(index);
	}
</script>
</body>
</html>