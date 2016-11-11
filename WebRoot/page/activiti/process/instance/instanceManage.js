$(document).ready(function(){
	
//	initDefinitionKey(initSelect);
	
	var grid_selector = "#grid-table-instance";
	var pager_selector = "#grid-pager-instance";
	
	
	var girdHeigth = getGridHeight("grid-table-instance")+51;
	jQuery(grid_selector).jqGrid({
		url: context+'/activiti/processInstance.do?method=getProcessInstanceByPage',
		datatype: "json",
		height: girdHeigth,
		regional: 'cn', 
		rownumbers:false,
		colNames:[ '流程实例ID','业务号','业务类型','开始时间','当前节点' ,'结束时间','actProcInstId','操作'],
		colModel:[
			{name:'procInstId',index:'procInstId', width:60, editable: true,align : 'center'},
			{name:'businessId',index:'businessId', width:60, editable: true,align : 'center'},
			{name:'businessType',index:'businessType',width:80, editable:true,align : 'center'},
			{name:'startTime',index:'startTime', width:80,editable: true,align : 'center'},
			{name:'curActName',index:'curActName', width:80,editable: true,align : 'center'},
			{name:'endTime',index:'endTime', width:80,editable: true,align : 'center'},
			{name:'actProcInstId',index:'actProcInstId', width:0, editable: true,hidden:true,align : 'center'},
			{name:'action',index:'action', width:50, editable: true,align : 'center'}
			
		], 
		gridComplete : function() {
			var ids = jQuery(grid_selector).jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[i]);
				var view = '<input type="button" class="btn btn-success btn-small instanceTraceButton" instId="'+rowData.actProcInstId+'" value="流程跟踪" data-toggle="modal" data-target="instanceModal">';
				jQuery(grid_selector).jqGrid('setRowData', ids[i], {action : view});
			}
		},
		viewrecords : true,
		rowNum:15,
		rowList:[15,30,45,60,75,90,105,120],
		pager : pager_selector,
		altRows: true,
		multiselect: true,
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},
		autowidth: true

	});
	
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,{edit:false,add:false,del:false,refresh:false,search:false});
	
	//绑定点击事件
	$('#seachInstanceButton').click(function() {
		reloadGridData();
    });
	
	/**
	 * 删除
	 */

	$("#deleteInstanceButton").bind("click",function(){
		var idsArr = $("#grid-table-instance").jqGrid("getGridParam","selarrrow");
		var ids = arrToStr(idsArr);
		if(isEmpty(ids)){
			layer.msg("没有选择要删除的流程实例！",{icon:2})
			return false;
		}
		
		$.ajax({
			url: context+'/activiti/processInstance.do',
			type: 'post',
			data: {ids:ids,method:'deleteProcessInstance'},
			dataType: 'json',
			success: function(data){
				if(data.success == '1'){
					layer.msg("删除成功！",{icon:1});
					reloadGridData();
				}else{
					layer.msg("删除失败！",{icon:2});
				}
			},
			error: function(){
				layer.msg("删除失败！",{icon:2});
			}
		});
	});
	/*流程跟踪*/
	traceInstance();
	
	
});

/**
 * 展示流程实例图
 * @param processInstanceId
 * @param businessKey
 * @param definitionKey
 */
function traceInstance(processInstanceId,businessKey,definitionKey){
	$("#grid-table-instance").on("click",".instanceTraceButton",function(){
		var processInstanceId = $(this).attr("instId");
		//var businessKey = $(this).attr("busKey");
		//var definitionKey = $("#definitionKey-instance").val();
		var src = context+'/activiti/processInstance.do?method=tracePicture&processInstanceId='+processInstanceId
		+'&businessKey='
		+'&definitionKey=';
		var img = '<img alt="..." src="'+src+'">';
		$("#editDiv-instance").html(img);
		$("#instanceModal").modal("show");
	});
}

/**
 * 初始化下拉框
 * @param callBack(回调函数)
 */
function initDefinitionKey(callBack){
	$.ajax({
		url: context+'/activiti/processDefinition.do',
		type: 'post',
		data: {method:'processEnum'},
		dataType: 'json',
		async : false,
		success: function(data){
			for(var i=0;i<data.length;i++){
				var json = data[i];
				for(var key in json){
					$("#definitionKey-instance").append("<option value='"+key+"'>"+json[key]+"</option>");
				}
			}
			callBack();
		},
		error: function(){
			layer.msg("初始化下拉选项失败！",{icon:2});
		}
	});
}
//function initSelect(){
//	$(".selectpicker").selectpicker({
//		  style: 'btn-info',
//		  size: 4
//	}); 
//}

/**
 * 判断是否为空
 */
function isEmpty(param){
	return param === undefined || param == null || param.length==0;
}

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}

function reloadGridData(){
	var businessId = $("#businessNo").val();
	$("#grid-table-instance").jqGrid('setGridParam', {
	    page : 1,
	    url: context+'/activiti/processInstance.do?method=getProcessInstanceByPage&businessId='+encodeURI(encodeURI(businessId)),
	    datatype : "json"
	}).trigger("reloadGrid");
}

/**
 * 修改
 * @param modelId
 */
function modifyModel(modelId){
	window.location.href = context+"/service/editor?id="+modelId;
}

/**
 * 部署
 * @param modelId
 */
function deployModel(modelId){
	$.ajax({
		url: context+'/activiti/processModel.do',
		type: 'post',
		data: {modelId:modelId,method:'deploy'},
		dataType: 'json',
		success: function(data){
			if(data.success == '1'){
				layer.msg("部署成功！",{icon:1});
				reloadGridData();
			}else{
				layer.msg("部署失败！",{icon:2});
			}
		},
		error: function(){
			layer.msg("部署失败！",{icon:2});
		}
	});
	
}
/**
 * 导出
 * @param modelId
 */
function exportModel(modelId){
	window.location.href = context+"/activiti/processModel.do?method=exportBpmnFile&modelId="+modelId;
}

function arrToStr(arr){
	if(arr == null || arr.length==0) return "";
	var str = "";
	for(var i=0;i<arr.length;i++){
		str += arr[i]+",";
	}
	return str.substring(0,str.length-1);
}

/**
 * 判断是否含有中文
 * @param str
 * @returns {Boolean}
 */
function isChinese(str){
	if((/[\u4e00-\u9fa5]+/).test(str)){
		return true;
	}else{
		return false;
	}
}


