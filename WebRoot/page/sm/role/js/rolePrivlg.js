var rpSetting = {
		async: {  //异步加载数据
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
var rpNodes =[
     			{ id:"0000", pId:"0000", name:"所有权限", open:true, iconSkin:"pIcon01",isParent:true,nocheck:true}
     		];

function getUrl(treeId, treeNode) {
	return context+'/role.do?method=loadRolePrivlgTree&roleId='+roleId+'&privlgId='+treeNode.id; 
}

var rolePrivlgTree;//左侧主题树相关

$(document).ready(function(){
	if(roleId==null || roleId==""){
		return;
	}else{
		//主题树设置
		$.fn.zTree.init($("#rolePrivlgTree"), rpSetting, rpNodes);
		rolePrivlgTree =  $.fn.zTree.getZTreeObj("rolePrivlgTree");
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
	rolePrivlgTree.updateNode(treeNode);
	//rolePrivlgTree.selectNode(treeNode.children[0]);
}

function ajaxGetNodes(treeNode, reloadType) {
	if (reloadType == "refresh") {
		treeNode.icon = context+"/public/ztree/css/zTreeStyle/img/loading.gif";
		rolePrivlgTree.updateNode(treeNode);
	}
	rolePrivlgTree.reAsyncChildNodes(treeNode, reloadType, true);
}

function rtCfgModal(){
	var nodes = rolePrivlgTree.getCheckedNodes(true);
	var privs = new Array();
	for(var i = 0,len = nodes.length;i<len;i++) {
		var priv = {
				privId:"",//权限编号
				hasChildren:""//是否包含子节点，若为父节点且节点为全勾选状态，则设置为yes;若是父节点，但节点为半勾选状态也设置为no
		};
		//是否父节点
		var isParent = nodes[i].isParent;
		//勾选状态
		var checkStatus= nodes[i].getCheckStatus();
		if(isParent) {
			priv.privId = nodes[i].id;
			if(checkStatus.half) {
				priv.hasChildren = "no";
			}else {
				priv.hasChildren = "yes";
			}
			privs.push(priv);
		}else {
			priv.privId = nodes[i].id;
			priv.hasChildren = "no";
			privs.push(priv);
		}
	}
	var loadIndex =  layer.load(2,{shade:[0.8,'#313131']});
	$.ajax({ 
        type:'post',
        /*dataType:"json",      
        contentType:"application/json",*/    
        url:context+'/role.do?method=cfgRolePrivlg&roleId='+roleId, 
        data:"nodes="+JSON.stringify(privs),//JSON.stringify({nodes:nodes}), 
        success:function(msg){
        	layer.close(loadIndex);
           if(msg.code=='1'){
        	   parent.layer.alert("保存成功！");        //弹出成功msg
               //刷新表格数据
               //doSearch();
               closWindow();
           }else{
        	   layer.close(loadIndex);
               alert("接口请求出现错误:"+msg.message);  //弹出失败msg
           }
        },
        error:function(msg){
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