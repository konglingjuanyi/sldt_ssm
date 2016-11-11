
var tool = [{ text: ' 查看详细', iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						openWin("view",rowData.noticeId);
					}else{
						alert("请选择一条记录！");
					}
				   
				}
			},'-',
            { text: ' 添加 ', iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
	                  openWin('add',null);
				}
			},'-',
			{ text: ' 修改 ', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						openWin("update",rowData.noticeId);
					}else{
						alert("请选择一条记录！");
					}
				}
			}, '-',
			{ text: ' 删除 ', iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length >= 1){
						var idsStr="";
						for(var i=0;i<ids.length;i++){
							var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
							var noticeId = rowData.noticeId;
							idsStr += noticeId+";";
						}
						idsStr=idsStr.substr(0,idsStr.length-1);
						deleteByIds(idsStr);
					}else{
						layer.msg('至少选择一条记录！', {shift: 6});
					}
				}
			}, '-',
			/*{ text: ' 预览', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openPrivWin("cfgMenuPrivlg", rowData);
					}else{
						alert("请选择一条记录！");
					}
				}
			},*/
			{text: '发布', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length >= 1){
						var idsStr="";
						for(var i=0;i<ids.length;i++){
							var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
							var noticeId = rowData.noticeId;
							idsStr += noticeId+";";
						}
						idsStr=idsStr.substr(0,idsStr.length-1);
						publishByIds(idsStr);
					}else{
						layer.msg('至少选择一条记录！', {shift: 6});
					}
				}
			},
			{text: ' 废弃', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length >= 1){
						var idsStr="";
						for(var i=0;i<ids.length;i++){
							var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
							var noticeId = rowData.noticeId;
							idsStr += noticeId+";";
						}
						idsStr=idsStr.substr(0,idsStr.length-1);
						abandonByIds(idsStr);
					}else{
						layer.msg('至少选择一条记录！', {shift: 6});
					}
				}
			}
			];
			
$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		datatype: "local",
		height: 240,
		regional: 'cn',
		colNames: [ '公告ID', '公告标题', '公告副标题', '公告内容', '发布时间', '发布用户', '状态' ],
		colModel: [{
						name : 'noticeId',
						width : 40,
						hidden : true
					}, {
						name : 'title',
						width : 80
					}, {
						name : 'subTitle',
						width : 80,
						hidden : true
					}, {
						name : 'content',
						width : 80,
						hidden : true
					}, {
						name : 'publishTime',
						width : 80
					}, {
						name : 'publishUser',
						width : 80
					},
					{
						name : 'state',
						width : 80,
						formatter:function(cellValue){
			            	var value = "";
			            	if("1" == cellValue){
			            		value = "草稿";
			            	}else if("2" == cellValue){
			            		value = "发布";
			            	}else if("3" == cellValue){
			            		value = "废弃";
			            	}
			            	return value;
			            }
					}], 
		viewrecords: true,
		rowNum: 10,
		rowList: [10,20,30],
		pager: pager_selector,
		altRows: true,
		toolbar: [ true, "top" ,tool],
		multiselect: true,
        multiboxonly: true,

		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},

		caption: "公告信息表",
		autowidth: true
	}).jqGrid('setGridParam', {
		page: 1,
	    url: root+'/notice.do?method=noticeListByPage',
	    datatype: "json"
	}).trigger("reloadGrid");
	
});

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

function doSearch(){
	var title = $("#shTitle").val();
	var state = $("#shState").val();
	if(title!="" ){
		title="%"+title+"%"
	}
	$("#grid-table").jqGrid('setGridParam', {
		page: 1,
	    url: root+'/notice.do?method=noticeListByPage',
	    postData: {'state':state,'title':title},
	    datatype: "json"
	}).trigger("reloadGrid");
}

function openWin(type,id){
	var url="";
	var title="";
	if(type=="add"){
		title="添加公告"
		url=root+"/page/sm/notice/noticeDetail.jsp?type="+type;
	}else if(type=="update"){
		title="更新公告"
		url=root+"/notice.do?method=noticeDetailInit&type="+type+"&id="+id;
	}else if(type=="view"){
		title="公告详情"
		url=root+"/notice.do?method=noticeDetailInit&type="+type+"&id="+id;
	}
	parent.layer.open({
		type:2,
		title:title,
		shadeClose: false,
		maxmin: true, //开启最大化最小化按钮
	    shade: 0.8,
	    area: ['1000px', '90%'],
	    content: url
	});

}
function openViewWin(type,rowData){
	var menuId = rowData.menuId;
	var menuName = rowData.menuName;
	var menuDesc = rowData.menuDesc;
	var menuLevel = rowData.menuLevel;
	var menuUrl = rowData.menuUrl;
	var menuOrder = rowData.menuOrder;
	var pMenuId = rowData.pMenuId;
	var appId = rowData.appId;
	var url= root+"/page/sm/menu/addOrUpdateMenu.jsp?type="+type+"&menuId="+menuId+"&menuName="+encodeURI(encodeURI(menuName))+"&menuDesc="+encodeURI(encodeURI(menuDesc))+"&menuLevel="+menuLevel+"&menuUrl="+menuUrl+"&menuOrder="+menuOrder+"&pMenuId="+pMenuId+"&appId="+appId;
    parent.openWin(url,"viewMenu");	
}
function openUpdateWin(type,rowData){
	var menuId = rowData.menuId;
	var menuName = rowData.menuName;
	var menuDesc = rowData.menuDesc;
	var menuLevel = rowData.menuLevel;
	var menuUrl = rowData.menuUrl;
	var menuOrder = rowData.menuOrder;
	var pMenuId = rowData.pMenuId;
	var appId = rowData.appId;
	var url= root+"/page/sm/menu/addOrUpdateMenu.jsp?type="+type+"&menuId="+menuId+"&menuName="+encodeURI(encodeURI(menuName))+"&menuDesc="+encodeURI(encodeURI(menuDesc))+"&menuLevel="+menuLevel+"&menuUrl="+menuUrl+"&menuOrder="+menuOrder+"&pMenuId="+pMenuId+"&appId="+appId;
    parent.openWin(url,"updateMenu");
}

/**
 * 批量删除
 * @param ids
 */
function deleteByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: root + '/notice.do?method=deleteNotice', 
        data: {"ids":ids},
        success: function(msg){
        	if(msg.code=='1'){
        		parent.layer.alert("删除成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		parent.layer.alert(msg.message); //弹出失败msg
        	}
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}
/**
 * 废弃
 * @param ids
 */
function abandonByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: root + '/notice.do?method=changeState', 
        data: {"ids":ids,"state":"3"},
        success: function(msg){
        	if(msg.code=='1'){
        		parent.layer.alert("废弃成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		parent.layer.alert(msg.message); //弹出失败msg
        	}
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}
/**
 * 发布
 * @param ids
 */
function publishByIds(ids){
	$.ajax({ 
		type: 'post', 
		url: root + '/notice.do?method=changeState', 
		data: {"ids":ids,"state":"2"},
		success: function(msg){
			if(msg.code=='1'){
				parent.layer.alert("发布成功！"); //弹出成功msg
				//刷新表格数据
				doSearch();
			}else{
				parent.layer.alert(msg.message); //弹出失败msg
			}
		},
		error:function(msg){
			parent.layer.alert("访问服务器出错！");        
		}
	});
}

