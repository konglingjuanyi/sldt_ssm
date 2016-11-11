
var tool=[];

 tool.push({ text: ' 详情 ', iconCls: 'fa fa-list-alt icolor', btnCls:'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openDetail("知识库查询详情",rowData.kncntId,"queryView");
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
	var gridHeight = getGridHeight('grid-table');
	jQuery(grid_selector).jqGrid({
		url: context + "/kno.do?method=knoContentsList",//1
		datatype: "json",
		mtype: "POST",
		height: gridHeight,
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
	fwkUtil.removeHorizontalScrollBar("#grid-table");
});
function openDetail(title,kncntId,option){
	var content="";
			content=context + "/kno.do?method=knoCAndQView&kncntId="+kncntId+"&option="+option;//2
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
function setOption(obj,optionArr){
	var options="<option  value=''>请选择 </option>";
	 for(var i=0;i<optionArr.length;i++){
		 options+='<option  value="'+optionArr[i].kncatId+'">'+optionArr[i].kncatName+'</option>';
	 }
	 $(obj).html(options);
	 
}