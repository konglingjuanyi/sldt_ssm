var upSetting = {
	async: { //异步加载数据
		enable: true,
		url: getUrl
	},
	check: { //可以选择，多选框
		enable: true
	},
	data: {
		simpleData: {
			enable: false
		}
	},
	callback: {
		onClick: onClick
	},
};

function getUrl(treeId, treeNode) {
	var params = "";
	if(treeNode == undefined){
		params = "&pId=0";
		params += "&nodeType=root";
	}else{
		params = "&id=" + treeNode.id;
		params += "&pId=" + treeNode.pId;
		params += "&nodeType=" + treeNode.nodeType;
		params += "&classId="+treeNode.classId;
		params += "&instId=" + treeNode.instId;
	}
	return context+app_mb+'/browseMetadata.do?method=metadataTree'+params; 
 }

var rolePrivlgTree; //左侧主题树相关
var myLayout;
var currInstId;
var currNamespace;

$(document).ready(function(){
	myLayout = $('.contentArea').layout({
		west__minSize:300,	
		west__resizable:true
	});
	
	if(roleId == null || roleId == ""){
		return;
	}else{
		// 主题树设置
		$.fn.zTree.init($("#rolePrivlgTree"), upSetting);
		rolePrivlgTree = $.fn.zTree.getZTreeObj("rolePrivlgTree");
	}
	$('#privlg_from input').iCheck({
		   checkboxClass: 'icheckbox_minimal-orange',
		   radioClass: 'iradio_minimal-orange',
		   increaseArea: '10%'
	 });
	//监听全选功能
	$("#privAllCk").on('ifChecked', function(event){
	    allCheckSysView("check");
	    //$("#sysViewCk input").iCheck("check");
	}).on('ifUnchecked', function(event){
		allCheckSysView("unCheck");
		//$("#sysViewCk input").iCheck("unCheck");
	});
});

/**
 * 根据状态进行全选或全不选
 * @param type
 */
function allCheckSysView(type){
	$("#privlg_from").find("input[type=checkbox]").iCheck(type);
}
/**
 * 节点点击事件
 * @param e
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function onClick(e, treeId, treeNode, clickFlag) {
	e.preventDefault();
	$('#metaInfoTitle').html("当前节点的权限【"+treeNode.instName+"】");
	$('#privlg_from').find("input").iCheck('uncheck');
	
	/**
	 * 获取用户元数据权限信息
	 */
	$.ajax({ 
	    type:'get', 
	    url:context+'/role.do?method=dispalyMetaPrivCfg', 
	    dataType:"json",
	    //async:false,
	    data:{
	    	roleId:roleId,
	    	instId:treeNode.instId,
	    	namespace:treeNode.namespace
	    },
	    success:function(data){
	    	if(data){
	    		//alert(data.length);
	    		displayCheckBtn(data,treeNode);
	    	}
	    },
	    error:function(msg){
	    }
	   });
	
}

/**
 * 根据查询的权限，设置按钮
 * @param data
 */
function displayCheckBtn(data,metadata){
	currInstId = metadata.instId;
	currNamespace = metadata.namespace;
	$('#md_view_btn').iCheck('check');
	for(var i=0;i<data.length;i++){
		var actType = data[i].actType;
		//alert(actType);
		if(actType == "all"){
			$('#privlg_from').find("input").iCheck('check');
		}else{
			$('#md_'+actType+'_btn').iCheck('check');
		}
	}
}


function rtCfgModal(){
	if(currInstId){
		//获取选择的按钮权限
		var btnPriv = $('input[name=privlgbtn]:checked');
		if(btnPriv.length<1){
			layer.msg("请至少选择一个按钮权限！", {icon: 2});// 弹出失败msg
			return ;
		}
		var btnPrivStr = "";
		for(var i=0;i<btnPriv.length;i++){
			//alert(btnPriv[i].value);
			btnPrivStr +=","+btnPriv[i].value; 
		}
		//layer.load(2,{shade:[0.8,'#313131']});
		/**
		 * 保存用户元数据权限设置
		 */
		$.ajax({ 
		    type:'get', 
		    url:context+'/role.do?method=setMetaPrivCfg', 
		    dataType:"json",
		    //async:false,
		    data:{
		    	roleId:roleId,
		    	instId:currInstId,
		    	namespace:currNamespace,
		    	btnPrivStr:btnPrivStr.substring(1)
		    },
		    success:function(msg){
		    	if(msg.code == '1'){
					parent.layer.msg(msg.message, {icon: 1}, function(){
					});// 弹出成功msg 
	        	}else{
	        		parent.layer.alert(msg.message, {icon: 2});// 弹出失败msg
	        	}
		    	closWindow();
		    },
		    error:function(msg){
		    	closWindow();
		    }
		   });
	}else{
		layer.msg("尚未选择节点！", {icon: 2});// 弹出失败msg
	}
	
}

/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}