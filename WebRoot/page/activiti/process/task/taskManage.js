var tool = [
		{
			text : '指定任务处理人',
			iconCls : 'fa fa-edit icolor',
			btnCls : 'button-gp button-info',
			handler : function() {
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if (ids.length == 1) {
					var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
			        openAddWin('delegateUser',rowData);
				} else {
					layer.msg("请选择一条记录！",{icon:2});
				}
			}
		},
		'-',
		{
			text : ' 手工执行任务 ',
			iconCls : 'fa fa-trash-o icolor',
			btnCls : 'button-gp button-info',
			handler : function() {
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow'); // --多个选中的id
				if (ids.length == 1) {
					var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
					runTask(rowData.taskInstId);
				}else{
					layer.msg("请选择一条记录！",{icon:2});
					return;
				}
			}
		}];

$(document).ready(function(){
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var girdHeigth = getGridHeight("grid-table");
	jQuery(grid_selector).jqGrid({
		url: context+'/activiti/processTaskInst.do?method=getTaskInstByPage',
		datatype: "json",
		height: girdHeigth,
		regional: 'cn', 
		rownumbers:false,
		colNames:[ '流程定义ActivitiID','流程实例ID','任务名称','任务节点类型','操作类型','被委托人','创建时间','描述','taskInstId','actTaskInstId'],
		colModel:[
			{name:'actId',index:'actId', width:60, editable: true,align : 'center'},
			{name:'procInstId',index:'procInstId', width:120, editable: true,align : 'center'},
			{name:'actName',index:'actName', width:60, editable: true,align : 'center'},
			{name:'actType',index:'actType',width:80, editable:true,align : 'center'},
			{name:'operateType',index:'operateType', width:80,editable: true,align : 'center'},
			{name:'assignee',index:'assignee', width:80, editable: true,align : 'center'},
			{name:'dateCreated',index:'dateCreated', width:100, editable: true,align : 'center'},
			{name:'description',index:'description', width:100, editable: true,align : 'center'},
			{name:'taskInstId',index:'taskInstId', width:0, hidden: true,align : 'center'},
			{name:'actTaskInstId',index:'actTaskInstId', width:0, hidden: true,align : 'center'},
		], 
		viewrecords : true,
		rowNum:15,
		rowList:[60,120,60,60,75,90,105,120,135],
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
	$('#seachTaskButton').click(function() {
		reloadGridData();
    });
});

/**
 * 查询系统的待办任务情况。
 */
function doSearch(){
	
	var procInstId = $("#procInstId").val();
	var assignee = $("#assignee").val();
	$("#grid-table").jqGrid('setGridParam', {
		page: 1,
	    url:context+'/activiti/processTaskInst.do?method=getTaskInstByPage',
	    postData: {'procInstId':procInstId,'assignee':assignee},
	    datatype: "json"
	}).trigger("reloadGrid");
}

/**
 * 执行任务
 */
function runTask(taskInstId){
	
	$.ajax({
		url: context+'/activiti/processTaskInst.do',
		type: 'post',
		data: {taskInstId:taskInstId,method:'runTask'},
		dataType: 'json',
		success: function(data){
			if(data.success == '1'){
				layer.msg("任务执行成功！",{icon:1});
				reloadGridData();
			}else{
				layer.msg("任务执行失败！",{icon:2});
			}
		},
		error: function(){
			layer.msg("任务执行失败！",{icon:2});
		}
	});
}

function fuzzQueryUser(){
	$("#userName").autocomplete({  
		scroll:true,
        source:function(request,response){  
            $.ajax({  
                type:"POST",  
                url:context+'/activiti/processDefinition.do',
                dataType : "json",  
                cache : false,  
                async : false,  
                data : {  
                    "userName" : encodeURI($("#userName").val()),
                     method:'fuzzyQueryUser'
                },  
                success : function(data) {
                    response($.map(data,function(item){  
                        return {  
                            label:item.name,//下拉框显示值  
                            value:item.name,//选中后，填充到下拉框的值  
                            id:item.userId//选中后，填充到id里面的值  
                        }  
                    }));  
                    
                }  
            }); 
        },  
        delay: 100,//延迟100ms便于输入  
        select : function(event, ui) {  
            $("#userId").val(ui.item.id);  
        }  
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
					$("#definitionKey-task").append("<option value='"+key+"'>"+json[key]+"</option>");
				}
			}
			callBack();
		},
		error: function(){
			layer.msg("初始化下拉选项失败！",{icon:2});
		}
	});
}
function initSelect(){
	$(".selectpicker").selectpicker({
		  style: 'btn-info',
		  size: 4
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
	var definitionKey = $("#definitionKey-task").val();
	var taskName =  $("#taskName").val();
	$("#grid-table").jqGrid('setGridParam', {
		page : 1,
		url: context+'/activiti/processTaskInst.do?method=getTaskInstByPage',
		datatype : "json"
	}).trigger("reloadGrid");
}

/*选中或反选*/
function freshSelect(selectId){
	var $this = $("#"+selectId);
	$this.bind("click",function(){
		var check = $(this).attr("check");
		if(check == 'true'){
			$(this).removeAttr("check");
			$(this).find("input[type=checkbox]").prop('checked',false);
		}else{
			$(this).attr("check","true");
			$(this).find("input[type=checkbox]").prop('checked',true);
		}
	});
}

function arrToStr(arr){
	if(arr == null || arr.length==0) return "";
	var str = "";
	for(var i=0;i<arr.length;i++){
		str += arr[i]+",";
	}
	return str.substring(0,str.length-1);
}

function openAddWin(type,rowData){
	var taskInstId = rowData.taskInstId;//待办任务主键id
	var actTaskInstId = rowData.actTaskInstId;//activiti任务id
	var url= context+"/page/activiti/process/task/delegateUser.jsp?type="+type+"&taskInstId="+taskInstId+"&actTaskInstId="+actTaskInstId;
   /* parent.openWin(url,"delegateUser","350px","22%");*/
    parent.layer.open({
		type:2,
		title:"指派审批人",
		shadeClose: false,
		maxmin: true, //开启最大化最小化按钮
	    shade: 0.8,
	    area: ['100%', '100%'],
	    content: url
	});
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


