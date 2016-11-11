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
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>
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
<script type="text/javascript">
var context = "<%=request.getContextPath()%>";
var chartPath="<%=request.getContextPath()%>/public/echarts";
var myChart="";
var option="";
$(function(){
    //初始化日期时间控件
    $('.form_datetime').datetimepicker({
//     	format: 'yyyymm', 
		startView: 3, 
        language:  'zh-CN',
        autoclose: true,
//         todayBtn: true,
        pickerPosition: "bottom-left",
//         todayHighlight: true,
//         startDate: "1999-01"
    });
})
</script>
</head>
<body>

        <div class="alert">
            <div id="managerContent1">
	            <form name="searchForm" class="form-inline icolor">
		            <div class="control-group controls-row">
		            	<label class="span2">起始年月:</label>
		            	<div class="input-append date form_datetime span4" 
		                                    data-date-format="yyyy-mm"
		                                    data-min-view="3"
		                                    data-max-view="3">
		                 	<input id="startDate" name="startDate" size="16" type="text" readonly/>
		                    <span class="add-on"><i class="icon-th"></i></span>
		               	</div>
		               	<label class="span2">结束年月:</label>
		               	<div class="input-append date form_datetime span4" 
		                                    data-date-format="yyyy-mm"
		                                    data-min-view="3"
		                                    data-max-view="3">
		                 	<input id="endDate" name="endDate" size="16" type="text" readonly/>
		                    <span class="add-on"><i class="icon-th"></i></span>
		                </div>
		            </div>
		            <div class="control-group controls-row">
		            	<label class="span2">问题类别</label>
		            	<select class="span3"  id="dateFlag" name="dateFlag" >
		                            	<option value="">请选择</option>
		                            	<option value="1" >按提出问题月份查询</option>
		                            	<option value="0" >按解决问题月份查询</option>
		                </select>
		            </div>
		            <div class="control-group controls-row" align="center">
		            	<button type="button" class="button button-info" onclick="doSearch()" ><i class="fa fa-search icolor"></i> 查询</button>
		                <button type="reset" class="button button-info" ><i class="fa fa-undo icolor"></i> 重置</button>
		            </div>
	            </form>
            </div>
        </div>  
        <div id="main" style="height:350px;"></div>
</body>
<script src="<%=request.getContextPath()%>/public/echarts/echarts.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/quesmgr/map/js/quesMap.js"></script>
</html>