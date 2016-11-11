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
    
	        { text: '非重大问题解决状态变更', iconCls: 'fa fa-exchange icolor', btnCls:'button-gp button-info', handler: function () {
	        		var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	        		var quesIds="";	
	        		if(ids.length>0){
	        			for(var i=0;i<ids.length;i++){
		        			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
		        			var quesType = rowData.quesType;
		        			if("重大问题"!=(quesType) && "解决中"==rowData.quesSolutionState){
		        				quesIds+=(rowData.quesId+",");
		        			}
		        		}
	        			if(quesIds.length>0){
	        				parent.layer.confirm('是否确认变更问题解决状态为已解决！', {
						   	 	btn: ['确认','取消'] //按钮
							}, function(){
								changeState(quesIds);
							});
		        			
		        		}else{
		        			layer.msg('请选择解决中的非重大问题 ！', {shift: 6})
		        		}
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

var tool2 = [
            { text: '方案发布 ', iconCls: 'fa fa-external-link-square icolor', btnCls:'button-gp button-info', handler: function () {
					var ids = $('#grid2-table').jqGrid('getGridParam', 'selarrrow');
					var quesIds="";	
	        		if(ids.length>0){
	        			for(var i=0;i<ids.length;i++){
		        			var rowData = $("#grid2-table").jqGrid('getRowData', ids[i]);	
		        			quesIds+=(rowData.quesId+",");			        			
		        		}
	        			announce(quesIds);		        		
	        		}else{
	        			layer.msg('请选择需要发布记录！', {shift: 6})
	        		}	  
					
	            }
	        },
	        
	        { text: ' 解决状态变更 ', iconCls: 'fa fa-exchange icolor', btnCls:'button-gp button-info', handler: function () {
					var ids = $('#grid2-table').jqGrid('getGridParam', 'selarrrow');
					var quesIds="";	
	        		if(ids.length>0){
	        			for(var i=0;i<ids.length;i++){
		        			var rowData = $("#grid2-table").jqGrid('getRowData', ids[i]);
		        			//if("解决中"==rowData.quesJjState){
		        				quesIds+=(rowData.quesId+",");
		        			//}else{
		        			//	layer.msg('此记录还未发布，请先发布！', {shift: 6})
		        			//}
		        		}
	        			changeState(quesIds);		        		
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

$(document).ready(function() {
	var grid2_selector = "#grid2-table";
	//var width=$('#managerContent1').width()-10;

	jQuery(grid2_selector).jqGrid({
		//url: context + "/dataQualityQues.do?method=dataQualityQuesList",
		datatype: "json",
		mtype: "POST",
		height: "230",
		autowidth: true,
		regional: 'cn',
		colNames: ['质量问题编号','质量问题描述',],
		colModel : [
		            {name:'quesId', width:'20%', align:'left'},
		            {name:'quesDesc', width:'20%', align:'left'},		            		         
		           ],
		viewrecords: true,
	//	rowNum: 10,
	//	rowList: [10,20,30],
		altRows: true,
		toolbar: [ true, "top" ,tool2],
		multiselect: true,
        multiboxonly: true,
        loadComplete: function() {
			var table = this;
			setTimeout(function(){
				fwkUtil.updatePagerIcons(table);
			}, 0);
		
		/*ondblClickRow: function(rowid,iRow,iCol,e){
			var row = $("#grid2-table").jqGrid('getRowData', rowid);
		},
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				fwkUtil.updatePagerIcons(table);
				fwkUtil.removeHorizontalScrollBar("#grid2-table")
			}, 0);*/
		}

	});
	fwkUtil.removeHorizontalScrollBar("#grid2-table");
	
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
/*
 * 移到右边
 */
function sendRight(){
	var id = jQuery("#grid2-table").jqGrid('getDataIDs');
	var maxId=0;
	var attrId = new Array();
	if(id.length!=0){
		//获得当前最大行号（数据编号）  
		maxId = Math.max.apply(Math,id);

		for(var i=0;i<id.length;i++){
			var rowData = $("#grid2-table").jqGrid('getRowData', id[i]);
			var quesId= rowData.quesId;
			attrId[i]=quesId;	
		}
	}
	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	if(ids.length>0){		
		for(var i=0;i<ids.length;i++){
			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
			var quesId= rowData.quesId;
			var quesDesc= rowData.quesDesc;
			var newrowid = maxId+1; 
			if(attrId.indexOf(quesId) == -1){
					var dataRow = {    
							quesId: quesId,
							quesDesc: quesDesc,
					};           					
					if("重大问题"==rowData.quesType){            
						if("未解决"==rowData.quesJjState || "解决方案待审核"==rowData.quesJjState){
							if(rowData.quesSolution!=null && ""!=rowData.quesSolution){
								//将新添加的行插入到第一列  
								$("#grid2-table").jqGrid("addRowData", newrowid, dataRow, "last"); 
								maxId = maxId+1;
							}else{
								parent.layer.msg("请填写解决方案！");
								return;
							}
						}else if("解决中"==rowData.quesJjState ){							
							if(rowData.quesJjResult!=null && ""!=rowData.quesJjResult){
								//将新添加的行插入到第一列  
								$("#grid2-table").jqGrid("addRowData", newrowid, dataRow, "last"); 
								maxId = maxId+1;
							}else{
								parent.layer.msg("请填写解决结果描述！");
								return;
							}
						}							
																	
					}else{
						parent.layer.msg("请选择重大问题！");
					}
			}				
		}
		
	}else{
		parent.layer.msg("请选择记录！");
	}
}
/*
 * 移除
 */
function del(){
	var ids = $('#grid2-table').jqGrid('getGridParam', 'selarrrow');
	if(ids.length != 0){
		for(var i=0;i<ids.length;i++){
			$("#grid2-table").jqGrid("delRowData", parseInt(ids[i]));
		}
	}else{
			parent.layer.msg('请选择记录！', {shift: 6});
	}
	 			
}
/*
 * 添加当前页
 */
function sendCurPage(){
	var id = jQuery("#grid2-table").jqGrid('getDataIDs'); 
	var maxId=0;
	var attrId = new Array();
	if(id.length!=0){
		//获得当前最大行号（数据编号）  
		maxId = Math.max.apply(Math,id); 
		for(var i=0;i<id.length;i++){
			var rowData = $("#grid2-table").jqGrid('getRowData', id[i]);
			var quesId= rowData.quesId;
			attrId[i]=quesId;	
		}
	}
	var ids = $('#grid-table').jqGrid('getDataIDs');
	if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]); 
			var quesId= rowData.quesId;
			var quesDesc= rowData.quesDesc;
			var newrowid = maxId+1; 
			if(attrId.indexOf(quesId) == -1){
				var dataRow = {    
						quesId: quesId,
						quesDesc: quesDesc,
				};      
      
				if("重大问题"==rowData.quesType){
					if("未解决"==rowData.quesJjState || "解决方案待审核"==rowData.quesJjState){						
						if(rowData.quesSolution!=null && ""!=rowData.quesSolution){	
					//将新添加的行插入到第一列  
							$("#grid2-table").jqGrid("addRowData", newrowid, dataRow, "last");
							maxId = maxId+1;
						}else{
							parent.layer.msg("请填写解决方案！");
							return;
						}
					}else if("解决中"==rowData.quesJjState ){
						if(rowData.quesJjResult!=null && ""!=rowData.quesJjResult){
							$("#grid2-table").jqGrid("addRowData", newrowid, dataRow, "last");
							maxId = maxId+1;
						}else{
							parent.layer.msg("请填写解决结果描述！");
							return;
						}
					}
						
					}
				}
			
		}	
	}else{
		parent.layer.msg('请选择记录！', {shift: 6});
	}
}
/*
 * 添加所有
 */
function sendAll(){
	$("#grid2-table").jqGrid('setGridParam', {
	  //  page: 1,
	    url: context + '/dealAction.do?method=showAllByType',
	    postData: {"quesType":"重大问题"},
	    datatype: "json"
	}).trigger("reloadGrid");	
}
/*
 * 移除所有
 */
function delAll(){
	var ids = $('#grid2-table').jqGrid('getDataIDs');
	if(ids.length != 0){
		for(var i=0;i<ids.length;i++){
			$("#grid2-table").jqGrid("delRowData", parseInt(ids[i]));
		}
	}else{
			parent.layer.msg('请选择记录！', {shift: 6});
	}
	 			
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