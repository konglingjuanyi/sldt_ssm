var orgId = null;
var orgName = null;
var orgTree; // 左侧机构树相关

var setting = {
	async: { //异步加载数据
		enable: true,
		url: getUrl
	},
	check: {
		enable: false
	},
	callback: {
		onClick: OnClick
	}
};

function getUrl(treeId, treeNode) {
	return context + '/org.do?method=loadOrgTree&orgId='+treeNode.id; 
}

var uoNodes = [
    			{id:"0000", pId:"0000", name:"所有机构", open:true, iconSkin:"org", isParent:true}
    		  ];

function OnClick(event, treeId, treeNode, clickFlag){
	orgId = treeNode.id;
	orgName = treeNode.name;
	doSearch();
}

$(document).ready(function() {
	//初始化布局div的大小
	var bodyH = $(window).height()-50;
	$(".layout_div").css("height",bodyH+"px");
	//布局设置
	myLayout = $('.layout_div').layout({
		west__minSize:210,	
		west__resizable:true
	});
	orgTree = $.fn.zTree.init($("#orgTree"), setting, uoNodes);
	var gridHeight = $(".layout_div").height()-150;
	gridHeight = gridHeight < 150 ? 150:gridHeight;
	jQuery("#grid-table").jqGrid({
		//direction: "rtl",
		url: context + "/user.do?method=listUser",
		datatype: "json",
		regional: 'cn',
		mtype: "POST",
		height: gridHeight,
		colNames: ['登录名', '机构编号', '用户名','所属机构', '用户状态', '启用日期', '截至日期', '登录密码', '联系电话', '邮箱地址', '同步时间', '最后维护时间'],
		colModel : [
		            {name:'userId', index:'userId', width:'20%', align:'center'},
		            {name:'orgId', index:'orgId', width:'20%', align:'center'},
		            {name:'name', index:'name', width:'20%', align:'center'},
		            {name:'orgNum', index:'orgNum', width:'20%', align:'center'},
		            {name:'userStat', index:'userStat', width:'20%', align:'center', formatter:function(cellValue){
		            	var value = "";
		            	if("0" == cellValue){
		            		value = "已启用";
		            	}else if("1" == cellValue){
		            		value = "未启用";
		            	}else if("2" == cellValue){
		            		value = "作废";
		            	}else if("5" == cellValue){
		            		value = "暂时停用";
		            	}else if("6" == cellValue){
		            		value = "锁定";
		            	}
		            	return value;
		            }},
		            {name:'startDt', index:'startDt', width:'20%', align:'center', hidden:true},
		            {name:'endDt', index:'endDt', width:'20%', align:'center', hidden:true},
		            {name:'logonPwd', index:'logonPwd', width:'20%', align:'center', hidden:true},
		            {name:'contTel', index:'contTel', width:'30%', align:'center'},
		            {name:'contEmail', index:'contEmail', width:'40%', align:'center'},
		            {name:'syncDt', index:'syncDt', width:'20%', align:'center', hidden:true},
		            {name:'lastUpdateDt', index:'lastUpdateDt', width:'20%', align:'center', hidden:true}
		           ],
		viewrecords: true,
		rowNum: 10,
		rowList: [10,20,30],
		pager: "#grid-pager",
		altRows: true,
		multiselect: true,
        multiboxonly: true,
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		autowidth: true
	});
	
	$('#org_tree').height($('.span10').height()-15);
});
	
// 调整分页图标
function updatePagerIcons(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'fa fa-angle-double-left',
		'ui-icon-seek-prev' : 'fa fa-angle-left',
		'ui-icon-seek-next' : 'fa fa-angle-right',
		'ui-icon-seek-end' : 'fa fa-angle-double-right'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	});
}


function doSearch(){
	var userId = $("#searchUserId").val();
	var name = $("#searchUserNm").val();
	if(userId == null || name == null){
		return ;
	}
	name = encodeURI(name);
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/user.do?method=listUser',
	    postData: {'userId':userId, 'name':name, 'orgId':orgId},
	    datatype: "json"
	}).trigger("reloadGrid");
}


function delegatUser(){
	//获取选择的用户
	var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
	if(ids.length > 1) {
		layer.alert("只能指派一个用户！");
		return;
	}
	if(ids.length < 1) {
		layer.alert("请选择一个要指派的用户！");
		return;
	}
	var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
	 var userId = rowData['userId'];
	 var userName = rowData['name'];
	 $.ajax({
			url: context+'/activiti/processTaskInst.do',
			type: 'post',
			data: { method:'delegateUser',
					taskInstId:taskInstId,
					actTaskInstId:actTaskInstId,
					userId : userId,
					userName : userName
				},
			dataType: 'json',
			async : false,
			success: function(data){
				if(data.success == '1'){
					layer.msg("指定处理人成功！",{icon:1});
					if(flag=="approve") {//审批界面中指派人员
						//刷新审批界面
						top.mainframe.contentWindow.mainArea_approve.doSearch();
						setTimeout(function(){parent.layer.closeAll();},500);
					}else{
						parent.search();
						setTimeout(function(){closWindow();},500);
					}
				}else{
					layer.msg("指定处理人失败！",{icon:2});
				}
				
			},
			error: function(){
				layer.msg("服务器异常！",{icon:2});
			}
		});
	 
}