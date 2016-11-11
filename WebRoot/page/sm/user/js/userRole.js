var urSetting = {
	async: { //异步加载数据
		enable: true,
		url: getUrl
	},
	check: { //可以选择，多选框
		enable: true
	},
	view: {
		showIcon:false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		//onRightClick: OnRightClick
		beforeExpand: beforeExpand,
		onAsyncSuccess: onAsyncSuccess
	}
};
var urNodes = [
     			{id:"0000", pId:"0000", name:"所有角色", open:true, iconSkin:"pIcon01", isParent:true,nocheck:true}
     		  ];

function getUrl(treeId, treeNode) {
	return context + '/user.do?method=loadUserRoleTree&userId='+userId+'&roleId='+treeNode.id; 
}

var userRoleTree; //左侧主题树相关

$(document).ready(function(){
	if(userId == null || userId == ""){
		return;
	}else{
		// 主题树设置
		$.fn.zTree.init($("#userRoleTree"), urSetting, urNodes);
		userRoleTree = $.fn.zTree.getZTreeObj("userRoleTree");
	}
});

function beforeExpand(treeId, treeNode) {
	if (!treeNode.isAjaxing) {
		ajaxGetNodes(treeNode, "refresh");
		return true;
	} else {
		alert("zTree 正在下载数据中，请稍后展开节点。。。");
		return false;
	}
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
	if (!msg || msg.length == 0) {
		return;
	}
	treeNode.icon = "";
	userRoleTree.updateNode(treeNode);
}

function ajaxGetNodes(treeNode, reloadType) {
	if (reloadType == "refresh") {
		treeNode.icon = context+"/public/ztree/css/zTreeStyle/img/loading.gif";
		userRoleTree.updateNode(treeNode);
	}
	userRoleTree.reAsyncChildNodes(treeNode, reloadType, true);
}

function rtCfgModal(method){
	var nodes = userRoleTree.getCheckedNodes(true);
	var roleArr = new Array();
	for(var i = 0;i<nodes.length;i++) {
		roleArr.push(nodes[i].id);
	}
	var loadIndex = layer.load(2,{shade:[0.8,'#313131']});
	$.ajax({ 
		type: 'post',
        url: context + '/user.do?method=cfgUserRole&userId='+userId, 
        data: "nodes=" + JSON.stringify(roleArr), // JSON.stringify({nodes:nodes}), 
        success: function(msg){
        	layer.close(loadIndex);
        	if(msg.code == '1'){
        		parent.layer.alert("保存成功！");// 弹出成功msg
        		closWindow();
        	}else{
        		parent.layer.alert("接口请求出现错误:" + msg.message); // 弹出失败msg
        	}
        },
        error: function(msg){
        	layer.close(loadIndex);
        	alert("访问服务器出错!");        
        }
	});
}
/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}