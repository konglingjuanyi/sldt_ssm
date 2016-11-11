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
		margin-left:30px;
	}
</style>
<script type="text/javascript">
var context = "<%=request.getContextPath()%>";
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
</script>
</head>
<body>
	<div class="container" style="margin-top: 30px;margin-left: 80px">
		<form role="form" id="knoCatsForm" >
		<table>
		<tr>
			<td><label class="span2">质量问题编号：</label></td>
			<td><input id="quesId" class="span3"  readonly="readonly" type="text" value="${vo.quesId }" >
			</td>
			<td><label class="span2">涉及系统：</label></td>
			<td><input id="aboutSys" class="span3"  readonly="readonly" type="text" value="${vo.aboutSys }" >
			</td>
		</tr>
		<tr>
			<td><label class="span2">影响范围：</label></td>
			<td><textarea class="span3" disabled="disabled" id="influence" >${vo.influence }</textarea>
			</td>
			<td><label class="span2">质量问题描述：</label></td>
			<td><textarea class="span3" disabled="disabled" id="quesDesc" >${vo.quesDesc }</textarea>
			</td>
			
		</tr>
		<tr>
			<td><label class="span2">涉及部门：</label></td>
			<td><input id="aboutDept" class="span3"  readonly="readonly" type="text" value="${vo.aboutDept }" ></td>
			<td><label class="span2">问题提出人：</label></td>
			<td>	<input id="quesPerson" class="span3" readonly="readonly"  type="text" value="${vo.quesPerson }" >
			</td>
		</tr>
		<tr>
			<td><label class="span2">问题提出日期：</label></td>
			<td><input id="quesDate" class="span3"  readonly="readonly" type="text" value="${vo.quesDate }" >
			</td>
			<td><label class="span2">问题发布状态：</label>
			<td><input id="quesFbState" class="span3" readonly="readonly"  type="text" value="${vo.quesFbState }" >
			</td>
		</tr>
		<tr>
			<td><label class="span2">问题解决状态：</label></td>
			<td><input id="quesJjState" class="span3" readonly="readonly"  type="text" value="${vo.quesJjState }" >
			</td>
			<td><label class="span2">解决方案状态：</label></td>
			<td><input id="quesSolutionState" class="span3" readonly="readonly"  type="text" value="${vo.quesSolutionState }" >
			</td>
		</tr>
		<tr>
			<td><label class="span2">问题解决方案：</label></td>
			<td><textarea id="quesSolution" disabled="disabled" class="span3">${vo.quesSolution }</textarea>
			</td>
			<td><label class="span2">问题解决结果描述：</label></td>
			<td><textarea class="span3"  disabled="disabled" id="quesJjResult" >${vo.quesJjResult }</textarea>
			</td>
		</tr>
		<tr>
			<td><label class="span2">问题分类：</label></td>
			<td><input id="quesType" class="span3" readonly="readonly"  type="text" value="${vo.quesType }" ></td>				
			<td><label class="span2">问题解决日期：</label></td>
			<td><input id="quesJjDate" class="span3" readonly="readonly" type="text" value="${vo.quesJjDate }" >
			</td>
		</tr>
		</table>
		</form>
	</div>   
	<div class="form-actions" style="margin-top: 18px;">
			<button onclick="closWindow();" id="cancelBtn" ><i class="fa fa-close icolor"></i>关闭</button>
	</div>
</body>
<script type="text/javascript">
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}
</script>
</html>