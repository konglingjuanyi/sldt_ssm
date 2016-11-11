var tool = [
            { text: ' 更新解决方案或结果描述 ', iconCls: 'fa fa-pencil icolor', btnCls:'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openDetail("更新解决方案或结果描述",rowData.quesId);
					}else{
						layer.msg('请选择一条记录！', {shift: 6});
					}
	            }
	        },
	        
	        { text: ' 上传 ', iconCls: 'fa fa-upload icolor', btnCls:'button-gp button-info', handler: function () {			
					openUploadFileWin();					
	        	}
	        },
	        {
				text : '模板下载',
				iconCls : 'fa fa-download icolor',btnCls : 'button-gp button-info',handler : function() {
					var url= context+"/excelTemplate/sm_ques_solution.xls";
					window.open (url);
				
				}
			},
	        { text: ' 详情 ', iconCls: 'fa fa-list-alt icolor', btnCls:'button-gp button-info', handler: function () {
	        		
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openDetail("detail",rowData.quesId);
					}else{
						layer.msg('请选择一条记录！', {shift: 6});
					}
        		}
	        },
    
	        { text: '问题解决状态变更', iconCls: 'fa fa-exchange icolor', btnCls:'button-gp button-info', handler: function () {
	        		var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	        		var quesIds="";	
	        		if(ids.length>0){
	        			for(var i=0;i<ids.length;i++){
		        			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
		        			var quesType = rowData.quesType;
		        			
		        				quesIds+=(rowData.quesId+",");
		        			
		        		}
	        			changeState(quesIds);
	        		
	        		}else{
	        			layer.msg('请选择需要变更记录！', {shift: 6})
	        		}	        			    
	        	}
	        },
	        { text: ' 问题无效 ', iconCls: 'fa fa-circle-o icolor', btnCls:'button-gp button-info', handler: function () {
	        		var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	        		var quesIds="";
	        		if(ids.length>0){
	        			for(var i=0;i<ids.length;i++){
		        			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
		        			var quesFbState = rowData.quesFbState;
		        			if("待审核"!=(quesFbState) && "审核未通过"!=rowData.quesFbState){
		        				quesIds+=(rowData.quesId+",");
		        			}
		        		}
	        			if(quesIds.length>0){
		        			nullity(quesIds);	        			
		        		}else{
		        			layer.msg('审核问题不可更改！', {shift: 6});
		        		}
	        		}else{
	        			layer.msg('请选择需要变更记录！', {shift: 6})
	        		}	 
	        			        		
	        	}
	        },
     ];



$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var width=$('#managerContent1').width()-10;

	jQuery(grid_selector).jqGrid({
		url: context + "/dealAction.do?method=dealList",
		datatype: "json",
		mtype: "POST",
		height: "230",
		autowidth: true,
		regional: 'cn',
		colNames: ['质量问题编号','问题分类', '问题解决方案', '解决方案状态', '问题解决状态',
		           '问题解决结果概述','问题解决时间','问题描述'],
		colModel : [
		            {name:'quesId', width:'20%', align:'left'},
		            {name:'quesType', width:'20%', align:'left'},
		            {name:'quesSolution', width:'20%', align:'left'}, 
		            {name:'quesSolutionState',  width:'20%', align:'left'},
		            {name:'quesJjState', width:'20%', align:'left'},
		            {name:'quesJjResult', width:'20%', align:'left'},		            
		            {name:'quesJjDate',  width:'20%', align:'left'},
		            {name:'quesDesc',  width:'20%', align:'left',hidden:true},
		           ],
		caption:"数据质量问题解决方案列表",
		viewrecords: true,
		rowNum: 10,
		rowList: [10,20,30],
		pager: pager_selector,
		altRows: true,
		toolbar: [ true, "top" ,tool],
		multiselect: true,
        multiboxonly: true,
		ondblClickRow: function(rowid,iRow,iCol,e){
			var row = $("#grid-table").jqGrid('getRowData', rowid);
		},
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				fwkUtil.updatePagerIcons(table);
			}, 0);
		}

	});
	fwkUtil.removeHorizontalScrollBar("#grid-table");
});

/*
 * 打开弹出窗口，修改信息
 */
function openDetail(title,quesId){
	var content	=context + "/dealAction.do?method=dealDetailInit&quesId="+quesId+"&title="+title;
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim',
	    title: title,
	    shadeClose: true,
	    area: ['1000px', '500px'],
	    fix: false, //不固定
	    maxmin: true,
	    content: content
	});
	return;
}
/*
 * 非重大问题状态变更
 */
function changeState(quesIds){
	$.ajax({ 
		type: 'post', 
        url: context + '/dealAction.do?method=changeState', 
        data: {quesIds:quesIds}, 
        success: function(msg){
        	if(msg==''){
	        	parent.layer.msg("更新成功！"); //弹出成功msg
	        	doSearch();
	        }else{
	        	parent.layer.alert(msg); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}

/*
 * 发布
 */
function announce(quesIds){
	$.ajax({ 
		type: 'post', 
        url: context + '/dealAction.do?method=changeSoluState', 
        data: {quesIds:quesIds}, 
        success: function(msg){
        	if(msg==''){
	        	parent.layer.msg("发布成功！"); //弹出成功msg
	        	doSearch();
	        }else{
	        	parent.layer.alert(msg); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}
/*
 * 问题无效
 */
function nullity(quesIds){
	$.ajax({ 
		type: 'post', 
        url: context + '/dealAction.do?method=nullity', 
        data:{quesIds:quesIds}, 
        success: function(msg){
        	if(msg==''){
	        	parent.layer.msg("更新成功！"); //弹出成功msg
	        	doSearch();
	        }else{
	        	parent.layer.alert(msg); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
}
/*
 * 条件查询
 */
function doSearch(){
	var quesId=$("#quesId").val();
	var quesJjState=$("#quesJjState").val();
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/dealAction.do?method=dealList',
	    postData: {"quesId":"%"+quesId+"%",
	    	       "quesJjState":quesJjState},
	    datatype: "json"
	}).trigger("reloadGrid");	
}

//打开文件上传窗口
function openUploadFileWin(){	
	var url= context+"/page/sm/quesmgr/deal/upload.jsp";
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