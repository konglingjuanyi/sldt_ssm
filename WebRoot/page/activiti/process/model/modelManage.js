$(document).ready(function(){
	var grid_selector = "#grid-table-model";
	var pager_selector = "#grid-pager-model";
	
	/* toolbox*/
	var tool = [{ text: ' 添加 ' , iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
        openAddWin('addModel',null);
	}
	}, '-', 
	{
		text: ' 修改 ' , iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids=$(grid_selector).jqGrid('getGridParam','selarrrow');
			if(ids.length==1){
				modifyModel(ids[0]);
			}else{
				layer.msg("请选择一条记录！",{icon:2});
			}
		}
	}, '-',
	{ text: ' 批量删除 ' , iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
		deleteModel();
		}
	},'-',
	{ text: ' 部署' , iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
		var ids=$(grid_selector).jqGrid('getGridParam','selarrrow');
		if(ids.length==1){
			deployModel(ids[0]);
		}else{
			layer.msg("请选择一条记录！",{icon:2});
		}
	}
	}, '-', 
	{ text: ' 导出' , iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
		var ids=$(grid_selector).jqGrid('getGridParam','selarrrow');
		if(ids.length==1){
			exportXmlFile(ids[0]);
		}else{
			layer.msg("请选择一条记录！",{icon:2});
		}
	}
	} 
	];
	var girdHeigth = getGridHeight("grid-table-model");
	jQuery(grid_selector).jqGrid({
		url: context+'/activiti/processModel.do?method=getProcessModelByPage',
		datatype: "json",
		height: girdHeigth,
		regional: 'cn', 
		rownumbers:false,
		colNames:[ '模型ID','模型名称','模型的key值','版本', '创建时间', '最后更新时间','元数据'],//, '操作'
		colModel:[
			{name:'id',index:'id', width:35, editable: true,align : 'center'},
			{name:'name',index:'name', width:60, editable: true,align : 'center'},
			{name:'key',index:'key',width:40, editable:true,align : 'center'},
			{name:'version',index:'version', width:20,editable: true,align : 'center'},
			{name:'createTime',index:'createTime',formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d H:i:s'}, width:60, editable: true,align : 'center'},
			{name:'lastUpdateTime',index:'lastUpdateTime',formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d H:i:s'}, width:60, editable: true,align : 'center'},
			{name:'metaInfo',index:'metaInfo', width:160, sortable:false,align : 'center',editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"}},
			//{name:'action',index:'action', width:90, editable: true,align : 'center'}
		], 
		gridComplete : function() {
			var ids = jQuery(grid_selector).jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[i]);
			}
		},
		viewrecords : true,
		rowNum:15,
		rowList:[15,30,45,60,75,90,105],
		pager : pager_selector,
		altRows: true,
		toolbar: [ true, "top" ,tool],
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
	$('#seachModelButton').click(function() {
		reloadGridData();
    });
	
	/**
	 * 添加
	 */
	$("#createModelButton").click(function(){
		//winwow.open(context+"/activiti/processModel.do?method=toAdd");
		$("#addModal").modal("show");
	});
	
});

function deleteModel(){
	
	layer.confirm('确定删除？', {
		btn: ['是','否'], //按钮
		shade: false //不显示遮罩
		}, function(){
			var idsArr = $("#grid-table-model").jqGrid("getGridParam","selarrrow");
			var ids = arrToStr(idsArr);
			if(isEmpty(ids)){
				layer.msg("没有选择要删除的模型！",{icon:2})
				return false;
			}
			$.ajax({
				url: context+'/activiti/processModel.do',
				type: 'post',
				data: {ids:ids,method:'deleteProcessModel'},
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
		}, function(index){
			layer.close(index);
		});
}

function openAddWin(type){
//    parent.openWin(url,"addModel","550px","450px");	
    top.layer.open({
		type:2,
		title:'新增流程模型',
		scrollbar: false,
		shadeClose: false,
		//closeBtn:false,
	    shade: 0.1,
	    //offset:["50px%,'25%'],
	    area: ['90%', '80%'],
	    content:context+"/page/activiti/process/model/addModel.jsp?type="+type
	});
}


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
	var name = $("#name").val();
	$("#grid-table-model").jqGrid('setGridParam', {
	    page: 1,
	    url: context+'/activiti/processModel.do?method=getProcessModelByPage&name='+name,
	    datatype: "json"
	}).trigger("reloadGrid");
}

function doSearch(){
	reloadGridData();
}

/**
 * 修改
 * @param modelId
 */
function modifyModel(modelId){
	//step 1 : 是否已经部署且有流程存在
	$.ajax({ 
		type: 'post', 
		url:context+"/activiti/processModel.do?method=isApproving&modelId="+modelId,
        success: function(data){
        	var count = data.count;
        	if(parseInt(count)>0){
        		top.layer.alert("<span style='color:red;text-align:center;'><strong>警告：</strong>该模型已经部署并存在进行中的审批流程，请在所有审批流程结束后进行更改！</span>");
        		return false;
        	}else{
//        		top.layer.alert("<span style='color:red;text-align:center;'><strong>温馨提示：该模型修改后请重新部署，并在流程定义页面配置节点审批信息。</strong></span>");
    		top.layer.confirm("<span style='color:red;text-align:center;'><strong>温馨提示：该模型修改后请重新部署，并在流程定义页面配置节点审批信息。是否继续？</strong></span>", {
        			btn: ['是','否'], //按钮
        			shade: false //不显示遮罩
        			}, function(index){
        				top.layer.close(index);
        				//删除之前的节点配置信息。
                		$.ajax({
            				url: context+'/activiti/processModel.do',
            				type: 'post',
            				data: {modelId:modelId,method:'deleteWfProcessCfg'},
            				dataType: 'json',
            				success: function(data){
            					top.layer.open({
            						type:2,
            						title:'修改流程模型',
            						scrollbar: false,
            						shadeClose: false,
            						//closeBtn:false,
            					    shade: 0.1,
            					    //offset:["50px%,'25%'],
            					    area: ['90%', '80%'],
            					    content:context+"/service/editor?id="+modelId
            					});
            				},
            				error: function(){
            					layer.msg("服务器出现出错！",{icon:2});
            				}
            			});
        			}, function(index){
        				layer.close(index);
        			});
        	}
        	
        },
        error: function(msg){
        	top.layer.msg('访问服务器出错', {shift: 4});
        }
   });	
}


function exportXmlFile(modelId){
	window.location.href = context+"/activiti/processModel.do?method=exportBpmnFile&modelId="+modelId;
}


/**
 * 部署
 * @param modelId
 */
function deployModel(modelId){
	/*首先校验模型是否已经部署了*/
	$.ajax({
		url: context+'/activiti/processModel.do',
		type: 'post',
		data: {modelId:modelId,method:'isDeployed'},
		dataType: 'json',
		async :false,
		success: function(data){
			if(data.success == '1'){
				parent.layer.confirm('该模型已部署，是否继续！',{
			   	 	btn: ['确认','取消'] //按钮
				}, function(index){
					parent.layer.close(index);
					excuteDeploy(modelId,'true');
				});
			}else if(data.success == '0'){
				layer.msg("查询失败！",{icon:2});
				return false;
			}else{//没有部署
				excuteDeploy(modelId,'false');
			}
		},
		error: function(){
			layer.msg("部署失败！",{icon:2});
		}
	});
}

function excuteDeploy(modelId,isDeployed){
	$.ajax({
		url: context+'/activiti/processModel.do',
		type: 'post',
		data: {modelId:modelId,isDeployed:isDeployed,method:'deploy'},
		dataType: 'json',
		async:false,
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


