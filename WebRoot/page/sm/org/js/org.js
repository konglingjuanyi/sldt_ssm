var orgId = null;
var orgName = null;
var orgTree; // 左侧机构树相关
//控制展开到几级节点rf
var num=1;
var number=0;
//用户权限
var UserOrg=new Array();
var setting = {
	async: { 
		//异步加载数据
		enable:true,
		url: getUrl
	},
    check: {
		enable: false
	},
	callback: {
		onClick: nodeClick,
		onAsyncSuccess: onAsyncSuccess
	},
	view:{
		fontCss:setFontCss
	}
};
function getUrl(treeId, treeNode) {
		return context + '/org.do?method=loadOrgTree&orgId='+treeNode.id; 
}

var uoNodes = [
    			{id:"0000", pId:"0000", name:"所有机构", open:true, iconSkin:"org", isParent:true,state:true}
    		  ];

/*function OnClick(event, treeId, treeNode, clickFlag){
	orgId = treeNode.id;
	orgName = treeNode.name;
	doSearch();
}*/
$(document).ready(function() {
	 $('#questionFrom input').iCheck({
		   checkboxClass: 'icheckbox_minimal-blue',
		   radioClass: 'iradio_minimal-blue',
		   increaseArea: '10%'
	  });
	//查询用户的权限,杭州版本暂时不做机构权限的查询
	/*$.ajax({ 
		type: 'post', 
        url: context + '/org.do?method=selectUserOrgs', 
        data: {},  
        success: function(data){
        	var data=eval("("+data+")");
        	//alert(data.orgCfg[0].orgId);
	       //赋值机构编号
        	for ( var i = 0; i < data.orgCfg.length;i++) {
        		UserOrg[i]=data.orgCfg[i][2];
        		if(i==data.orgCfg.length-1)
        		UserOrg[i+1]=data.orgCfg[i][4];
			}
        },
        error:function(msg){
        	alert("访问服务器出错！");
        	layer.close(loadIndex);
        }
	});*/
	//布局设置
	$('body').layout({
		applyDemoStyles:true,
		west__minSize:400,
		west__resizeable:true
	});
//	var grid_selector = "#grid-table";
//	var pager_selector = "#grid-pager";
	orgTree = $.fn.zTree.init($("#orgTree"), setting, uoNodes);
//	orgTree.reAsyncChildNodes(orgTree.getNodes()[0], "refresh", false);
	var treeObj = $.fn.zTree.getZTreeObj("orgTree"); 
	expandNodes(treeObj.getNodes());
	
//	treeObj.expandAll(true);
//	var tool = [{text: '同步机构', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function (){
//		var msg = "<font color='red'>同步机构信息可能导致用户机构信息有误，是否继续同步？</font>";
//		layer.confirm(msg,{btn:["确认","取消"]},function(indx){
//			syncOrg();
//			layer.close(indx);
//		});
//	}}];
//	
//	var gridOffsetTop = document.getElementById("grid-table").offsetTop;
//	//alert("top:"+top.document.documentElement.clientHeight);
//	var windowHeight = top.document.documentElement.clientHeight;
//	//	var girdHeigth = windowHeight-gridOffsetTop-230;
//	var gridHeigth=getGridHeight("grid-table")-10;
//	//girdHeigth = girdHeigth<320?girdHeigth:305;
//	jQuery(grid_selector).jqGrid({
//		url: context + '/org.do?method=listOrgs',
//		datatype: "json",
//		mtype: "POST",
//		regional: 'cn',
//		height: gridHeigth,
//		shrinkToFit: false, 
//		colNames: ['机构编号', '父级机构编号', '机构简称', '机构全称', '机构地址', '邮政编码', '机构状态', '机构级别', '备注'],
//		colModel: [
//		            {name:'orgId', width:180, align:'center'},
//		            {name:'pOrgId', width:180, align:'center'},
//		            {name:'orgShName', width:200, align:'center'},
//		            {name:'orgName', width:250, align:'center'},
//		            {name:'orgAddr', width:320, align:'center'},
//		            {name:'zipCd', width:180, align:'center'},
//		            {name:'orgStat', width:120, align:'center'},
//		            {name:'orgLevel', width:120, align:'center'},
//		            {name:'meno', width:280, align:'center'},
//		          ], 
//		viewrecords: true,
//		rowNum: 10,
//		rowList: [ 10, 20, 30 ],
//		pager: pager_selector,
//		toolbar: [ true, "top" ,tool],
//		altRows: true,
//		multiselect: true,
//		multiboxonly: true,
//		loadComplete: function() {
//			var table = this;
//			setTimeout(function(){
//				updatePagerIcons(table);
//			}, 0);
//		}
//	});
//	
//	//适应窗体宽度
	//jQuery(grid_selector).setGridWidth(jQuery(window).width());
});

//调整分页图标
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
	var orgId = $("#orgId").val();
	var orgName = $("#orgName").val();
	if(orgId == null || orgName == null){
		return ;
	}
	$("#grid-table").jqGrid('setGridParam', {
	    page: 1,
	    url: context + '/org.do?method=listOrgs',
	    postData: {'orgId':orgId, 'orgName':orgName},
	    datatype: "json"
	}).trigger("reloadGrid");
}
function TB(){
	var msg = "<font color='red'>同步机构信息可能导致用户机构信息有误，是否继续同步？</font>";
	parent.layer.confirm(msg,{btn:["确认","取消"]},function(indx){
		syncOrg();
		layer.close(indx);
	});
}
function syncOrg(){
	var loadIdx = parent.layer.load(1);
	$.ajax({
		url:context+"/org.do?method=syncTsmOrgInfo",
		type:'POST',
		success:function(msg){
			if(msg.code == '1'){
				parent.layer.alert("同步成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		parent.layer.alert(msg.message); //弹出失败msg
        	}
			parent.layer.close(loadIdx);
		}
	});
	}
//节点点击事件
function nodeClick(event, treeId, treeNode, clickFlag){
	//获取节点编号
	orgId = treeNode.id;
	//判断用户是否有该节点的权限 杭州银行版本暂时屏蔽
	/*for ( var i = 0; i < UserOrg.length; i++) {
		if(orgId==UserOrg[i]){
			number=1;
		}
	}*/
	orgIdFor = orgId;
	number = 1;
	if(number==1){
	//通过节点编号查询数据
	$.ajax({
		url:context+"/org.do?method=nodeOrgs",
		type:'POST',
		data:"&nodeId="+orgId,
		success:function(data){
			var data=eval("("+data+")");
			var tsm = data.tsm;  //获取后台list
			var tsm1=data.tsm1;
			//向文本框中添加数据
				$("#orgId").val(tsm.orgId);
				$("#porgId").val(tsm1.orgName);
				$("#orgName").val(tsm.orgName);
				$("#zpCd").val(tsm.zipCd);
				$("#orgStat").val(tsm.orgStat);
				$("#orgLevel").val(tsm.orgLevel);
				$("#meno").val(tsm.meno);
				number=0;
		}
	});
	
	$.ajax({
		url:context+"/isDapSts.do?method=queryStatus",
		type:'POST',
		data:"&nodeId="+orgId,
		success:function(data){
			//向文本框中添加数据
				if(data == 0){
					$("input[id=add-square-1]").iCheck('uncheck');
					$("input[id=add-square-2]").iCheck('check');
				}else{
					$("input[id=add-square-2]").iCheck('uncheck');
					$("input[id=add-square-1]").iCheck('check');
				}
		}
	});
	}else{
		parent.layer.alert("您没有该机构的查看权限");
	}
} 

//保存按钮
function addSubmit(){
	var status = $('input:radio:checked').val();
	$.ajax({
		url:context+"/isDapSts.do?method=updateStatus",
		type:'POST',
		data:{"status":status, "nodeId":orgIdFor},
		success:function(msg){
            if(msg.code=='1'){
                   //返回问答列表页面
                   layer.alert(msg.message, 1);        //弹出成功msg
            }else{
                   layer.alert(msg.message,3);        //弹出失败msg
            }
         },
         error:function(msg){
              layer.alert("访问服务器出错",3);        
         }
	});
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
	if(num>0){
		expandNodes(treeNode.children);
		num--;
		}
}
function expandNodes(nodes) {
	if (!nodes) return;
	var zTree = $.fn.zTree.getZTreeObj("orgTree");
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(nodes[i].children);
		} 
	}
}
//设置节点颜色
function setFontCss(treeId, treeNode) {
	//无权限的设置灰色
	var org=0;
	/**
	 * 杭州银行版本暂时不做机构权限查看的控制
	 */
	/*for ( var i = 0; i < UserOrg.length; i++) {
		if(treeNode.id==UserOrg[i]){
			org=1;
		}
	}*/
	org = 1;
	return org == 1 ? {} : {color:"gray"};
};



