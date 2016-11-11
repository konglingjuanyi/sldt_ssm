$(document).ready(function(){
	var grid_selector = "#grid-table-definition";
	var pager_selector = "#grid-pager-definition";
	var stateJson = {"1":"有效","2":"无效"};
	/*toolbox*/
	var tool = [{ text: ' 添加 ' , iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
		openAddWin('addDefinition',null);
	}
	}, '-', 
	
	{
		text: ' 修改 ' , iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $(grid_selector).jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[0]);
				openUpdateWin(rowData.procId);
			}else{
				layer.msg("请选择一条记录！",{icon:2});
			}
	   
		}
	}, '-',
	
	{ text: ' 批量删除 ' , iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
			deleteDefinitions();
		}
	}
	, '-',
	/**{ text: ' 转换成模型', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
		var ids = $(grid_selector).jqGrid('getGridParam', 'selarrrow');
		if(ids.length == 1){
			var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[0]);
			var convertUrl = context+"/activiti/processDefinition.do?method=convert2Model&definitionId="+rowData.actProcDefId;
			window.location.href=convertUrl;
		}else{
			layer.msg("请选择一条记录！",{icon:2});
		}
	}
	}, '-', **/
	{ text: ' 启动流程', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
		var ids = $(grid_selector).jqGrid('getGridParam', 'selarrrow');
		if(ids.length == 1){
			var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[0]);
			startInstance(rowData.procId);//已proc_inst的主键
		}else{
			layer.msg("请选择一条记录！",{icon:2});
		}
	}
	}
	
	];
	var girdHeigth = getGridHeight("grid-table-definition")-35;
	jQuery(grid_selector).jqGrid({
		url: context+'/activiti/processDefinition.do?method=getProcessDefinitionByPage',
		datatype: "json",
		height: girdHeigth,
		regional: 'cn', 
		rownumbers:false,
		colNames:['id','流程配置名称','流程key值','<ul class="colUl"><li>流程节点名称</li><li>竞签/会签 </li><li>角色</li></ul>', '状态','流程定义ID'],//,'操作'
		colModel:[
			{
				name:'procId',
				index:'procId',
				width:20, 
				editable: true,
				hidden:true,
				align : 'center'
			},
			{
				name:'procName',
				index:'procName',
				width:50, 
				editable: true,
				align : 'center'
			},
			{
				name:'actProcDefKey',
				index:'actProcDefKey',
				width:50, 
				editable:true,
				align : 'center'
			},
			{
				name:'tasks',
				index:'tasks', 
				width:120,
				editable: true,
				formatter : function(v){
					var processDom = "";
					for(var i=0;i<v.length;i++){
						 processDom += "<ul class='colUl_'>";
						var task = v[i];//任务
						var flag = task.operateType;//节点审批类型
						if(flag == '0'){
							flag = "竞签";
						}else if(flag == '1'){
							flag = "会签";
						}else{
							flag = "";
						}
						var taskOpers = task.taskOpers;//节点的审批用户
						var usersShow = "";
						for(var j=0;j<taskOpers.length;j++){
							//var user = taskUsers[j].user;
							if(j==taskOpers.length-1){
								usersShow += taskOpers[j].groupName;
							}else{
								usersShow += taskOpers[j].groupName+"<br>";
							}
						}
						usersShow = isEmpty(usersShow) ? "&nbsp" : usersShow;
						processDom +="<li>"+task.actName+"</li><li>"+flag+"</li><li>"+usersShow+"</li>";
						processDom += "</ul>";
					}
					
					 
					 return processDom;
					
				},
				align : 'center'
			},
			{
				name:'state',
				index:'state',
				width:20, 
				sortable:false,
				align : 'center',
				formatter : function(v){
					if(isEmpty(v)){
						return "";
					}else{
						return stateJson[v];
					}
				}
			},
			{
				name:'actProcDefId',
				index:'actProcDefId',
				width:0, 
				sortable:false,
				align : 'center',
				hidden:true
			}
//			,{
//				name:'action',
//				index:'action',
//				width:60,
//				editable: true,
//				align : 'center'
//			},
		], 
		gridComplete : function() {
			var ids = jQuery(grid_selector).jqGrid('getDataIDs');
			for ( var i = 0; i < ids.length; i++) {
				var rowData = jQuery(grid_selector).jqGrid('getRowData', ids[i]);
				var convertUrl = context+"/activiti/processDefinition.do?method=convert2Model&definitionId="+rowData.id;
//				var view1 = "<input type='button' class='btn btn-info btn-small' onclick='javascript:window.location.href=\""+convertUrl+"\"' value='转换成模型'  title=\"转换成模型\">";
//				var view2 = "<input type='button' class='btn btn-warning btn-small' onclick='startInstance(\""+rowData.id+"\")' value='启动流程'  title=\"启动流程\">";
//				jQuery(grid_selector).jqGrid('setRowData', ids[i], {action : view1+"&nbsp;&nbsp;"+ view2});
			}
			/**
			 * 解决样式问题
			 */
			$(".colUl_").each(function(){
				var height = $(this).find("li").last().css("height");
				$(this).find("li").last().siblings().css("line-height",height);
			});
		},
		viewrecords : true,
		rowNum:15,
		rowList:[15,30,45,60,75,90,105,120],
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
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
		  useColSpanStyle: true, 
		  groupHeaders:[
		    {startColumnName: 'tasks', numberOfColumns: 1, titleText: '<em style="color:#fff;">流程定制</em>'}
		  ]
		});
	
	//绑定点击事件
	$('#seachDefinitionButton').click(function() {
		reloadGridData();
    });
});

/**
 * 删除
 */
function deleteDefinitions(){
	layer.confirm('确定删除？', {
		btn: ['是','否'], //按钮
		shade: false //不显示遮罩
		}, function(){
			var idsArr = $("#grid-table-definition").jqGrid("getGridParam","selarrrow");
			var idstr = [];
			$.each(idsArr,function(i,item){
				var rowData = jQuery("#grid-table-definition").jqGrid('getRowData', item);//procId
				idstr.push(rowData["procId"]);
			});
			var ids = arrToStr(idstr);
			
			if(isEmpty(ids)){
				layer.msg("没有选择要删除的流程定义！",{icon:2})
				return false;
			}
			$.ajax({
				url: context+'/activiti/processDefinition.do',
				type: 'post',
				data: {ids:ids,method:'deleteProcessDefinition'},
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
					$("#definitionKey").append("<option value='"+key+"'>"+json[key]+"</option>");
				}
			}
			callBack();
		},
		error: function(){
			layer.msg("初始化下拉选项失败！",{icon:2});
		}
	});
}

function openAddWin(type){
	var url= context+"/page/activiti/process/definition/addDefinition.jsp?type="+type;
    parent.openWin(url,"addDefinition","800px");	
}

function openUpdateWin(procId){
	var url= context+"/page/activiti/process/definition/addDefinition.jsp?procId="+procId;
	parent.openWin(url,"addDefinition","800px");
}

/**
 * 启动流程
 */
function startInstance(id){
	$.ajax({
		url: context+'/activiti/processDefinition.do',
		type: 'post',
		data: {definitionId:id,method:'startInstance'},
		dataType: 'json',
		success: function(data){
			if(data.success == '1'){
				layer.msg("启动成功！",{icon:1});
				//reloadGridData();
			}else{
				layer.msg("启动失败！",{icon:2});
			}
		},
		error: function(){
			layer.msg("启动失败！",{icon:2});
		}
	});
}

/**
 * 判断是否为空
 */
function isEmpty(param){
	return param === undefined || param == null || param.length==0;
}

//调整分页图标
function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'fa fa-angle-double-left',
			'ui-icon-seek-prev' : 'fa fa-angle-left',
			'ui-icon-seek-next' : 'fa fa-angle-right',
			'ui-icon-seek-end' : 'fa fa-angle-double-right'
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
	var procDefName = $("#procDefName").val();
	$("#grid-table-definition").jqGrid('setGridParam', {
	page : 1,
	url: context+'/activiti/processDefinition.do?method=getProcessDefinitionByPage&procDefName='+encodeURI(encodeURI(procDefName)),
	datatype : "json"
	}).trigger("reloadGrid");
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


