var menuId = null;
var menuName = null;
var menuTree; // 左侧菜单树相关
//按钮类型
var BtnType = {'1':'新增','2':'删除','3':'修改','4':'查询','5':'上传','6':'下载','7':'导入','8':'打印','default':""};
var setting = {
	async: { //异步加载数据
		enable: true,
		url: getUrl
	},
	check: {
		enable: false
	},
	callback: {
		onClick: OnClick
	}
};

function getUrl(treeId, treeNode) {
	return context + '/menu.do?method=loadMenuTree&menuId='+treeNode.id; 
}

var bmNodes = [
               {id:"0000", pId:"0000", name:"所有菜单", open:true, iconSkin:"bmMenu", isParent:true}
              ];

function OnClick(event, treeId, treeNode, clickFlag){
	menuId = treeNode.id;
	menuName = treeNode.name;
	doSearch();
}

var tool = [{ text: ' 查看 ', iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openViewWin('viewBtn', rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, '-', { text: ' 添加 ', iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
			if(menuId == null){
				alert("您好，请选择左侧的菜单树中的一个菜单！");
				return;
			}
			if(menuId == "0000"){
				alert("您好，添加按钮时所选菜单不能为所有菜单，请重新选择！");
				return;
			}
			openAddWin('addBtn', null);
		}
	}, '-', { text: ' 修改 ', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openUpdateWin('updateBtn', rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, '-', { text: ' 批量删除 ', iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length < 1){
				alert("请至少选择一条记录！");
				return;
			}
			var idsStr = "";
			if(confirm("您确定要删除选中的记录吗？")){
				// 获取要删除的btnId
				for(var i=0;i<ids.length;i++){
					var rowData = $("#grid-table").jqGrid('getRowData', ids[i]),
					btnId = rowData.btnId;
					idsStr += "," + btnId;
				};
				deleteBtnsByIds(idsStr.substring(1));
			}
		}
	}, '-', { text: ' 权限配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openPrivWin("cfgBtnPrivlg", rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}];
			
$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	menuTree = $.fn.zTree.init($("#menuTree"), setting, bmNodes);
	
	var girdHeigth = getGridHeight("grid-table");
	jQuery(grid_selector).jqGrid({
		datatype: "local",
		regional: 'cn',
		height: girdHeigth,
		colNames: ['按钮编号', '显示名称', '别名', '按钮描述', '归属菜单', '按钮类型', '图标', '操作动作'],
		colModel : [
		            {name:'btnId', index:'btnId', width:'20%', align:'center', hidden:true},
		            {name:'btnText', index:'btnText', width:'20%', align:'center'},
		            {name:'btnNickname', index:'btnNickname', width:'20%', align:'center'},
		            {name:'btnDesc', index:'btnDesc', width:'20%', align:'center'},
		            {name:'menuId', index:'menuId', width:'20%', align:'center'},
		            {name:'btnType', index:'btnType', width:'20%', align:'center',formatter:function(val,opt,rObj){
		            	val = val || "default";
		            	return BtnType[val];
		            }},
		            {name:'btnIcon', index:'btnIcon', width:'20%', align:'center'},
		            {name:'btnAct', index:'btnAct', width:'20%', align:'center'}
		           ], 
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
		/*caption: "按钮资源表",*/
		autowidth: true
	}).jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/btn.do?method=listBtn',
	    datatype: "json"
	}).trigger("reloadGrid");
	
	$('#menu_tree').height($('.span10').height()-15);
});

// 调整分页图标
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
	});
}

function doSearch(){
	var btnText = $("#searchBtnText").val();
	if(btnText == null){
		return;
	}
	btnText = encodeURI(btnText);
	$("#grid-table").jqGrid('setGridParam', {
		page: 1,
	    url: context + '/btn.do?method=listBtn',
	    postData: {'btnText':btnText, 'menuId':menuId},
	    datatype: "json"
	}).trigger("reloadGrid");
}

/**
 * 根据类型打开模态框 
 * load模窗口的数据
 * @param type
 * @param rowdata
 */
/*function openModalWin(type, rowData){
	$('#saveBtn').show();
	if(type=="addBtn"){
		$('#btnTitle').html("添加按钮");
		$('#if_btnId').attr('value',"");
		$('#if_menuId').attr('value',menuId);
		$('#if_btnText').attr('value',"");
		$('#if_btnNickname').attr('value',""); 
		$('#if_btnDesc').attr('value',"");
		$('#if_btnType option').each(function(){
			$(this).attr('selected',false);
		});
		$('#if_btnIcon').attr('value',"");
		$('#if_btnAct').attr('value',"");

	}else if(type=="viewBtn"){
		$('#btnTitle').html("查看按钮【"+rowData.btnText+"】");
		$('#if_btnId').attr('value',rowData.btnId);
		$('#if_menuId').attr('value',rowData.menuId);
		$('#if_btnText').attr('value',rowData.btnText);
		$('#if_btnNickname').attr('value',rowData.btnNickname); 
		$('#if_btnDesc').attr('value',rowData.btnDesc);
		$('#if_btnType option').each(function(){
			if($(this).val() == rowData.btnType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_btnIcon').attr('value',rowData.btnIcon);
		$('#if_btnAct').attr('value',rowData.btnAct);
		$('#saveBtn').hide();
	}else if(type=="updateBtn"){	
		$('#btnTitle').html("修改按钮【"+rowData.btnText+"】");
		$('#if_btnId').attr('value',rowData.btnId);
		$('#if_menuId').attr('value',rowData.menuId);
		$('#if_btnText').attr('value',rowData.btnText);
		$('#if_btnNickname').attr('value',rowData.btnNickname); 
		$('#if_btnDesc').attr('value',rowData.btnDesc);
		$('#if_btnType option').each(function(){
			if($(this).val() == rowData.btnType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_btnIcon').attr('value',rowData.btnIcon);
		$('#if_btnAct').attr('value',rowData.btnAct);
	}
	
	var slModalHtml = $('#slModal').html();
	parent.showSlModal('btnModal',slModalHtml,type);
}*/
function openAddWin(type){
	var url= context+"/page/sm/btn/addOrUpdateBtn.jsp?type="+type+"&menuId="+menuId;
    parent.openWin(url,"addBtn");	
}
function openViewWin(type,rowData){
	var btnId = rowData.btnId;
	var menuId = rowData.menuId;
	var btnText = rowData.btnText;
	var btnNickname = rowData.btnNickname;
	var btnDesc = rowData.btnDesc;
	var btnType = rowData.btnType;
	var btnIcon = rowData.btnIcon;
	var btnAct = rowData.btnAct;
	var url= context+"/page/sm/btn/addOrUpdateBtn.jsp?type="+type+"&btnId="+btnId+"&menuId="+menuId+"&btnText="+encodeURI(encodeURI(btnText))+"&btnNickname="+encodeURI(encodeURI(btnNickname))+"&btnDesc="+encodeURI(encodeURI(btnDesc))+"&btnType="+encodeURI(encodeURI(btnType))+"&btnIcon="+encodeURI(encodeURI(btnIcon))+"&btnAct="+encodeURI(encodeURI(btnAct));
    parent.openWin(url,"viewBtn");	
}
function openUpdateWin(type,rowData){
	var btnId = rowData.btnId;
	var menuId = rowData.menuId;
	var btnText = rowData.btnText;
	var btnNickname = rowData.btnNickname;
	var btnDesc = rowData.btnDesc;
	var btnType = rowData.btnType;
	var btnIcon = rowData.btnIcon;
	var btnAct = rowData.btnAct;
	var url= context+"/page/sm/btn/addOrUpdateBtn.jsp?type="+type+"&btnId="+btnId+"&menuId="+menuId+"&btnText="+encodeURI(encodeURI(btnText))+"&btnNickname="+encodeURI(encodeURI(btnNickname))+"&btnDesc="+encodeURI(encodeURI(btnDesc))+"&btnType="+encodeURI(encodeURI(btnType))+"&btnIcon="+encodeURI(encodeURI(btnIcon))+"&btnAct="+encodeURI(encodeURI(btnAct));
    parent.openWin(url,"updateBtn");
}
/**
 * 接收返回信息
 * @param el
 * @param method
 *//*
function rtSlModal(el, method){
	var data = parent.$('#btnInfoFrom').serialize();
	$.ajax({ 
		type: 'post', 
        url: context + '/btn.do?method=' + method, 
        data: data, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("保存成功！"); //弹出成功msg
                //刷新表格数据
                doSearch();
                parent.$('#btnModal').modal('hide');
            }else{
            	alert(msg.message); //弹出失败msg
            }
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
    });
}
*/
/**
 * 批量删除
 * @param ids
 */
function deleteBtnsByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: context + '/btn.do?method=batchDelete', 
        data: "&ids=" + ids, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("删除成功！"); // 弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		alert(msg.message); // 弹出失败msg
        	}
        },
        error: function(msg){
        	alert("访问服务器出错！");        
        }
	});
}

function openPrivWin(type, rowData){
	//$('#btnPrivlgTitle').html("权限配置【"+rowData.btnText+"】");
	var url = context+"/page/sm/btn/btnPrivlg.jsp?btnId="+rowData.btnId;
	/*$('#btnPrivlgWin').attr("src", url);
	var slModalHtml = $('#cfgPrivlgModal').html();
	parent.showSlModal('btnPrivlgModal', slModalHtml, type);*/
	parent.openWin(url,"btnPrivlgModal");
}