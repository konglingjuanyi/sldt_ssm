
var tool=[];


 tool.push({ text: ' 新增 ', iconCls: 'fa fa-plus icolor', btnCls:'button-gp button-info', handler: function () {
						openKonCatsDetail("新增","","add");
	            }
	        });

 tool.push({ text: ' 修改 ', iconCls: 'fa fa-pencil icolor', btnCls:'button-gp button-info', handler: function () {
	        	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	        	if(ids.length == 1){
	        		var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
	        		openKonCatsDetail("修改",rowData.kncatId,"update");
	        	}else{
	        		layer.msg('请选择一条记录！', {shift: 6});
	        	}
	        }
	        });
 
 tool.push({ text: ' 删除 ', iconCls: 'fa fa-trash-o icolor', btnCls:'button-gp button-info', handler: function () {
	        	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	        	if(ids.length == 1){
	        		var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
	        		deteleKonCats(rowData.kncatId);
	        	}else{
	        		layer.msg('请选择一条记录！', {shift: 6});
	        	}
	        }
	        });

$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	var width=$('#managerContent1').width()-10;
	var gridHeight = getGridHeight('grid-table');
	jQuery(grid_selector).jqGrid({
		url: context + "/kno.do?method=knoCatsList",//1
		datatype: "json",
		mtype: "POST",
		height: gridHeight,
		width:width,
		colNames: ['分类编号','分类名称', '分类描述'],
		colModel : [
		            {name:'kncatId', width:'20%', align:'left'},
		            {name:'kncatName', width:'20%', align:'left'},
		            {name:'kncatDesp', width:'20%', align:'left'}
		           ],
		/*caption:"分类列表",*/
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
	fwkUtil.removeHorizontalScrollBar("#grid-table")
	
});
function openKonCatsDetail(title,kncatId,option){
	var content="";
		if(title=="新增"){
			///dqm_web/WebRoot/page/dqs/kno/js/knoCatsDetail.js
			content=context + "/page/sm/kno/knoCatsDetail.jsp?option="+option;
		}else if(title=="修改"){
			content=context + "/kno.do?method=knoCatsDetail&kncatId="+kncatId+"&option="+option;//2
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

function doSearch(){
	var kncatName=$("#kncatName").val();
	if(kncatName!=null&&kncatName!=''){
			kncatName="%"+kncatName+"%";	
		}
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/kno.do?method=knoCatsList',
	    postData: {"kncatName":kncatName},
	    datatype: "json"
	}).trigger("reloadGrid");

	
}
function deteleKonCats(kncatId){
	$.ajax({ 
		type: 'post', 
        url: context + '/kno.do?method=deleteKnoCatById', //3
        data: {"kncatId":kncatId}, 
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