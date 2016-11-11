var upSetting = {
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
		//beforeExpand: beforeExpand,
		onAsyncSuccess: onAsyncSuccess
	}
};
var upNodes = [
     			{id:"0000", pId:"0000", name:"所有权限", open:true, iconSkin:"pIcon01", isParent:true}
     		  ];

function getUrl(treeId, treeNode) {
	var privlgId = "0000";
	if(treeNode != undefined){
		privlgId = treeNode.id;
	}
	return context + '/user.do?method=loadUserPrivlgTree&userId='+userId+'&privlgId='+privlgId; 
}

var userPrivlgTree; //左侧主题树相关

$(document).ready(function(){
	if(userId == null || userId == ""){
		return;
	}else{
		// 主题树设置
//		$.fn.zTree.init($("#userPrivlgTree"), upSetting, upNodes);
		$.fn.zTree.init($("#userPrivlgTree"), upSetting);
		userPrivlgTree = $.fn.zTree.getZTreeObj("userPrivlgTree");
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
	if(treeNode == undefined){
		return;
	}
	if (!msg || msg.length == 0) {
		return;
	}
	treeNode.icon = "";
	userPrivlgTree.updateNode(treeNode);
}

function ajaxGetNodes(treeNode, reloadType) {
	if (reloadType == "refresh") {
		treeNode.icon = context+"/public/ztree/css/zTreeStyle/img/loading.gif";
		userPrivlgTree.updateNode(treeNode);
	}
	userPrivlgTree.reAsyncChildNodes(treeNode, reloadType, true);
}

function rtCfgModal(method){
	var checkedNodes = userPrivlgTree.getCheckedNodes(true);
	//权限数组
	var privArr = new Array();
	var loadIndex = layer.load(2,{shade:[0.8,'#313131']});
	var nodes = [];
	for(var i=0;i<checkedNodes.length;i++){
		//权限信息
		var priv = {
				privId:"",//权限编号
				hasChildren:"no",//是否包含子节点，只有选择的节点为权限状态切有子节点才能为"yes"
		};
		var privId = checkedNodes[i].id;
		var isParent = checkedNodes[i].isParent;
		var checkStatus= checkedNodes[i].getCheckStatus();
		priv.privId = privId;
		if(isParent){
			if(checkStatus.half) {
				priv.hasChildren = "no";
			}else {
				priv.hasChildren = "yes";
			}
		}else{
			priv.hasChildren = "no";
		}
		privArr.push(priv);
	}
	$.ajax({
		type: "post",
		url: context + "/user.do",
		data: {"method":"configUserPriv","userId":userId,"privIds":JSON.stringify(privArr)},
		success: function(msg){
			layer.close(loadIndex);
			if(msg.code == '1'){
				layer.msg(msg.message, {icon: 1}, function(){
					closWindow();
				});// 弹出成功msg 
        	}else{
        		layer.msg(msg.message, {icon: 2});// 弹出失败msg
        	}
		},
		error: function(){
			layer.close(loadIndex);
			alert("访问服务器出错！");
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