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

var tool2 = [
             { text: '数据质量问题发布 ', iconCls: 'fa fa-external-link-square icolor', btnCls:'button-gp button-info', handler: function () {
 					var ids = $('#grid-table1').jqGrid('getGridParam', 'selarrrow');
 					var quesIds="";	
 	        		if(ids.length>0){
 	        			for(var i=0;i<ids.length;i++){
 		        			var rowData = $("#grid-table1").jqGrid('getRowData', ids[i]);
 		        			quesIds+=(rowData.quesId+",");		        			
 		        		}
 	        			announce(quesIds);		        		
 	        		}else{
 	        			layer.msg('请选择需要发布记录！', {shift: 6})
 	        		}	  
 					
 	            }
 	        }];
$(document).ready(
	function() {
		var grid_selector1 = "#grid-table1";
		jQuery(grid_selector1).jqGrid(
		{
			datatype : "json",
			mtype : "POST",
			height: "230",
			autowidth: true,
			regional: 'cn',
			colNames : [ '质量问题编号', '数据质量问题描述'],
			colModel : [ {
				name : 'quesId',
				width : '20%',
				align : 'left'
			}, {
				name : 'quesDesc',
				width : '20%',
				align : 'left'
			}],
			viewrecords : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],

			toolbar : [ true, "top", tool2 ],
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


//选中按钮事件
function opera(flag){
	if('add'==flag)
	{
	//获得左边表格选中数据
		var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
		var llength=ids.length;
		var str="";
	//循环遍历得到值，，判断是否未总打事件，添加到右边
		for(var i=0;i<llength;i++){
			//每一次都是默认为可以添加的，如果有相同的就该为false，下一条的循环就还是默认可以添加
			var flag=true;
			//左边选中的值
			var rowData = $("#grid-table").jqGrid('getRowData', ids[i]);
			//判定是重大事件
			if(rowData.quesType=='重大问题' && rowData.quesFbState!="已发布"){
				//添加到右边的
				var rids = $("#grid-table1").jqGrid("getDataIDs");//获得所有行 的id数组
				if(rids.length==0){
					var rid=1;
					$('#grid-table1').addRowData(rid,{'quesId':rowData.quesId,'quesDesc':rowData.quesDesc},"last");
				}else{
					//判定是否有重复添加
					for(var j=0;j<rids.length;j++){
					//和里面相等就设置为false，不添加
						var rightid=$("#grid-table1").jqGrid('getRowData', rids[j]).quesId;
						if(rowData.quesId==rightid){
//							alert("右边已经存在编号为："+rightid);parent.layer.alert("")
							str+=rightid+","
							flag=false;
						}
							
					}
					
					if(flag){
						var rid=rids[rids.length-1];
						 rid=rid+1;
						 $('#grid-table1').addRowData(rid,{'quesId':rowData.quesId,'quesDesc':rowData.quesDesc},"last");
					}
				}
				
			}else{
				parent.layer.alert("非重大问题或已发布重大问题不能移动！");
			}
			//最后一次循环弹框
			if(i==llength-1&str!=""){
				parent.layer.alert("右边已经存在编号为："+str);
				str="";
			}
		}
		
	}
	/*
	 * 删除右边的选中的行
	 * */
	if('remove'==flag)
	{
		var rids = $('#grid-table1').jqGrid('getGridParam', 'selarrrow');
		var len = rids.length;  
		for(var i = 0;i < len ;i ++) {  
			$("#grid-table1").jqGrid("delRowData", rids[0]);  
		} 
	}
	
	/*
	 * 添加当前页
	 * 
	 * */
	if('addPage'==flag)
	{
		var lids = $("#grid-table").jqGrid("getDataIDs");
		var str="";
		for(var i=0;i<lids.length;i++){
			var flag=true;
			//左边选中的值
			var rowData = $("#grid-table").jqGrid('getRowData', lids[i]);
			if(rowData.quesType=='重大问题' && rowData.quesFbState!="已发布"){
				//添加到右边的
				var rids = $("#grid-table1").jqGrid("getDataIDs");//获得所有行 的id数组
				if(rids.length==0){
					var rid=1;
					$('#grid-table1').addRowData(rid,{'quesId':rowData.quesId,'quesDesc':rowData.quesDesc},"last");	
				}else{
					//判定是否有重复添加
					for(var j=0;j<rids.length;j++){
					//和里面相等就设置为false，不添加
						var rightid=$("#grid-table1").jqGrid('getRowData', rids[j]).quesId;
						if(rowData.quesId==rightid){
							str+=rightid+",";
							flag=false;
						}
						
					}
					if(flag){
						var rid=rids[rids.length-1];
						 rid=rid+1;
						 $('#grid-table1').addRowData(rid,{'quesId':rowData.quesId,'quesDesc':rowData.quesDesc},"last");
					}
				}
			}else{
				parent.layer.alert("非重大问题或已发布重大问题不能移动！");
			}
			
			//最后一次循环弹框
			if(i==lids.length-1&str!=""){
				parent.layer.alert("右边已经存在编号为："+str);
				str="";
			}
		}
		
	}
	
	/*
	 * 
	 * 添加所有
	 * */
	
	if('addAll'==flag)
	{
		//1,从数据库查出所有的重大问题，直接添加到右边
		var url=context+"/announce.do?method=impSearch";
		var str="";
		$.ajax({ 
			type: 'post', 
	        url: url, 
	        async:false,
	        success: function(data){
		        if(data!=null){
		        	//添加到右边
		    		for(var i=0;i<data.length;i++){
		    			//左边选中的值 			
		    			//添加到右边的
		    			var flag=true;
		    			var rids = $("#grid-table1").jqGrid("getDataIDs");//获得所有行 的id数组
		    			if(rids.length==0){
		    				var rid=1;
		    				$('#grid-table1').addRowData(rid,{'quesId':data[i].quesId,'quesDesc':data[i].quesDesc},"last");
		    			}else{
		    				//判定是否有重复添加
							for(var j=0;j<rids.length;j++){
							//和里面相等就设置为false，不添加
								var rowData = $("#grid-table1").jqGrid('getRowData', rids[j]);
								var rightid=data[i].quesId;
								if(rowData.quesId==rightid){
//									相同的就追加
									str+=rightid+",";
									flag=false;
								}
									
							}
							
							if(flag){
								var rid=rids[rids.length-1];
								 rid=rid+1;
								 $('#grid-table1').addRowData(rid,{'quesId':data[i].quesId,'quesDesc':data[i].quesDesc},"last");	 
							}
		    			}	
		    			
						//最后一次循环弹框
						
						if(i==data.length-1&str!=""){
							parent.layer.alert("右边已经存在编号为："+str);
							str="";
						}
		    		}
		        }
	        },
	        error:function(msg){
	        	parent.layer.alert("访问服务器出错！");        
	        }
		});
	}
	
	/**
	 * 
	 * 移除所有
	 * */
	if('removeAll'==flag){
		var rids = $("#grid-table1").jqGrid("getDataIDs");
		var len = rids.length;  
		for(var i = 0;i < len ;i ++) {  
		$("#grid-table1").jqGrid("delRowData", rids[i]);  
		} 
	}
}

