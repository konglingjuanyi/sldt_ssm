
var tool = [{ text: ' 添加 ', iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
	                         openAddWin('addMenu',null);
				}
			}, '-',
			{ text: ' 查看详细', iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						openViewWin('viewMenu',rowData);
					}else{
						alert("请选择一条记录！");
					}
				   
				}
			}, '-',
			{ text: ' 修改 ', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						openUpdateWin('updateMenu',rowData);
					}else{
						alert("请选择一条记录！");
					}
				}
			}, '-',
			{ text: ' 批量删除 ', iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow'); //--多个选中的id
					if(ids.length<1){
						alert("请至少选择一个菜单！");
						return;
					}
					var idsStr = "";
					if(confirm("您确定要删除选择的菜单吗？")){
						//获取要删除的roleId
						for(var i=0;i<ids.length;i++){
							var rowData = $("#grid-table").jqGrid('getRowData',ids[i]),
							menuId = rowData.menuId;
							idsStr += ","+menuId;
						};
						deleteMenusByIds(idsStr.substring(1));
					 }
				}
			}, '-',
			{ text: ' 权限配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openPrivWin("cfgMenuPrivlg", rowData);
					}else{
						alert("请选择一条记录！");
					}
				}
			}];
			
$(document).ready(function() {
	//查询子系统设置系统下拉选项
	initSubSysInfo();
	
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	var gridHeigth=getGridHeight("grid-table");
	jQuery(grid_selector).jqGrid({
		url: root+'/menu.do?method=listMenu',
		 datatype: "json",
		height: gridHeigth,
		regional: 'cn',
		colNames: [ '菜单编号', '菜单名称', '菜单描述', '菜单层级', '路径', '排序次号', '菜单类型', '父级菜单编号', '父级菜单名称','归属子系统编号','归属子系统','参数2','参数3' ],
		colModel: [
					{
						name : 'menuId',
						index : 'menuId',
						width : 40,
						hidden : true
					}, {
						name : 'menuName',
						index : 'menuName',
						width : 80
					}, {
						name : 'menuDesc',
						index : 'menuDesc',
						width : 100
					}, {
						name : 'menuLevel',
						index : 'menuLevel',
						width : 40,
						formatter:function(cellValue){
			            	var value = cellValue+" 级菜单";
			            	return value;
			            }
					}, {
						name : 'menuUrl',
						index : 'menuUrl',
						width : 160
					}, {
						name : 'menuOrder',
						index : 'menuOrder',
						width : 40
					},{
						name : 'menuType',
						index : 'menuType',
						width : 40,
						formatter:function(cellValue){
			            	var value = "";
			            	if("01" == cellValue){
			            		value = "系统菜单";
			            	}else if("02" == cellValue){
			            		value = "功能菜单";
			            	}else if("03" == cellValue){
			            		value = "业务菜单";
			            	}
			            	return value;
			            },
			            unformat:function(cellValue){
			            	var value = "";
			            	if("系统菜单" == cellValue){
			            		value = "01";
			            	}else if("功能菜单" == cellValue){
			            		value = "02";
			            	}else if("业务菜单" == cellValue){
			            		value = "03";
			            	}
			            	return value;
			            }
					}, {
						name : 'pMenuId',
						index : 'pMenuId',
						width : 80,
						hidden:true
					},{name:'pMenuName',index:'pMenuName',width:80}, {
						name : 'appId',
						index : 'appId',
						width : 80,
						hidden:true
					},{
						name : 'appName',
						index : 'appName',
						width : 80
					},{
						name : 'menuParam2',
						index : 'menuParam2',
						width : 80,
						hidden:true
					},{
						name : 'menuParam3',
						index : 'menuParam3',
						width : 80,
						hidden:true
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

		/*caption: "菜单信息表",*/
		autowidth: true
	});
	
});


/**
 * 动态读取数据库中的子系统注册配置信息，并显示信息到首页
 */
function initSubSysInfo(){
	 $.ajax({
		 url:root+"/system.do?method=listSystem",
		 type:'post',
		 data:{
			 currentPage:1,
			 pageSize:10
			 },
		 dataType:'json',
		 async:false,
		 success:function(data){
			 if(data && data.length>0){
				 var appIdSecHtml = "<option value='' selected>全部</option>";
				 for(var i=0;i<data.length;i++){
				      appIdSecHtml +='<option value="'+data[i].appId+'">'+data[i].appName+'</option>';
				 }
				 $('#appId').html(appIdSecHtml);
			 }
		 },
		 error:function(){
			 alert("访问服务器出错！");
		 }
	 });
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

function doSearch(){
	var menuName = $("#searchMenuNm").val();
	var pMenuName = $("#searchPMenuNm").val();
	
	var menuType = $("#searchMenuType").val();
	var appId = $("#appId").val();
	//alert(menuType);
	menuName = encodeURI(menuName);
	pMenuName = encodeURI(pMenuName);
	$("#grid-table").jqGrid('setGridParam', {
		page: 1,
	    url: root+'/menu.do?method=listMenu',
	    postData: {
	    	 'menuName':menuName,
	    	 'menuType':menuType,
	    	 'pMenuName':pMenuName,
	    	 'appId':appId
	    	 },
	    datatype: "json"
	}).trigger("reloadGrid");
}


function openAddWin(type){
	var url= root+"/page/sm/menu/addOrUpdateMenu.jsp?type="+type;
    parent.openWin(url,"addMenu");	
}

function openViewWin(type,rowData){
	var menuId = rowData.menuId;
	var menuName = rowData.menuName;
	var menuDesc = rowData.menuDesc;
	    menuDesc =menuDesc.replace(/\n/g,'@');
	var menuLevel = rowData.menuLevel;
	var menuUrl = rowData.menuUrl;
		menuUrl = menuUrl.replace('http://',"$");
		menuUrl = menuUrl.replace(/\&/g,"@");
		menuUrl = menuUrl.replace(/\?/g,"*");
		
	var menuOrder = rowData.menuOrder;
	var pMenuId = rowData.pMenuId;
	var appId = rowData.appId;
	var menuType = rowData.menuType;
	var menuParam2 = rowData.menuParam2;
	var menuParam3 = rowData.menuParam3;
	var appName = encodeURI(encodeURI(rowData.appName));
	var pMenuName = encodeURI(encodeURI(rowData.pMenuName));
	var url= root+"/page/sm/menu/addOrUpdateMenu.jsp?type="+type+
	"&menuId="+menuId+"&menuName="+encodeURI(encodeURI(menuName))+"&menuParam2="+menuParam2+"&menuParam3="+menuParam3+
	"&menuDesc="+encodeURI(encodeURI(menuDesc))+"&menuLevel="+menuLevel+"&menuType="+menuType+"&appName="+appName+
	"&menuUrl="+menuUrl+"&menuOrder="+menuOrder+"&pMenuId="+pMenuId+"&appId="+appId+"&pMenuName="+pMenuName;
    parent.openWin(url,"viewMenu");	
}

function openUpdateWin(type,rowData){
	var menuId = rowData.menuId;
	var menuName = rowData.menuName;
	var menuDesc = rowData.menuDesc;
	    menuDesc =menuDesc.replace(/\n/g,'@');
	var menuLevel = rowData.menuLevel;
	var menuUrl = rowData.menuUrl;
		menuUrl = menuUrl.replace('http://',"$");
		menuUrl = menuUrl.replace(/\&/g,"@");
		menuUrl = menuUrl.replace(/\?/g,"*");
		
	var menuOrder = rowData.menuOrder;
	var pMenuId = rowData.pMenuId;
	var appId = rowData.appId;
	var menuType = rowData.menuType;
	var menuParam2 = rowData.menuParam2;
	var menuParam3 = rowData.menuParam3;
	var appName = encodeURI(encodeURI(rowData.appName));
	var pMenuName = encodeURI(encodeURI(rowData.pMenuName));
	var url= root+"/page/sm/menu/addOrUpdateMenu.jsp?type="+type+
	"&menuId="+menuId+"&menuName="+encodeURI(encodeURI(menuName))+"&menuParam2="+menuParam2+"&menuParam3="+menuParam3+
	"&menuDesc="+encodeURI(encodeURI(menuDesc))+"&menuLevel="+menuLevel+"&menuType="+menuType+"&appName="+appName+
	"&menuUrl="+menuUrl+"&menuOrder="+menuOrder+"&pMenuId="+pMenuId+"&appId="+appId+"&pMenuName="+pMenuName;
    parent.openWin(url,"updateMenu");
}


/**
 * 批量删除
 * @param ids
 */
function deleteMenusByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: root + '/menu.do?method=batchDelete', 
        data: "&ids="+ids, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("删除成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		alert(msg.message); //弹出失败msg
        	}
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
	});
}

function openPrivWin(type, rowData){
	//$('#menuPrivlgTitle').html("权限配置【"+rowData.menuName+"】");
	var url = root+"/page/sm/menu/menuPrivlg.jsp?menuId="+rowData.menuId+"&pMenuId="+rowData.pMenuId;
	/*$('#menuPrivlgWin').attr("src", url);
	var slModalHtml = $('#cfgPrivlgModal').html();
	parent.showSlModal('menuPrivlgModal', slModalHtml, type);*/
	parent.openWin(url,"menuPrivlgModal");
}

var zTree;
function showMenu() {
	if(!zTree){ //只加载一次
		//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
		zTree = $.fn.zTree.init($("#clsTree"), setting);
	}
	var obj = $("#if_pMenuId");
	var offset = $("#if_pMenuId").offset();
	$("#menuContent").css({left: offset.left + "px", top: offset.top + obj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}