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
		     			{id:"0000", pId:"0000", name:"角色/用户", open:true, iconSkin:"pIcon01", isParent:true,nocheck:true}
		     		  ];
		
		var userRoleTree; //左侧主题树相关
		
		$.fn.zTree.init($("#userRoleTree"), urSetting, urNodes);
		userRoleTree = $.fn.zTree.getZTreeObj("userRoleTree");
	

$(document).ready(function(){
	/*初始化部署的流程下拉选项*/
	initActivitiDefinition();
	/*选择部署的流程初始化表单数据*/
	initDefinitionInfo();
	/*模糊匹配用户*/
	//fuzzyQueryUser();
	fuzzyQueryUserRole();
	/*将用户配置信息展示到表格*/
	//addConfigToTb();
	/*保存数据*/
	saveConfigDatas();
	
	
	//初始化更新数据
	if(procId != 'null'){
		initUpdateData();
	}
	
	
});


/**
 * 修改模块
 */
function initUpdateData(){
	$.ajax({
		url : context + '/activiti/processDefinition.do?procId='+procId,
		type : 'post',
		data : {
			method : 'queryDefinition'
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			$("#developedModel").val(data.actProcDefId);//流程定义id
			$("#definitionId").val(data.actProcDefId);//流程定义id
			$("#deploymentId").val(data.deploymentId);//部署id
			$("#definitionName").val(data.actProcDefName);//流程定义名
			$("#definitionKey").val(data.actProcDefKey);//流程定义key
			$("#version").val(data.version);//版本
			$("#procName").val(data.procName);//流程配置名称
			
			//======
			var tasks = data.tasks;
			for(var i=0;i<tasks.length;i++){
				var json = tasks[i];
				$("#taskNodes").append("<option value='"+json.actId+"'>"+json.actName+"</option>");
				rendProcConfigTb(tasks);
//				var taskOpers = json.taskOpers;
//				initUpdateTab(taskOpers,json.actId);
				//rendProcConfigTb(tasks.taskOpers);
			}
			
			

			
		},
		error : function() {
			layer.msg("服务器出现问题！", {
				icon : 2
			});
		}
	});
	
}

function initUpdateTab(taskOpers,actId){
	var $configTrs = $("#procConfigTbody").find("tr");
		$configTrs.each(function(){
			for(var i=0;i<taskOpers.length;i++){
				var taskOper = taskOpers[i];
				var operTypeShow = {"0":"竞签","1":"会签"};
				var operType = taskOper.operType;
				var userRoleName = taskOper.groupName;
				var userRoleId =  taskOper.groupId;
				if($(this).attr("actId") == actId){
					$(this).find("td:nth-child(2)").attr("type",operType);
					$(this).find("td:nth-child(2)").text(operTypeShow[operType]);
					//var operation = "<input type='button' style='margin-left:100px;' class='btn btn-success btn-small editor' onclick='edituserRoleName(this,'"+userRoleName+"');' value=编辑>";
					var operation = "<button class='btn btn-default btn-mini' style='margin-left:45%;margin-bottom:2px;' onclick='removeUser(this);' type='button' ><i class='icon-trash icolor'></i> 删除</button>";
					$(this).find("td:nth-child(3)").append("<div style=''><span class='block_span' userRoleId='"+userRoleId+"'>"+userRoleName+"</span>"+operation+"</div>");
				}
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

/*初始化部署的流程下拉选项*/
function initActivitiDefinition() {
	$.ajax({
		url : context + '/activiti/processDefinition.do',
		type : 'post',
		data : {
			method : 'queryDefinitionList'
		},
		dataType : 'json',
		async : false,
		success : function(data) {

			for (var i = 0; i < data.length; i++) {
				var json = data[i];
				$("#developedModel").append(
						"<option value='" + json.id + "'>" + json.name
								+ "</option>");
			}

		},
		error : function() {
			layer.mag("初始化下拉选项失败！", {
				icon : 2
			});
		}
	});
}

/**
 * 选择部署的流程初始化表单数据
 */
function initDefinitionInfo(){
	$("#developedModel").on('change',function(){
		var definitionId = $(this).val();
		if(definitionId !== undefined&&definitionId!=""){
			fillDefinitionInfo(definitionId);
		}else{
			$("#add-definition-form")[0].reset();
		}
	});
}

/**
 * 流程信息初始化
 * @param id
 */
function fillDefinitionInfo(id){
	
	$.ajax({
		url: context+'/activiti/processDefinition.do',
		type: 'post',
		data: {method:'getDefinitionById',definitionId:id},
		dataType: 'json',
		async : false,
		success: function(data){
			showDefinition(data);
			initTaskNodes(data.id);
		},
		error: function(){
			layer.msg("数据初始化失败！",{icon:2});
		}
	});
}

function showDefinition(data){
	$("#definitionId").val(data.id);
	$("#deploymentId").val(data.deploymentId);
	$("#definitionName").val(data.name);
	$("#definitionKey").val(data.key);
	$("#procName").val(data.name);
	$("#version").val(data.version);
}

/**
 * 初始化可配置的任务节点
 * @param id
 */
function initTaskNodes(id){
	$.ajax({
		url: context+'/activiti/processDefinition.do',
		type: 'post',
		data: {method:'initTaskNodes','definitionId':id},
		dataType: 'json',
		async : false,
		success: function(data){
			$("#taskNodes").empty();
			for(var i=0;i<data.length;i++){
				var json = data[i];
				$("#taskNodes").append("<option value='"+json.actId+"'>"+json.actName+"</option>");
			}
			//初始化流程配置表信息
			rendProcConfigTb(data);
			
		},
		error: function(){
			layer.msg("初始化下拉选项失败！",{icon:2});
		}
	});
}

/**
 * 初始化流程配置表信息
 * @param data
 */
function rendProcConfigTb(data){
	$("#procConfigTbody").empty();
	for(var i=0;i<data.length;i++){
		var json = data[i];
		var tbdata = "<tr align='center' actId='"+json.actId+"'>";
		tbdata += "<td>"+json.actName+"</td>";
		tbdata += "<td type='0'>竞签</td>";
		
		var taskOpers = json.taskOpers;
		tbdata += "<td colspan='2'>";
		if(taskOpers != undefined){
			for(var j=0;j<=taskOpers.length-1;j++){
				var taskOper = taskOpers[j];
				var operation = "<button class='btn btn-default btn-mini' style='margin-left:15%;margin-bottom:2px;' onclick='removeUser(this);' type='button' ><i class='icon-trash icolor'></i> 删除</button>";
				tbdata += "<div style=''><span class='block_span' userRoleId='"+taskOper.groupId+"'>"+taskOper.groupName+"</span>"+operation+"</div>";
			}
		}
		tbdata += "</td>";
		$("#procConfigTbody").append(tbdata);
	}
}

function fuzzyQueryUserRole(){
	/**
	$("#userRoleName").autocomplete({ 
		scroll:true,
        source:function(request,response){  
            $.ajax({  
                type:"POST",  
                url:context+'/activiti/processDefinition.do',
                dataType : "json",  
                cache : false,  
                async : false,  
                data : {  
                    "serVal" : encodeURI($("#userRoleName").val()),
                     method:'fuzzyQueryUserRole'
                },  
                success : function(data) {
                    response($.map(data,function(item){  
                        return {  
                            label:item.roleName,//下拉框显示值  
                            value:item.roleName,//选中后，填充到下拉框的值  
                            id:item.roleId//选中后，填充到id里面的值  
                        }  
                    }));  
                }  
            });  
        },  
        delay: 500,//延迟500ms便于输入  
        select : function(event, ui) {  
            $("#userRoleId").val(ui.item.id);  
        }  
    });  **/
	$("#userRoleName").focus(function(){
		$("#userRoleName").val("");
		if(isEmpty($("#developedModel").val())){
			layer.msg("请选择部署的模型！",{icon:2});
			return false;
		}
		openUserSelectWin();
	});
	
}

/**
 * 
 */
var  index_;
function openUserSelectWin(){
	
	
	
//	var url= context+"/page/activiti/process/definition/userSelect.jsp";
	   /* parent.openWin(url,"delegateUser","350px","22%");*/
	  index_ =  layer.open({
			type:1,
			title:"用户角色选择",
			shadeClose: false,
		    shade: 0.1,
		    area: ['50%', '50%'],
		    content: $("#ztree_01")
		});
}

function addUserRole(){
		var nodes = userRoleTree.getCheckedNodes(true);
		var apdNames = "";
		for(var i=0;i<nodes.length;i++){
			var node = nodes[i];
			var operTypeShow = {"0":"竞签","1":"会签"};
			var actId = $("#taskNodes").val();
			var operType = $("#operType").val();
			var userRoleName = node.name;
			apdNames += node.name+"、";
			var userRoleId = node.id;
			var $configTrs = $("#procConfigTbody").find("tr");
			if(isEmpty(userRoleName)){
				return false;
			}
			$configTrs.each(function(){
				if($(this).attr("actId") == actId){
					$(this).find("td:nth-child(2)").attr("type",operType);
					$(this).find("td:nth-child(2)").text(operTypeShow[operType]);
					/*重复用户不用append*/
					var tduserRoleName = $(this).find("td:nth-child(3)").find("span").text();
					//var operation = "<input type='button' style='margin-left:100px;' class='btn btn-success btn-small editor' onclick='edituserRoleName(this,'"+userRoleName+"');' value=编辑>";
					var operation = "<button class='btn btn-default btn-mini' style='margin-left:15%;margin-bottom:2px;' onclick='removeUser(this);' type='button' ><i class='icon-trash icolor'></i> 删除</button>";
					if(tduserRoleName.indexOf(userRoleName) == -1){
						$(this).find("td:nth-child(3)").append("<div><span class='block_span' userRoleId='"+userRoleId+"'>"+userRoleName+"</span>"+operation+"</div>");
					}
					
				}
			});
		}
		
		$("#userRoleName").val(apdNames.substring(0, apdNames.length-1));
		
		layer.close(index_);
}

function closChileWindow(){
	layer.close(index_);
}

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



function getUrl(treeId, treeNode) {
	return context + '/activiti/processDefinition.do?method=loadUserRoleTree&roleId='+treeNode.id; 
}


function fuzzyQueryUser(){
	$("#userName").autocomplete({ 
		scroll:true,
        source:function(request,response){  
            $.ajax({  
                type:"POST",  
                url:context+'/activiti/processDefinition.do',
                dataType : "json",  
                cache : false,  
                async : false,  
                data : {  
                    "userName" : encodeURI($("#userName").val()),
                     method:'fuzzyQueryUser'
                },  
                success : function(data) {
                    response($.map(data,function(item){  
                        return {  
                            label:item.name,//下拉框显示值  
                            value:item.name,//选中后，填充到下拉框的值  
                            id:item.userId//选中后，填充到id里面的值  
                        }  
                    }));  
                }  
            });  
        },  
        delay: 500,//延迟500ms便于输入  
        select : function(event, ui) {  
            $("#userId").val(ui.item.id);  
        }  
    });  
}

//addProcessConfig
//function addConfigToTb(){
//	$("#addProcessConfig").bind("click",function(){
//		var operTypeShow = {"0":"竞签","1":"会签"};
//		var actId = $("#taskNodes").val();
//		var operType = $("#operType").val();
//		var userRoleName = $("#userRoleName").val();
//		var userRoleId = $("#userRoleId").val();
//		var $configTrs = $("#procConfigTbody").find("tr");
//		if(isEmpty(userRoleName)){
//			return false;
//		}
//		$configTrs.each(function(){
//			if($(this).attr("actId") == actId){
//				$(this).find("td:nth-child(2)").attr("type",operType);
//				$(this).addClass("configed");
//				$(this).find("td:nth-child(2)").text(operTypeShow[operType]);
//				/*重复用户不用append*/
//				var tduserRoleName = $(this).find("td:nth-child(3)").find("span").text();
//				//var operation = "<input type='button' style='margin-left:100px;' class='btn btn-success btn-small editor' onclick='edituserRoleName(this,'"+userRoleName+"');' value=编辑>";
//				var operation = "<button class='btn btn-default btn-mini' style='margin-left:55%;margin-bottom:2px;' onclick='removeUser(this);' type='button' ><i class='icon-trash icolor'></i> 删除</button>";
//				if(tduserRoleName.indexOf(userRoleName) == -1){
//					$(this).find("td:nth-child(3)").html("<div style=''><span userRoleId='"+userRoleId+"'>"+userRoleName+"</span>"+operation+"</div>");
//				}
//			}
//		});
//		
//	});
//}

function removeUser(dom){
	$(dom).parent().remove();
//	$(dom).parent().parent().parent().removeClass("configed");
}
/**
 * 保存配置信息
 */
function saveConfigDatas(){
	$("#saveModelButton").click(function(){
		//流程定义信息
		var definitionId =  $("#definitionId").val();//流程定义id
		var deploymentId = $("#deploymentId").val();//部署id
		var definitionName = $("#definitionName").val();//流程定义名
		var definitionKey = $("#definitionKey").val();//流程定义key
		var version = $("#version").val();//版本
		var procName = $("#procName").val();//流程配置名称
		if(isEmpty(procName)){
			layer.msg("请输入流程配置名称！",{icon:2});
			return false;
		}
		var definitionConfig = {definitionId:definitionId,deploymentId:deploymentId,definitionName:definitionName,definitionKey:definitionKey,version:version,procName:procName};
		
		/*获取任务节点用户配置信息*/
		var tasksNodesConfig = [];
		var tasksTr = $("#procConfigTbody").find("tr");
		if(tasksTr.length == 0){
			layer.msg("请进行相关配置后再保存！",{icon:2});
			return false;
		}
		tasksTr.each(function(){
			var actId = $(this).attr("actId");//任务节点id
			var actName = $(this).find("td:nth-child(1)").text();//任务节点名
			var operType = $(this).find("td:nth-child(2)").attr("type");//操作类型
			//操作用户----------
			var $usersSpan = $(this).find("td:nth-child(3)").find("span");
			var taskUsersRole = [];
			$usersSpan.each(function(){
				var userRoleId = $(this).attr("userRoleId");//用户id
				var userRoleName = $(this).text();//用户名
				taskUsersRole.push({userRoleId:userRoleId,userRoleName:userRoleName});
			});
			//-----------------
			//var taskUsersStr = JSON.stringify(taskUsers);
			tasksNodesConfig.push({actId:actId,actName:actName,operType:operType,taskUsersRole:taskUsersRole});
		});
		
		//转成json字符串(已对部分版本浏览器添加json2.js支持)
		var deifinitionConfigStr = JSON.stringify(definitionConfig);
		var tasksNodesConfigStr = JSON.stringify(tasksNodesConfig);
		
		//后台存储
		$.ajax({
			url: context+'/activiti/processDefinition.do',
			type: 'post',
			data: {	method : 'saveDefinitionConfigs',
					deifinitionConfigStr : deifinitionConfigStr,
					tasksNodesConfigStr : tasksNodesConfigStr
				  },
			dataType: 'json',
			async : false,
			success: function(data){
				if(data.success == true){
					layer.msg("保存成功！",{icon:1});
					closWindow();
					window.parent.reloadGridData();
					
				}else{
					layer.msg("保存失败！",{icon:2});
				}
			},
			error: function(){
				layer.msg("存储异常！",{icon:2});
			}
		});
		
	});
}


function isEmpty(str){
	return str == null || str === undefined || str == "";
}

