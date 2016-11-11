<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>

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
<style type="text/css">
	form label[class="span2"]{
		width:110px;
		margin-left:50px;
	}
</style>
<script type="text/javascript">
var context = "<%=request.getContextPath()%>";
var quesJjState = "${vo.quesJjState }";
//var title = "${title}";
var title = "<%=request.getParameter("title")%>";
$(function(){
	//初始化日期时间控件
	$('.form_datetime').datetimepicker({
		language:  'zh-CN',
		autoclose: true,
		todayBtn: true,
		pickerPosition: "bottom-left",
		todayHighlight: true,
		startDate: "1999-01-01"
	});
	
	//如果是查看就移除保存按钮
	if(title=="detail"){
		$("#saveUpdateBtn").remove();
	}
	})
</script>
</head>
<body>
		<div class="container" style="margin-top: 30px;margin-left: 50px">
			<form role="quesForm" id="quesForm" >
			<table >
				<tr>
					<td><label for="quesId" class="span2">质量问题编号：</label></td>
					<td><input id="quesId" name="quesId" class="input-xlarge"  type="text" value="${vo.quesId }"  readonly="readonly" ></td>
				
				<input id="influence" name="influence"  class="input-xlarge" type="hidden" readonly="readonly" value ="${vo.influence }">
				<input id="aboutSys" name="aboutSys" class="input-xlarge"  type="hidden" readonly="readonly" value = "${vo.aboutSys }">
				
					<td ><label for="quesDesc" class="span2" >质量问题描述：</label></td>
					<td><textarea id="quesDesc" name="quesDesc" class="input-xlarge" readonly="readonly" >${vo.quesDesc }</textarea></td>
				</tr>
				<input id="aboutDept" name="aboutDept" class="input-xlarge"  type="hidden" readonly="readonly" value = "${vo.aboutDept }">
				<tr>
					<td><label for="quesDate" class="span2">问题提出日期：</label></td>
					<td><input id="quesDate" name="quesDate" class="input-xlarge"  type="text" value="${vo.quesDate }" readonly="readonly" ></td>
				
					<td><label for="quesPerson" class="span2">问题提出人：</label></td>
					<td><input id="quesPerson" name="quesPerson" class="input-xlarge"  type="text" value="${vo.quesPerson }" readonly="readonly"  ></td>
				<</tr>
				<input id="quesFbState" name="quesFbState" class="input-xlarge"  type="hidden" readonly="readonly" value = "${vo.quesFbState }">
				<input id="quesJjState" name="quesJjState" class="input-xlarge"  type="hidden" readonly="readonly" value = "${vo.quesJjState }">
				<tr>
					<td><label for="quesSolution" class="span2">问题解决方案：</label></td>
					<td><textarea id="quesSolution" class="input-xlarge" name="quesSolution" readonly="readonly" >${vo.quesSolution }</textarea></td>
				
					<td><label for="quesJjResult" class="span2">问题解决结果描述：</label></td>
					<td><textarea id="quesJjResult" class="input-xlarge" name="quesJjResult" readonly="readonly" >${vo.quesJjResult }</textarea></td>
				</tr>
			
				<tr>
					<td><label for="quesType" class="span2">问题分类：</label></td>
					<td><input id="quesType" class="input-xlarge" name="quesType" type="text" value="${vo.quesType }" readonly="readonly"  ></td>
				
					<td><label for="quesJjDate" class="span2">问题解决日期<font color="red">*</font>： </label></td>
					<td><div class="input-append date form_datetime input-xlarge" 
					  				data-date-format="yyyy-mm-dd"
					  				data-min-view="2"
					  				data-max-view="2">
				          	<input id="quesJjDate" name="quesJjDate" size="16" type="text" value="${vo.quesJjDate }"  readonly="readonly" placeholder="请选择日期..." >
							<span class="add-on"><i class="icon-th"></i></span>
					</div></td>
				</tr>
				</table>
			</form>
		</div>
		<div class="form-actions" style="margin-top: 30px;">
			<button onclick="save();" id="saveUpdateBtn"  ><i class="fa fa-save icolor"></i> 保存</button>
			<button onclick="closWindow();" id="cancelBtn" ><i class="fa fa-undo icolor"></i> 取 消</button>
		</div>
		           
</body>

<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/deal/js/dealListDetail.js"></script>

</html>