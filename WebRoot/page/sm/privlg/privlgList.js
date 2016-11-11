var griddata;
var nodes;
var privlgCateTree;//左侧主题树相关
var setting = {
		async: {
			enable: true,
			url:context+'/privlg.do?method=listprivlgCate',
			autoParam:["id", "name=n", "pId=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"}/*,
			dataFilter: filter*/
		},
		
//		async: {  //异步加载数据
//			enable: true,
//			url: context+'/privlg.do?method=listprivlgCate'
//		},
//		check: { //可以选择，多选框
//			enable: true
//		},
//		data: {
//			simpleData: {
//				enable: true
//			}
//		},
		callback: {
			onClick: OnClick
			/*beforeExpand: beforeExpand,
			onAsyncSuccess: onAsyncSuccess*/
		}
};

var cateId = "";

function OnClick(event, treeId, treeNode, clickFlag){
	cateId = treeNode.name;
	doSearch();
	
}


function filter(treeId, parentNode, childNodes) {
	
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].name;
		childNodes[i].id = childNodes[i].id;
		childNodes[i].pid = 0;
		//childNodes[i].url = context+'/privlg.do?method=listprivlg&cateId='+childNodes[i].privCateId;
	}
	return childNodes;
}

var tool = [{ text: ' 添加 ' , iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
    //$('#PrivModal').modal('show');
					openAddWin('addPriv');
			}
			}, '-', 
			{ text: ' 查看详细' , iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
				var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
				if(ids.length==1){
					var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
					//alert(rowData.PrivName+"----"+rowData.memo);
					openViewWin('viewPriv',rowData);
				}else{
					alert("请选择一条记录！");
				}
			  
			}
			}, '-', 
			{ text: ' 修改 ' , iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
				var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
				if(ids.length==1){
					var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
					openUpdateWin('updatePriv',rowData);
				}else{
					alert("请选择一条记录！");
				}
			
			}
			}, '-', 
			{ text: ' 批量删除 ' , iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');//--多个选中的id
			if(ids.length<1){
				alert("请至少选择一条权限信息！");
				return;
			}
			var idsStr = "";
			if(confirm("您确定要删除选择的权限吗？")){
				  //获取要删除的PrivId
				for(var i=0;i<ids.length;i++){
					  var rowData = $("#grid-table").jqGrid('getRowData',ids[i]),
					      privId = rowData.privId;
					  idsStr += ","+privId;
				  };
				  deletePrivsByIds(idsStr.substring(1));
			 }
			//alert("删除----》"+idsStr.substring(1));
			}
			}];

$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	/*$.ajax({
		type:"POST",
		dataType : 'JSON',
		url:context +"/privlg.do?method=listprivlgCate",
		error:function(json){alert("erro");},
		success:function(json){
			var dataObj=eval(json);   
			for(var i=0;i<dataObj.length;i++){
				alert(dataObj[i]+"---1-");
			    var bj = eval("("+dataObj[i]+")");
			    alert(dataObj[i]+"---2-");
				alert(bj.id+" "+bj.priv_cate_name);     
			}  
		}
	});  */
	
	privlgCateTree = $.fn.zTree.init($("#privlgcatetree"), setting);
	//privlgCateTree =  $.fn.zTree.getZTreeObj("privlgCateTree");
	
	var girdHeigth = getGridHeight("grid-table");
	
	$("#grid-table").jqGrid({
		
		url : context + "/privlg.do?method=listprivlg",
		data : griddata,
		datatype : "JSON", // 数据来源
		mtype : "POST", // 提交方式
		regional: 'cn',
		height : girdHeigth, // 高度
		colNames : [ '权限ID', '权限名称', '权限描述', '权限类型','权限类别', '父权限编号ID', '创建日期', '状态', '备注' ],
		colModel : [ {name : 'privId',index : 'privId',	width : '20%',align : 'center',hidden : true}, 
		             {name : 'privName',index : 'privName',	width : '20%',align : 'center'},
		             {name : 'privDesc',index : 'privDesc',	width : '20%',align : 'center'},
		             {name : 'privType',index : 'privType',	width : '20%',align : 'center',formatter : function(cellValue){
		            	 var value = "";
			             if("1" == cellValue){
			            	value = "BIEE内置权限组";
			            }else if("2" == cellValue){
			            	value = "web权限组";
			            }
			            return value;
		             }},
		             {name : 'cateId',index : 'cateId',width : '20%',align : 'center'}, 
		             {name : 'pPrivId',index : 'pPrivId',width : '20%',align : 'center',hidden : true}, 
		             {name : 'createDt',index : 'createDt',	width : '20%',align : 'center'},
		             {name : 'privStat',index : 'privStat',	width : '20%',align : 'center',formatter : function(cellValue){
		            	 var value = "";
		            	 if("0" == cellValue){
		            		 value = "下架";
		            	 }else if("1" == cellValue){
		            		 value = "上架";
		            	 }
		            	 return value;
		             }},
		             {name : 'memo',index : 'memo',	width : '20%',align : 'center'}
					],
		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		pager : pager_selector,
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
		/*caption: "权限信息列表",*/
		autowidth: true
		});
		/*.jqGrid('setGridParam', {
		    page : 1,
		    url:context+"/privlg.do?method=listprivlg",
		    datatype : "json"
		}).trigger("reloadGrid");*/

	//alert($('.span10').height());
	$('#privlgcate_tree').height($('.span10').height()-15);
	
	//初始化权限类别
//	initPrivCate();
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
	var privname = $("#privname_id").val();
	var privState = $("#privState_id").val();
	if(privname == null || privState == null){
		return ;
	}
	privname = encodeURI(privname);
	var cate_id = encodeURI(cateId);
	$("#grid-table").jqGrid('setGridParam', {
	    page : 1,
	    url : context+"/privlg.do?method=listprivlg",
	    postData : {'privName':privname, 'privState':privState, 'cateId':cate_id},
	    datatype : "json"
	}).trigger("reloadGrid");
	
}

/**
 * 根据类型打开模态框 
 *  load模窗口的数据
 * @param type
 * @param rowdata
 */
/*function openModalWin(type,rowData){
	$('#saveUpdateBtn').show();
	//初始化父权限编号ID
	initPPrivId(type,rowData);
	if(type != "addPriv"){
		if("上架" == rowData.privStat){
			rowData.privStat = "1";
		}else if("下架" == rowData.privStat){
			rowData.privStat = "0";
		}
		
		if("BIEE内置权限组" == rowData.privType){
			rowData.privType = "1";
		}else if("web权限组" == rowData.privType){
			rowData.privType = "2";
		}
	}
	if(type=="addPriv"){
		$('#pivTitle').html("添加权限");
		$('#priv_id').attr('value',"");
		$('#priv_name').attr('value',"");
		$('#priv_desc').attr('value',"");
		$('#parent_priv option').each(function(){
			$(this).attr('selected',false);
		});
		$('#priv_type option').each(function(){
			$(this).attr('selected',false);
		});
		$('#cate_id').each(function(){
			$(this).attr('selected',false);
		});
		$('#privstat_id').each(function(){
			$(this).attr('selected',false);
		});
		$('#priv_memo').text("");
	}else if(type=="viewPriv"){
		$('#pivTitle').html("查看权限【"+rowData.privName+"】");
		$('#priv_id').attr('value',rowData.privId);
		$('#priv_name').attr('value',rowData.privName);
		$('#priv_desc').attr('value',rowData.privDesc);
		 
		$('#parent_priv option').each(function(){
			if($(this).val()==rowData.pPrivId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_type option').each(function(){
			if($(this).val()==rowData.privType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#cate_id option').each(function(){
			if($(this).val()==rowData.cateId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#privstat_id option').each(function(){
			if($(this).val()==rowData.privStat) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		 });
		$('#priv_memo').text(rowData.memo);
		$('#saveUpdateBtn').hide();
	}else if(type=="updatePriv"){
		$('#pivTitle').html("修改权限【"+rowData.privName+"】");
		$('#priv_id').attr('value',rowData.privId);
		$('#priv_name').attr('value',rowData.privName);
		$('#priv_desc').attr('value',rowData.privDesc);
		
		$('#parent_priv option').each(function(){
			if($(this).val()==rowData.pPrivId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_type option').each(function(){
			if($(this).val()==rowData.privType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#cate_id option').each(function(){
			if($(this).val()==rowData.cateId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#privstat_id option').each(function(){
			if($(this).val()==rowData.privStat) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_memo').text(rowData.memo);
	}
	
	var slModalHtml = $('#slModal').html();
	parent.showSlModal('privModal',slModalHtml,type);
}
*/
function openAddWin(type){
	var url= context+"/page/sm/privlg/addOrUpdate.jsp?type="+type+"&cateId="+encodeURI(encodeURI(cateId));
    parent.openWin(url,"addPriv");	
}
function openViewWin(type,rowData){
	var privId = rowData.privId;
	var privName = rowData.privName;
	var privDesc = rowData.privDesc;
	var pPrivId = rowData.pPrivId;
	var privType = rowData.privType;
	var cateId = rowData.cateId;
	var privStat = rowData.privStat;
	var memo = rowData.memo;
	var url= context+"/page/sm/privlg/addOrUpdate.jsp?type="+type+"&privId="+privId+"&privName="+encodeURI(encodeURI(privName))+"&privDesc="+encodeURI(encodeURI(privDesc))+"&pPrivId="+pPrivId+"&privType="+encodeURI(encodeURI(privType))+"&cateId="+encodeURI(encodeURI(cateId))+"&privStat="+encodeURI(encodeURI(privStat))+"&memo="+encodeURI(encodeURI(memo));
    parent.openWin(url,"viewPriv");	
}
function openUpdateWin(type,rowData){
	var privId = rowData.privId;
	var privName = rowData.privName;
	var privDesc = rowData.privDesc;
	var pPrivId = rowData.pPrivId;
	var privType = rowData.privType;
	var cateId = rowData.cateId;
	var privStat = rowData.privStat;
	var memo = rowData.memo;
	var url= context+"/page/sm/privlg/addOrUpdate.jsp?type="+type+"&privId="+privId+"&privName="+encodeURI(encodeURI(privName))+"&privDesc="+encodeURI(encodeURI(privDesc))+"&pPrivId="+pPrivId+"&privType="+encodeURI(encodeURI(privType))+"&cateId="+encodeURI(encodeURI(cateId))+"&privStat="+encodeURI(encodeURI(privStat))+"&memo="+encodeURI(encodeURI(memo));
    parent.openWin(url,"updatePriv");
}
/**
 * 接收返回信息
 * @param el
 * @param method
 *//*
function rtSlModal(el,method){
	//$('#PrivModal').replaceWith(el);
	var PrivName = parent.$('#if_PrivName').val();
	var PrivDesc = parent.$('#if_PrivDesc').val();
	var appId = parent.$('#if_appId').val();
	var memo = parent.$('#if_memo').val();
	
	if(method=='addPriv'){
		alert(data);
	}else if(method=='updatePriv'){
		alert(data);
	}
	var data = parent.$('#PrivInfoFrom').serialize();
	$.ajax({ 
         type:'post', 
         url:context+'/privlg.do?method='+method, 
         data:data, 
         success:function(msg){
            if(msg.code=='1'){
                alert("保存成功！");        //弹出成功msg
                //刷新表格数据
                doSearch();
                parent.$('#privModal').modal('hide');
            }else{
                alert(msg.message);  //弹出失败msg
            }
         },
         error:function(msg){
            alert("访问服务器出错！");        
         }
    });
}*/

/**
 * 批量删除
 * @param ids
 */
function deletePrivsByIds(ids){
	$.ajax({ 
        type:'post', 
        url:context+'/privlg.do?method=batchDelete', 
        data:"&ids="+ids, 
        success:function(msg){
           if(msg.code=='1'){
               alert("删除成功！");        //弹出成功msg
               //刷新表格数据
               doSearch();
           }else{
               alert(msg.message);  //弹出失败msg
           }
        },
        error:function(msg){
           alert("访问服务器出错！");        
        }
   });
}

/*
 * 初始化权限类别
 
function initPrivCate(){
	$.ajax({ 
		type:'post', 
        url:context+'/privlg.do?method=initPrivCate', 
        success:function(data){
        	var options = "";
        	for(var i=0;i<data.length;i++){
        		var privCateName = data[i].privCateName;
        		options += "<option value="+privCateName+">"+privCateName+"</option>";
        	}
        	if(options != ""){
        		$('#cate_id').html(options);
        	}
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
   });
}


 * 初始化父权限编号ID
 
function initPPrivId(type,rowData){
	$.ajax({ 
		type:'post', 
        url:context+'/privlg.do?method=initPPrivId', 
        async:false,
        success:function(data){
        	var options = "<option value=''>无</option>";
        	for(var i=0;i<data.length;i++){
        		var privId = data[i].privId;
        		var privName = data[i].privName;
        		if(type != "addPriv" && privId == rowData.privId){
        			continue;
        		}
        		options += "<option value="+privId+">"+privName+"</option>";
        	}
        	$('#parent_priv').html(options);
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
   });
}*/