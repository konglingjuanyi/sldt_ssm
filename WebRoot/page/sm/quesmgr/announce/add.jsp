<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
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
		margin-left:30px;
	}
</style>

<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	var idtype = "<%=request.getParameter("idtype")%>";
	var flag = "<%=request.getParameter("flag")%>";
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
		if(flag=="detail"){
			$("#saveUpdateBtn").remove();
			$("select").attr("readonly","readonly");
			 $("input[type='text']").attr("readonly","readonly");
			    $("textarea").attr("readonly","readonly");
		}
		/* if(idtype=="add"){
			$("#id").attr("display","none");			
		} */
	})
</script>
</head>
<body>
	<div style="margin-top: 30px;margin-left: 20px">
			<form  id="saveFrom" >
			<table>
				<tr>
					<td width="10%">				
					<label class="span2"  for="quesDesc" >质量问题编号<font color="red">*</font>：</label></td>
					<td width="40%"><input class="input-xlarge" type="text" id="quesId"  name="quesId" value="${tq.quesId}" readonly="readonly"/>
					</td>
					<td width="10%">
					<label class="span2" for="aboutSys" >涉及系统<font color="red">*</font>：</label>
					</td><td width="40%"><input class="input-xlarge" type="text" id="aboutSys"  name="aboutSys" value="${tq.aboutSys}"/></td>
									
				</tr>
				<tr><td>
					<label class="span2" for="influence" >影响范围<font color="red">*</font>：</label>
					</td><td><textarea class="input-xlarge" rows="3" cols="" id="influence" name="influence" value="${tq.influence}">${tq.quesDesc}</textarea>
					</td><td>
					<label class="span2" for="quesDesc" >质量问题描述<font color="red">*</font>：</label>
					</td><td><textarea class="input-xlarge" rows="3" cols="" id="quesDesc" name="quesDesc" value="${tq.quesDesc}">${tq.quesDesc}</textarea></td>
				</tr>
				<tr><td>
					<label class="span2" for="aboutDept" >涉及部门<font color="red">*</font>：</label>
					</td><td><input class="input-xlarge" type="text" id="aboutDept"  name="aboutDept" value="${tq.aboutDept}"/>
					</td><td>
					<label class="span2" for="quesPerson" >问题提出人<font color="red">*</font>：</label>
					</td><td><input class="input-xlarge" type="text" id="quesPerson"  name="quesPerson" value="${tq.quesPerson}"/></td>
				</tr>
				<tr><td>
					<label class="span2" for="quesType" >问题分类<font color="red">*</font>：</label>
					</td><td><select class="input-xlarge"  id="quesType" name="quesType">
						<option value="">请选择</option>
						<option value="一般问题" <c:if test="${tq.quesType eq '一般问题' }">selected</c:if> >一般问题
						<option value="重要问题" <c:if test="${tq.quesType eq '重要问题' }">selected </c:if> >重要问题
						<option value="重大问题" <c:if test="${tq.quesType eq '重大问题' }">selected </c:if> >重大问题
					</select></td>
					<td>
					<label class="span2"  for="quesPerson" >问题提出日期<font color="red">*</font>：</label>
					</td><td><div class="input-append date form_datetime input-xlarge" 
			  				data-date-format="yyyy-mm-dd"
			  				data-min-view="2"
			  				data-max-view="2">
						<input id="quesDate" name="quesDate" size="16" type="text" value="${tq.quesDate}" readonly>
						<span class="add-on"><i class="icon-th"></i></span>
					</div></td>
				</tr>
				</table>
			</form>
	</div>
	<div class="form-actions" style="margin-top: 1px;">
			<button onclick="saveQues();" id="saveUpdateBtn"  ><i class="fa fa-save icolor"></i> 保存</button>
			<button onclick="closWindow();" id="cancelBtn" ><i class="fa fa-undo icolor"></i> 退出</button>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/announce/js/add.js"></script>
</body>
</html>