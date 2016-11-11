var bpSetting = {
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
var bpNodes = [
     			{id:"0000", pId:"0000", name:"所有权限", open:true, iconSkin:"pIcon01", isParent:true}
     		  ];

function getUrl(treeId, treeNode) {
	return context + '/btn.do?method=loadBtnPrivlgTree&btnId='+btnId+'&privlgId='+treeNode.id; 
}

var btnPrivlgTree;

$(document).ready(function(){
	if(btnId == null || btnId == ""){
		return;
	}else{
		// 主题树设置
		$.fn.zTree.init($("#btnPrivlgTree"), bpSetting, bpNodes);
		btnPrivlgTree = $.fn.zTree.getZTreeObj("btnPrivlgTree");
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
	btnPrivlgTree.updateNode(treeNode);
}

function ajaxGetNodes(treeNode, reloadType) {
	if (reloadType == "refresh") {
		treeNode.icon = context+"/public/ztree/css/zTreeStyle/img/loading.gif";
		btnPrivlgTree.updateNode(treeNode);
	}
	btnPrivlgTree.reAsyncChildNodes(treeNode, reloadType, true);
}

function rtCfgModal(){
	var nodes = btnPrivlgTree.getCheckedNodes(true);
	
	$.ajax({ 
		type: 'post',
        url: context + '/btn.do?method=cfgBtnPrivlg&btnId='+btnId, 
        data: "nodes=" + JSON.stringify(nodes), // JSON.stringify({nodes:nodes}), 
        success: function(msg){
        	if(msg.code == '1'){
        		alert("保存成功！");// 弹出成功msg
        		//parent.$('#btnPrivlgModal').modal('hide');
        		closWindow();
        	}else{
        		alert("接口请求出现错误:" + msg.message); // 弹出失败msg
        	}
        },
        error: function(msg){
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