
var tool=[];

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
	        		openDetail("新增","","add");
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
	var width=$('#managerContent1').width()-10;

	jQuery(grid_selector).jqGrid({
		url: context + "/kno.do?method=knoContentsList",//1
		datatype: "json",
		mtype: "POST",
		height: "auto",
		width:width,
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
		ondblClickRow: function(rowid,iRow,iCol,e){
			var row = $("#grid-table").jqGrid('getRowData', rowid);
		},
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				fwkUtil.updatePagerIcons(table);
				
			}, 0);
		}
//		,
//		autowidth: true
	});
	fwkUtil.removeHorizontalScrollBar("#grid-table")
});
function openDetail(title,kncntId,option){
	var content="";
	if(option=="add"){
		content=context + "/page/sm/kno/knoContentsDetail.jsp?option="+option;//2
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

function doSearch(){
	var kncntTitle=$("#kncntTitle").val();
	if(kncntTitle!=null&&kncntTitle!=''){
			kncntTitle="%"+kncntTitle+"%";	
		}
	var kncatId=$("[name='kncatId']").val();
	//var kncatId=$("[name='kncatName']").val();
	var kncntKey=$("#kncntKey").val();
	if(kncntKey!=null&&kncntKey!=''){
			kncntKey="%"+kncntKey+"%";	
		}
	
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/kno.do?method=knoContentsList',
	    postData: {"kncntTitle":kncntTitle,"kncatId":kncatId,"kncntKey":kncntKey},
	    datatype: "json"
	}).trigger("reloadGrid");
}
function deleteKnoContent(kncntId){
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
}


function setOption(obj,optionArr){
	var options="<option  value=''>请选择 </option>";
	 for(var i=0;i<optionArr.length;i++){
		 options+='<option  value="'+optionArr[i].kncatId+'">'+optionArr[i].kncatName+'</option>';
	 }
	 $(obj).html(options);
}
