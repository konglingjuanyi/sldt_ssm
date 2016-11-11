var TotalPage=1;
var tool = [
            {
				text : '详情',
				iconCls : 'fa fa-list-alt icolor',
				btnCls : 'button-gp button-add',
				handler : function() {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						//打开修改窗口
						openWindow("detail",rowData.quesId);
					}
					else{
						layer.msg('请选择一条记录！', {shift: 6});
					}
				}
			} ,{
			text : ' 新增数据质量问题',
			iconCls : 'fa fa-plus icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				openWindow("add",null);
			}
		},{
			text : ' 更新数据质量问题',
			iconCls : 'fa fa-pencil icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				//获得选中的id
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if(ids.length==1){
					var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
					//打开修改窗口
					openWindow("update",rowData.quesId);
				}
				else{
					layer.msg('请选择一条记录！', {shift: 6});
				}
				
			}
		},{
			text : '删除数据质量问题',
			iconCls : 'fa fa-trash-o icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if(ids.length==1){
					var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
					deleteQues(rowData.quesId);
				}
				else{
					layer.msg('请选择一条记录！', {shift: 6});
				}
				
			}
		},{
			text : '上传',
			iconCls : 'fa fa-upload icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				openUploadFileWin();
			}
		},{
			text : '模板下载',
			iconCls : 'fa fa-download icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				var url= context+"/excelTemplate/"+"sm_ques_template.xls";
				$.ajax({
					type: 'post', 
					 url: context + '/announce.do?method=fileExist', 
					 data: {"filePath":"sm_ques_template.xls"}, 
					 success: function(data){
					 	if(data){window.open (url);}
							   else{layer.msg('文件不存在！', {shift: 6});}
					 },
					 error:function(msg){
						 parent.layer.alert("访问服务器出错！+to");        
					 }
					});
			}
		} 
		];


$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";	
	
	jQuery(grid_selector).jqGrid({
		url : context+ '/announce.do?method=announce',
		datatype : "json",
		mtype : "POST",
		height: "230",
		autowidth: true,
		regional: 'cn',
		colNames : [ '质量问题编号', '数据质量问题描述',
					'涉及系统', '涉及部门','问题提出人', '问题提出时间',
					'问题分类', '问题发布状态'],
		colModel : [ {
					name : 'quesId',
					width : '20%',
					align : 'left',
							}, {
								name : 'quesDesc',
								width : '20%',
								align : 'left'
							}, {
								name : 'aboutSys',
								width : '20%',
								align : 'left'
							}, {
								name : 'aboutDept',
								width : '20%',
								align : 'left'
							},{
								name : 'quesPerson',
								width : '20%',
								align : 'left'
							},{
								name : 'quesDate',
								width : '20%',
								align : 'left'
							},{
								name : 'quesType',
								width : '20%',
								align : 'left'
							}
							, {
								name : 'quesFbState',
								width : '20%',
								align : 'left'
							}],
							caption:"数据质量问题列表",
							viewrecords : true,
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							pager : pager_selector,
							toolbar : [ true, "top", tool ],
							altRows : true,
							multiselect : true,
							multiboxonly : true,

							loadComplete : function() {
								var table = this;
								setTimeout(
										function() {
											fwkUtil.updatePagerIcons(table);											
										}, 0);
							}
						});

});


function doSearch() {
	var quesId=$("#quesId").val();
	var quesFbState=$("#quesFbState").val();
	if(quesFbState==""||quesFbState==" "){
		quesFbState=null;
	}
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context+ '/announce.do?method=announce',
	    postData: {"quesId":quesId,"quesFbState":quesFbState},
	    datatype: "json",
	}).trigger("reloadGrid");
}
//删除
function deleteQues(id){
	$.ajax({ 
		type: 'post', 
        url: context+"/announce.do?method=delete", 
        data: {"id":id}, 
        success: function(msg){
	        if(msg==''){
	        	parent.layer.alert("删除成功！"); //弹出成功msg
	        	doSearch();
	        }else{
	        	parent.layer.alert("删除失败"); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
	
}

function announce(quesIds){
	$.ajax({ 
		type: 'post', 
        url: context+"/announce.do?method=announceQues", 
        data: {"quesIds":quesIds}, 
        success: function(msg){
	        if(msg==''){
	        	parent.layer.alert("发布成功！"); //弹出成功msg
	        }else{
	        	parent.layer.alert("发布失败"); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
	
	
}
//弹窗处理
function openWindow(flag,id){
	var title="";
	var connect="";
	//add
	if(flag=='add'){
		connect=context+"/page/sm/quesmgr/announce/add.jsp?idtype="+"add";
		title="添加";
	}
	if(flag=='update'){
		connect=context+"/announce.do?method=turn&id="+id+"&idtype="+"update";
		title="修改";
	}

	if(flag=="detail"){
		connect=context+"/announce.do?method=turn&id="+id+"&flag="+flag;
		title="详情";
	}
	//弹窗
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim',
	    title: title,
	    shadeClose: true,
	    area: ['1000px', '500px'],
	    fix: false, //不固定
	    maxmin: true,
	    content: connect
	});
	return;
}

function openUploadFileWin(){	
	var url= context+"/page/sm/quesmgr/announce/upload.jsp";
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim',
	    title: "导入",
	    shadeClose: true,
	    area: ['600px', '400px'],
	    fix: false, //不固定
	    maxmin: true,
	    content: url
	});
	return;
}



