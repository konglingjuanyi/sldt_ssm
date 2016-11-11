var tool=[];
 tool.push({ text: ' 详情 ', iconCls: 'fa fa-list-alt icolor', btnCls:'button-gp button-info', handler: function () {
					var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
					if(ids.length == 1){
						var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
						openDetail("详情",rowData.quesId);
					}else{
						layer.msg('请选择一条记录！', {shift: 6});
					}
	            }
	        });



$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url: context + "/announce.do?method=announce",//1
		datatype: "json",
		mtype: "POST",
		height: "230",
		autowidth: true,
		regional: 'cn',
		colNames: ['质量问题编号','数据质量问题描述', '影响范围', '涉及系统', '涉及部门',
		           '问题提出人','问题提出日期', '问题分类', '问题发布状态','问题解决状态 ','方案发布状态','问题解决方案'],
		colModel : [
		            {name:'quesId', width:'20%', align:'left'},
		            {name:'quesDesc', width:'20%', align:'left'},
		            {name:'influence', width:'20%', align:'left'}, 
		            {name:'aboutSys',  width:'20%', align:'left'},
		            {name:'aboutDept',  width:'15%', align:'left'},
		            {name:'quesPerson', width:'15%', align:'left'},
		            {name:'quesDate',  width:'20%', align:'left'},
		            {name:'quesType',  width:'15%', align:'left'},
		            {name:'quesFbState',  width:'20%', align:'left'},
		             {name:'quesJjState',  width:'20%', align:'left'},
		             {name:'quesSolutionState',  width:'20%', align:'left'},
		            {name:'quesSolution',  width:'20%', align:'left'}
		           ],
		caption:"质量问题查询表",
		viewrecords: true,
		rowNum: 10,
		rowList: [10,20,30],
		pager: pager_selector,
		altRows: true,
		toolbar: [ true, "top" ,tool],
		multiselect: true,
        multiboxonly: true,

		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				fwkUtil.updatePagerIcons(table);
				
			}, 0);
		}

	});

});
function openDetail(title,quesId){
	var content="";
		content=context + "/announce.do?method=turnDetail&id="+quesId;
	parent.layer.open({
	    type: 2,
	    skin: 'layui-layer-rim',
	    title: title,
	    shadeClose: true,
	    area: ['900px', '500px'],
	    fix: false, //不固定
	    maxmin: true,
	    content: content
	});
	return;
}

function doSearch(){
	var quesId=$("#quesId").val();
	var quesFbState=$("[name='quesFbState']").val();
	var quesSolutionState=$("#quesSolutionState").val();
	var quesJjState=$("#quesJjState").val();

	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/announce.do?method=announce',
	    postData: {"quesId":quesId,"quesFbState":quesFbState,"quesSolutionState":quesSolutionState,"quesJjState":quesJjState},
	    datatype: "json"
	}).trigger("reloadGrid");

	
}
