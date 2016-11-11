
var tool=[];
var setting = {
	view: {  
        dblClickExpand: true//双击展开  
        },
	async: { //异步加载数据
		enable: true,
		url: context + '/kno.do?method=loadTree'//获得知识库分类的节点
	},
	check: { //可以选择，多选框
		enable: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
        onClick:zTreeOnClick,
		beforeExpand: beforeExpand,
		onAsyncSuccess: onAsyncSuccess
	}
};

var zNodes = [
    			{id:"0000", pId:"0000", name:"知识库内容树", open:true , isParent:true,iconSkin:"org"}
    		  ];
var zTree; 
	$(document).ready(function(){
		$.fn.zTree.init($("#tree"), setting, zNodes);
		zTree=$.fn.zTree.getZTreeObj("tree");
		//zTree.expandAll(true);
	});
	
	//定义一个变量记住我所点击的分类是哪一个
	var kncatName=null;
	var kncntId=null;
	function zTreeOnClick(event, treeId, treeNode) {
		//alert("shushu");
		//alert(treeId);
		var kncatId=treeNode.id;
		kncntId=treeNode.id;
		 kncatName=treeNode.name;//alert(id)
		 
		//查询这个id的分类，然后开始从写表格
		 doSearch();

			
	}
	
		function beforeExpand(treeId, treeNode) {
		if (!treeNode.isAjaxing) {
			ajaxGetNodes(treeNode, "refresh");
			return true;
		} else {
			//alert("zTree 正在下载数据中，请稍后展开节点。。。");
			return false;
		}
	}
	
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		if (!msg || msg.length == 0) {
			return;
		}
		treeNode.icon = "";
		zTree.updateNode(treeNode);
	}
	
	function ajaxGetNodes(treeNode, reloadType) {
		if (reloadType == "refresh") {
			treeNode.icon = context+"/public/ztree/css/zTreeStyle/img/loading.gif";
			zTree.updateNode(treeNode);
		}
		zTree.reAsyncChildNodes(treeNode, reloadType, true);
	}


	//刷新树和表格
	function reflash(){
		//$.fn.zTree.init($("#tree"), setting, zNodes);
		doReflashNode()
		mainReflash();
	}
	
	//初始化工具栏
	var tool = [];
	 tool.push({ text: ' 详情 ', iconCls: 'fa fa-list-alt icolor', btnCls:'button-gp button-info', handler: function () {
						var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
						if(ids.length == 1){
							var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
							openDetail("知识库内容详情",rowData.kncntId,"contentView");
						}else{
							layer.msg('请选择一条记录！', {shift: 6});
						}
		            }
		        });

	 tool.push({ text: ' 新增 ', iconCls: 'fa fa-plus icolor', btnCls:'button-gp button-info', handler: function () {
		        		openDetail("新增",kncntId,"add");
		        }
		        });

	 tool.push({ text: ' 修改 ', iconCls: 'fa fa-pencil icolor', btnCls:'button-gp button-info', handler: function () {
		        	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
		        	if(ids.length == 1){
		        		var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
		        		openDetail("修改",rowData.kncntId,"update");
		        	}else{
		        		layer.msg('请选择一条记录！', {shift: 6});
		        	}
		        }
		        });
	 tool.push({ text: ' 删除 ', iconCls: 'fa fa-trash-o icolor', btnCls:'button-gp button-info', handler: function () {
		        	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
		        	if(ids.length == 1){
		        		var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
		        		deleteKnoContent(rowData.kncntId);
		        	}else{
		        		layer.msg('请选择一条记录！', {shift: 6});
		        	}
		        }
		        });


	//初始化表格
	$(document).ready(function() {
		$.ajax({ 
				type: 'post', 
		        url: context + '/kno.do?method=getKnoCatOption', 
		        data: "", 
		        success: function(msg){
			        if(msg.length<1){
			        	alert("获取下拉框失败");
			        }else{
			        	setOption($("#kncatId"),msg);
			        }
		        },
		        error:function(msg){
		        	alert("访问服务器出错！");        
		        }
			});
		
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";
		//	var width=$('#content').width()-10;
		//	var gridHeight = getGridHeight('grid-table')-5;
			jQuery(grid_selector).jqGrid({
				url: context + "/kno.do?method=knoContentsList",//1
				datatype: "json",
				mtype: "POST",
				height: "230",
				autowidth: true,
				regional: 'cn',
				colNames: ['内容编号','内容标题', '解决方案', '知识库分类', '关键字'],
				colModel : [
				            {name:'kncntId', width:'20%', align:'left'},
				            {name:'kncntTitle', width:'20%', align:'left'},
				            {name:'planTitle', width:'20%', align:'left'}, 
				            {name:'kncatName',  width:'20%', align:'left'},
				            {name:'kncntKey',  width:'20%', align:'left'}
				           ],
				viewrecords: true,
				rowNum: 10,
				rowList: [10,20,30],
				pager: pager_selector,
				altRows: true,
				toolbar: [ true, "top" ,tool],
				multiselect: true,
		        multiboxonly: true,
				/*ondblClickRow: function(rowid,iRow,iCol,e){
					var row = $("#grid-table").jqGrid('getRowData', rowid);
				},*/
				loadComplete: function() {
					var table = this;
					setTimeout(function(){
						fwkUtil.updatePagerIcons(table);
						
					}, 0);
				}
//				,
//				autowidth: true
			});
		//	fwkUtil.removeHorizontalScrollBar("#grid-table");
		});
	
	
	function setOption(obj,optionArr){
		var options="<option  value=''>请选择 </option>";
		 for(var i=0;i<optionArr.length;i++){
			 options+='<option  value="'+optionArr[i].kncatId+'">'+optionArr[i].kncatName+'</option>';
		 }
		 $(obj).html(options);
		 
	}
	
	//添加分类的内容
	function openKonCatsDetail(title,option){
		var content="";
			if(title=="新增"){
				///dqm_web/WebRoot/page/dqs/kno/js/knoCatsDetail.js
				content=context + "/page/sm/kno/knoCatsDetail.jsp?option="+option;
			}else if(title=="修改"){
				if(kncntId!="0000"){
					content=context + "/kno.do?method=knoCatsDetail&kncatId="+kncntId+"&option="+option;//2
				}else{
						layer.msg('这是总节点不支持该操作！', {shift: 6});
						return;
					}
			}
		
		parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim',
		    title: title,
		    shadeClose: true,
		    area: ['550px', '300px'],
		    fix: false, //不固定
		    maxmin: true,
		    content: content
		});
		return;
	}

	
	//添加内容的弹窗
	function openDetail(title,kncntId,option){
		var content="";
		if(option=="add"){
			content=context + "/page/sm/kno/knoContentsDetail.jsp?option="+option+"&kncntId="+kncntId;//2
		}else if(option=="update"){
			content=context + "/kno.do?method=knoContentDetail&kncntId="+kncntId+"&option="+option;//2
		}else if(option=="contentView"){
			content=context + "/kno.do?method=knoCAndQView&kncntId="+kncntId+"&option="+option;//2
		}else{
		}
		parent.layer.open({
		    type: 2,
		    skin: 'layui-layer-rim',
		    title: title,
		    shadeClose: true,
		    area: ['800px', '560px'],
		    fix: false, //不固定
		    maxmin: true,
		    content: content
		});
		return;
	}
	
	//删除分类
	function deteleKonCats(){
		if(kncntId!="0000"){
			parent.layer.confirm('是否确认删除选中数据？', {
						    btn: ['确认','取消'] //按钮
						}, function(){
							$.ajax({ 
								type: 'post', 
						        url: context + '/kno.do?method=deleteKnoCatById', //3
						        data: {"kncatId":kncntId}, 
						        success: function(msg){
							        if(msg==''){
							        	parent.layer.msg("删除成功！"); //弹出成功msg
							        	//刷新树
						        	var treeObj = $.fn.zTree.getZTreeObj("tree");
						        	//获得父类节点
						        	var node = treeObj.getNodeByTId("tree_1");
						        	//刷新父类节点
						        	ajaxGetNodes(node, "refresh");
							        	doSearch();
							        }else{
							        	parent.layer.alert(msg); //弹出失败msg
							        }
						        },
						        error:function(msg){
						        	parent.layer.alert("访问服务器出错！");        
						        }
							});
							
						}, function(){
						    
						});
					
		}else{
			layer.msg('这是总节点不支持该操作！', {shift: 6});
			return;
		}
		
	}
	
	//删除内容
	function deleteKnoContent(kncntId){
		parent.layer.confirm('是否确认删除选中数据？', {
						    btn: ['确认','取消'] //按钮
						}, function(){
							$.ajax({ 
							type: 'post', 
					        url: context + '/kno.do?method=deleteKnoContentById', //3
					        data: {"kncntId":kncntId}, 
					        success: function(msg){
						        if(msg==''){
						        	parent.layer.msg("删除成功！"); //弹出成功msg
						        	doSearch();
						        }else{
						        	parent.layer.alert(msg); //弹出失败msg
						        }
					        },
					        error:function(msg){
					        	parent.layer.alert("访问服务器出错！");        
					        }
						});
							
						}, function(){
						    
						});
		
	}
	
	
	function doSearch(){
		var kncntTitle=$("#kncntTitle").val();
		if(kncntTitle!=null&&kncntTitle!=''){
				kncntTitle="%"+kncntTitle+"%";	
			}
		//var kncatId=$("[name='kncatId']").val();
		//var kncatId=$("[name='kncatName']").val();;
		var kncatId=kncntId;
		if(kncatId=="0000"){
			kncatId="";
		}
		var kncntKey=$("#kncntKey").val();
		if(kncntKey!=null&&kncntKey!=''){
				kncntKey="%"+kncntKey+"%";	
			}
		var allField=$("#allField").val();
		if(allField!=null&&allField!=''){
			allField="%"+allField+"%";	
		}
		$("#grid-table").jqGrid('setGridParam', {
		    page: 1,
		    url: context + '/kno.do?method=knoContentsList',
		    postData: {"kncntTitle":kncntTitle,"kncatId":kncatId,"kncntKey":kncntKey,"allField":allField},
		    datatype: "json"
		}).trigger("reloadGrid");
		
	}
	
	function mainReflash(){
	var kncntTitle=$("#kncntTitle").val();
		if(kncntTitle!=null&&kncntTitle!=''){
				kncntTitle="%"+kncntTitle+"%";	
			}
		//var kncatId=$("[name='kncatId']").val();
		//var kncatId=$("[name='kncatName']").val();;
		var kncntKey=$("#kncntKey").val();
		if(kncntKey!=null&&kncntKey!=''){
				kncntKey="%"+kncntKey+"%";	
			}
		$("#grid-table").jqGrid('setGridParam', {
		    page: 1,
		    url: context + '/kno.do?method=knoContentsList',
		    postData: {"kncntTitle":kncntTitle,"kncatId":"","kncntKey":kncntKey},
		    datatype: "json"
		}).trigger("reloadGrid");
	}
	
	function doReflashNode(){
			//刷新树
        	var treeObj = $.fn.zTree.getZTreeObj("tree");
        	//获得父类节点
        	var node = treeObj.getNodeByTId("tree_1");
        	//刷新父类节点
        	ajaxGetNodes(node, "refresh");
	}
