var tool = [{ text: ' 查看 ', iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openViewWin('viewPrivCate', rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, '-', { text: ' 添加 ', iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
		openAddWin('addPrivCate', null);
		}
	}, '-', { text: ' 修改 ', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openUpdateWin('updatePrivCate', rowData);
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
				// 获取要删除的privCateId
				for(var i=0;i<ids.length;i++){
					var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
					var privCateId = rowData.privCateId;
					idsStr += "," + privCateId;
				}
				deletePrivCatesByIds(idsStr.substring(1));
			}
		}
	}];
			
$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var girdHeigth = getGridHeight("grid-table");
	jQuery(grid_selector).jqGrid({
		datatype: "local",
		regional: 'cn',
		height: girdHeigth,
		colNames: ['目录类别ID', '类别名称', '选择类型'],
		colModel : [
		            {name:'privCateId', index:'privCateId', width:'20%', align:'center', hidden:true},
		            {name:'privCateName', index:'privCateName', width:'20%', align:'center'},
		            {name:'checkType', index:'checkType', width:'20%', align:'center',formatter:function(val,opt,rObj){
		            	if(val == '0') {
		            		return "单选";
		            	}
		            	if(val == '1') {
		            		return "多选";
		            	}
		            }}
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
		/*caption: "权限类别表",*/
		autowidth: true
	}).jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/privCate.do?method=listPrivCate',
	    datatype: "json"
	}).trigger("reloadGrid");
	
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
	})
}

function doSearch(){
	var privCateName = $("#searchPrivCateName").val();
	if(privCateName == null){
		return;
	}
	privCateName = encodeURI(privCateName);
	$("#grid-table").jqGrid('setGridParam', {
		page: 1,
	    url: context + '/privCate.do?method=listPrivCate',
	    postData: {'privCateName':privCateName},
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
	if(type=="addPrivCate"){
		$('#privCateTitle').html("添加权限类别");
		$('#if_privCateId').attr('value',"");
		$('#if_privCateName').attr('value',"");
		$('#if_checkType option').each(function(){
			$(this).attr('selected',false);
		});

	}else if(type=="viewPrivCate"){
		$('#privCateTitle').html("查看权限类别【"+rowData.privCateName+"】");
		$('#if_privCateId').attr('value',rowData.privCateId);
		$('#if_privCateName').attr('value',rowData.privCateName);
		$('#if_checkType option').each(function(){
			if($(this).val() == rowData.checkType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#saveBtn').hide();
	}else if(type=="updatePrivCate"){	
		$('#privCateTitle').html("修改权限类别【"+rowData.privCateName+"】");
		$('#if_privCateId').attr('value',rowData.privCateId);
		$('#if_privCateName').attr('value',rowData.privCateName);
		$('#if_checkType option').each(function(){
			if($(this).val() == rowData.checkType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
	}
	
	var slModalHtml = $('#slModal').html();
	parent.showSlModal('privCateModal',slModalHtml,type);
}
*/
function openAddWin(type){
	var url= context+"/page/sm/privCate/addOrUpdate.jsp?type="+type;
    parent.openWin(url,"addPrivCate");	
}
function openViewWin(type,rowData){
	var privCateId = rowData.privCateId;
	var privCateName = rowData.privCateName;
	var checkType = rowData.checkType;
	checkType = checkType == "单选" ? "0" : "1";
	var url= context+"/page/sm/privCate/addOrUpdate.jsp?type="+type+"&privCateId="+privCateId+"&privCateName="+encodeURI(encodeURI(privCateName))+"&checkType="+encodeURI(encodeURI(checkType));
    parent.openWin(url,"viewPrivCate");	
}
function openUpdateWin(type,rowData){
	var privCateId = rowData.privCateId;
	var privCateName = rowData.privCateName;
	var checkType = rowData.checkType;
	checkType = checkType == "单选" ? "0" : "1";
	var url= context+"/page/sm/privCate/addOrUpdate.jsp?type="+type+"&privCateId="+privCateId+"&privCateName="+encodeURI(encodeURI(privCateName))+"&checkType="+encodeURI(encodeURI(checkType));
    parent.openWin(url,"updatePrivCate");
}
/**
 * 接收返回信息
 * @param el
 * @param method
 *//*
function rtSlModal(el, method){
	var data = parent.$('#privCateInfoFrom').serialize();
	$.ajax({ 
		type: 'post', 
        url: context + '/privCate.do?method=' + method, 
        data: data, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("保存成功！"); //弹出成功msg
                //刷新表格数据
                doSearch();
                parent.$('#privCateModal').modal('hide');
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
function deletePrivCatesByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: context + '/privCate.do?method=batchDelete', 
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
