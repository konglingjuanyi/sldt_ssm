var tool = [{
			text : ' 审核通过',
			iconCls : 'fa fa-plus icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				//获得选中的id
				var quesIds="";	
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if(ids.length>0){
					for(var i=0;i<ids.length;i++){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						if(rowData.quesSolutionState=="待审核"){
							quesIds+=(rowData.quesId+",");							
						}
					}
					if(quesIds.length>0){
						check(quesIds,"yes");						
					}else{						
						layer.msg('只能选择待审核的问题！', {shift: 6});						
					}
					//打开修改窗口
				}else{
					layer.msg('请选择要审核的数据！', {shift: 6});
				}
			
			}
		},{
			text : ' 审核不通过',
			iconCls : 'fa fa-pencil icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				var quesIds="";	
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if(ids.length>0){
					for(var i=0;i<ids.length;i++){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						if(rowData.quesSolutionState=="待审核"){
							quesIds+=(rowData.quesId+",");							
						}else{
							layer.msg('只能选择待审核的问题！', {shift: 6});
						}
					}
					check(quesIds,"no");
					//打开修改窗口
				}
				else{
					layer.msg('请选择要审核的数据！', {shift: 6});
				}
			
			}
		},{
			text : ' 刷新',
			iconCls : 'fa fa-refresh icolor',
			btnCls : 'button-gp button-add',
			handler : function() {
				doSearch();
			}
				
		}
		];


$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
//	var width=$('#managerContent1').width()-10;

	jQuery(grid_selector).jqGrid({
		url: context + "/dealAction.do?method=dealList",
		postData: {"quesSolutionState":"待审核",
    		"quesType":"重大问题"},
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

function doSearch() {
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context+ '/dealAction.do?method=dealList',
	    postData: {"quesSolutionState":"待审核",
    		"quesType":"重大问题"},
	    datatype: "json",
		mtype : "POST",
	}).trigger("reloadGrid");
}


function check(quesIds,flag){
	$.ajax({ 
		type: 'post', 
        url: context+"/dealAction.do?method=check", 
        data: {"quesIds":quesIds,
        		"flag":flag},      		
        success: function(msg){
	        if(msg==''){
	        	parent.layer.alert("操作成功！"); //弹出成功msg
	        	//doSearch();
	        }else{
	        	parent.layer.alert("操作失败"); //弹出失败msg
	        }
        },
        error:function(msg){
        	parent.layer.alert("访问服务器出错！");        
        }
	});
	
}
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}