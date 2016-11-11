var reportMetaTree; //报表元数据树
var searchClsTree; //搜索导航类型树
var setting = {
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId"
		}
	},
	async: {
		enable: true, //异步加载
        url: root + "/menu.do?method=dropDownTree",
        autoParam: ["id"] //自动提交的一个参数
	},
	callback: {
		onClick: onClick
	}
};

function onClick(event, treeId, treeNode, clickFlag){
	//设置实际的菜单编号
	$("#pMenuId").val(treeNode.id);
	$("#if_pMenuId").val(treeNode.name);
	hideMenu();
}

var repSetting = {
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
			onClick: repOnClick
		},
};

function repOnClick(event, treeId, treeNode, clickFlag){
	//alert(treeNode.classId);
	if(treeNode.classId=='Rep_info'){//如果点击了报表
		//$("#menuParam2").val(treeNode.id);
	    $("#if_menuParam2").val(treeNode.id);
		hideReportMetaTree();
	}
}

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
	return root+app_mb+'/browseMetadata.do?method=metadataTree'+params; 
 }

var searchClsSetting = {
		async: { //异步加载数据
			enable: false,
			url: getSearchClsUrl
		},
		data: {
			simpleData: {
				enable: false
			}
		},
		callback: {
			onClick: searchClsOnClick,
			beforeExpand: zTreeBeforeExpand
		},
};

var rootNodes = [{id:"report", pId:"0000", nodeType:'report',nodeLevel:1,rootType:'report', name:"业务数据、报表", open:false, iconSkin:"menu02",children:[], isParent:true},
                 {id:"dsm", pId:"0000", nodeType:'dsm',nodeLevel:1,rootType:'dsm',name:"数据标准", open:false, iconSkin:"menu02", children:[],isParent:true},
                 {id:"dqsRule", pId:"0000", nodeType:'dqsRule',nodeLevel:1,rootType:'dqsRule',name:"数据质量", open:true, iconSkin:"menu01", isParent:false},
                 {id:"mds", pId:"0000",nodeType:'mds', nodeLevel:1,rootType:'mds',name:"数据字典", open:false, iconSkin:"menu02", isParent:true}
                ];

function getSearchClsUrl(treeId, treeNode) {
    return  "";
}

function searchClsOnClick(event, treeId, treeNode, clickFlag){
	//alert(treeId);
	var params3 = getPamars3ByNode(treeNode,"");
	$('#if_menuParam3').val(params3);
	hideSearchClsTree();
}

function getPamars3ByNode(treeNode,params3){
	var params = "";
	if(treeNode){
		if(treeNode.nodeLevel==1){
			params = "metaTye:'"+treeNode.id+"'";
		}else{
			params = "'instId':'"+treeNode.id+"',classId:'"+treeNode.nodeType+"'";
		}
		if(params3 && params3!=""){
			params +=",cType:{"+params3+"}";
		}
		var pNode = treeNode.getParentNode();
		if(pNode){
			params = getPamars3ByNode(pNode,params);
			
		}else{
			params = "{"+params+"}";
		}
	}
	return params;
}

function zTreeBeforeExpand(treeId, treeNode){
	//alert(treeId);
	if(treeNode == undefined){
		//alert(treeNode);
	}else{
		if(treeNode.children && treeNode.children.length>0){
			
		}else{
			var cNodes = getCNodes(treeNode.id,treeNode.nodeType,treeNode.nodeLevel,treeNode.rootType);
			searchClsTree.addNodes(treeNode, cNodes);
			treeNode.expandNode(treeNode, true, false, false);
		}
	}
	return true;
}

/**
 * 根据不同的节点ID，生成子节点，模拟分类导航的层级加载
 * @param pId
 * @returns
 */
function getCNodes(pId,NodeType,nodeLevel,rootType){
	var cNodes = [];
	if(pId=='mds'){
		 cNodes = [
		              {id:"DDSystem", pId:pId, nodeType:'DDSystem', nodeLevel:nodeLevel+1,rootType:'mds',name:"系统", open:false, iconSkin:"menu01", isParent:false},
		              {id:"DDSchema", pId:pId, nodeType:'DDSchema',nodeLevel:nodeLevel+1,rootType:'mds',name:"模式", open:false, iconSkin:"menu01",isParent:false},
		              {id:"DDTable", pId:pId, nodeType:'DDTable',nodeLevel:nodeLevel+1,rootType:'mds',name:"表级", open:false, iconSkin:"menu01", isParent:false},
		              {id:"DDColumn", pId:pId,nodeType:'DDColumn',nodeLevel:nodeLevel+1,rootType:'mds', name:"表字段", open:false, iconSkin:"menu01", isParent:false}
		     ];
	}else if(pId=='report'){
		$.ajax({
		    type: "get",
		    url:root+"/../mds/rest/api/metadata/getRepAndScheam",
		    data: {},
		    async: false,
		    dataType:'jsonp',
		    processData: false, 
		    success: function(data){
		    	if(data.length>0){
		    		for(var i=0;i<data.length;i++){
		    			var node = {id:data[i].inst_id, pId:pId, nodeType:data[i].CLASS_ID,nodeLevel:nodeLevel+1,rootType:'report',name:data[i].INST_NAME, open:false, iconSkin:"menu02", isParent:true};
		    			cNodes.push(node);
		    		}
		    	}
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown) {
		    	 alert("XMLHttpRequest.status = "+XMLHttpRequest.status+
			    		   ";XMLHttpRequest.readyState = "+XMLHttpRequest.readyState+
			    		   ";textStatus = "+textStatus);
		      }
		});
	}else if(pId=='dsm'){
		$.ajax({
		    type: "get",
		    url:root+"/../dsm/rest/api/dsmrest/getDataStdRestLeft",
		    data: {},
		    async: false,
		    dataType:'jsonp',
		    processData: false, 
		    success: function(data){
		    	if(data.length>0){
		    		for(var i=0;i<data.length;i++){
		    			var node = {id:data[i].INST_ID, pId:pId, nodeType:data[i].CLASS_ID,nodeLevel:nodeLevel+1,rootType:'dsm',name:data[i].INST_NAME, open:false, iconSkin:"menu02", isParent:true};
		    			cNodes.push(node);
		    		}
		    		node = {id:"AmCommonCode", pId:pId, nodeType:"AmCommonCode", name:"公共代码", open:false, iconSkin:"menu02", isParent:true};
		    		cNodes.push(node);
		    	}
		    },
		    error:function(XMLHttpRequest, textStatus, errorThrown) {
		    	 alert("XMLHttpRequest.status = "+XMLHttpRequest.status+
			    		   ";XMLHttpRequest.readyState = "+XMLHttpRequest.readyState+
			    		   ";textStatus = "+textStatus);
		      }
		});
	}else if(nodeLevel>1){
		if(rootType=='report'){
			$.ajax({
			    type: "get",
			    url:root+"/../mds/rest/api/metadata/getReportTypeByPId?pId="+pId,
			    async: false,
			    dataType:'jsonp',
			    processData: false, 
			    success: function(data){
			    	if(data.length>0){
			    		for(var i=0;i<data.length;i++){
			    			for(var key in data[i]){
			    				for(var j = 0 ; j < data[i][key].length;j++){
			    					if(data[i][key][j] && data[i][key][j].CLASS_ID!='Rep_info'){
						    			var node = {id:data[i][key][j].INST_ID, pId:pId, nodeType:data[i][key][j].CLASS_ID,nodeLevel:nodeLevel+1,rootType:'report',name:data[i][key][j].INST_NAME, open:false, iconSkin:"menu02", isParent:true};
						    			cNodes.push(node);
						    	   }
			    				}
			    			}
			    		
			    		}
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown) {
			        layer.alert("XMLHttpRequest.status = "+XMLHttpRequest.status+
				    		   ";XMLHttpRequest.readyState = "+XMLHttpRequest.readyState+
				    		   ";textStatus = "+textStatus);
			      }
			});
		}else if(rootType=='dsm'){
			$.ajax({
			    type: "get",
			    url:root+"/../dsm/rest/api/dsmrest/getDataStdSub?params="+pId,
			    async: false,
			    dataType:'jsonp',
			    processData: false, 
			    success: function(data){
			    	if(data.length>0){
			    		for(var i=0;i<data.length;i++){
			    			for(var key in data[i]){
			    				for(var j = 0 ; j < data[i][key].length;j++){
			    					if(data[i][key][j] && nodeLevel<5){
						    			var node = {id:data[i][key][j].INST_ID, pId:pId, nodeType:data[i][key][j].CLASS_ID,nodeLevel:nodeLevel+1,rootType:'dsm',name:data[i][key][j].INST_NAME, open:false, iconSkin:"menu02", isParent:true};
						    			cNodes.push(node);
						    	   }
			    				}
			    			}
			    		
			    		}
			    	}
			    },
			    error:function(XMLHttpRequest, textStatus, errorThrown) {
			        layer.alert("XMLHttpRequest.status = "+XMLHttpRequest.status+
				    		   ";XMLHttpRequest.readyState = "+XMLHttpRequest.readyState+
				    		   ";textStatus = "+textStatus);
			      }
			});
		}
		
	}
	return cNodes;
}

$(document).ready(function(){
	  $('input').iCheck({
		    checkboxClass: 'icheckbox_minimal',
		    radioClass: 'iradio_minimal-blue',
		    increaseArea: '20%' // optional
	  });
	  
	  $('input').on('ifChecked', function(event){
		  //alert(this.id);
		  if(this.id=='belong_1'){
			  $('#menuUrlInfo').show();
			  
			  $('#if_menuParam2').val('');
			  $('#if_menuParam3').val('');
			  $('#ReportInfo').hide();
			  $('#metaClassInfo').hide();
			  $("#reportMetaTree").fadeOut("fast");
			  $("#searchClsTree").fadeOut("fast");
		  }else if(this.id=='belong_2'){
			  $('#ReportInfo').show();
			  
			  $('#if_menuUrl').val('');
			  $('#if_menuParam3').val('');
			  $('#menuUrlInfo').hide();			  
			  $('#metaClassInfo').hide();
			  $("#searchClsTree").fadeOut("fast");
		  }else if(this.id=='belong_3'){
              $('#metaClassInfo').show();
			  $('#if_menuUrl').val('');
			  $('#if_menuParam2').val('');
			  $('#menuUrlInfo').hide();			  
			  $('#ReportInfo').hide();
			  $("#reportMetaTree").fadeOut("fast");
		  }
		  
	  });
	  
	  $.fn.zTree.init($("#reportMetaTree"), repSetting);
	  reportMetaTree = $.fn.zTree.getZTreeObj("reportMetaTree");
	  
	  $.fn.zTree.init($("#searchClsTree"), searchClsSetting,rootNodes);
	  searchClsTree = $.fn.zTree.getZTreeObj("searchClsTree");
	  
	//下拉列表
	$("#system-table").jqGrid({
		url:root+"/system.do?method=listSystem",
		datatype:"json",
		regional: 'cn',
		height:150,
		width:260,
		colNames:["编号","子系统名称"],
		colModel:[
		          {name:'appId',index:'dsId',align : "center"},
		          {name:'appName',index:'appName',align : "center"}
		          ],
		 viewrecords : true,
		/* rowNum:10,
		 rowList:[10,20,30],*/
		multiboxonly:true,
		/* pager : "#templet-pager",*/
		altRows: true,
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		onSelectRow:function(id,status){     
			  var selData = $("#system-table").getRowData(id);
			  $('#appName').val(selData.appName);
			  $('#appId').attr('value',selData.appId);
			  hideSys();
		},
	});
	validateForm();
	if(type=="addMenu"){
		$('#if_menuId').attr('value',"");
		$('#if_menuName').attr('value',"");
		$('#if_menuDesc').attr('value',""); 
		$('#if_menuLevel').attr('value',"");
		$('#if_menuUrl').attr('value',"");
		$('#if_menuOrder').attr('value',"");
		$('#if_pMenuId option').each(function(){
			$(this).attr('selected',false);
		});
		/*$('#if_appId option').each(function(){
			$(this).attr('selected',false);
		});*/
		$('#appId').attr('value',"");

	}else if(type=="viewMenu"){
		$('#if_menuId').attr('value',menuId);
		$('#if_menuName').attr('value',menuName);
		//alert(menuDesc);
		$('#if_menuDesc').attr('value',menuDesc); 
		$('#if_menuDesc').text(menuDesc);
		$('#if_menuLevel').attr('value',menuLevel);
		$('#if_menuUrl').attr('value',menuUrl);
		$('#if_menuOrder').attr('value',menuOrder);
		$('#if_pMenuId').attr('value',pMenuName);
		$('#pMenuId').attr('value',pMenuId);
		$('#appId').attr('value',appId);
		$('#if_menuParam2').attr('value',menuParam2);
		$('#if_menuParam3').attr('value',menuParam3);
		//alert(menuType);
		$('#appName').attr('value',appName);
		$('#menuType option').each(function(){
			if($(this).val()==menuType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});

		if(menuParam2 && menuParam2!=''){
			//alert("??????"+menuParam2);
			$('#belong_1').iCheck('uncheck');
			$('#belong_2').iCheck('check'); 
			$('#belong_3').iCheck('uncheck'); 
		}
		if(menuParam3 && menuParam3!=''){
			//alert("??????"+menuParam3);
			$('#belong_1').iCheck('uncheck');
			$('#belong_2').iCheck('uncheck'); 
			$('#belong_3').iCheck('check'); 
		}
		
		$('#saveUpdateBtn').hide();
		
		disableForm('menuInfoFrom',true);
	}else if(type=="updateMenu"){	
		$('#if_menuId').attr('value',menuId);
		$('#if_menuName').attr('value',menuName);
		$('#if_menuDesc').attr('value',menuDesc); 
		$('#if_menuDesc').text(menuDesc);
		$('#if_menuLevel').attr('value',menuLevel);
		$('#if_menuUrl').attr('value',menuUrl);
		$('#if_menuOrder').attr('value',menuOrder);
		$('#if_pMenuId').attr('value',pMenuName);
		$('#pMenuId').attr('value',pMenuId);
		$('#appId').attr('value',appId);
		$('#if_menuParam2').attr('value',menuParam2);
		$('#if_menuParam3').attr('value',menuParam3);
		
		$('#appName').attr('value',appName);
		$('#menuType option').each(function(){
			if($(this).val()==menuType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		
		if(menuParam2 && menuParam2!=''){
			//alert("??????"+menuParam2);
			$('#belong_1').iCheck('uncheck');
			$('#belong_2').iCheck('check'); 
			$('#belong_3').iCheck('uncheck'); 
		}
		if(menuParam3 && menuParam3!=''){
			//alert("??????"+menuParam3);
			$('#belong_1').iCheck('uncheck');
			$('#belong_2').iCheck('uncheck'); 
			$('#belong_3').iCheck('check'); 
		}
	}
});

/**
 * 接收返回信息
 * @param el
 * @param method
 */
function rtSlModal(){
	//校验数据
	if(!$("#menuInfoFrom").valid()){
		return;
	}
	
	var data = $('#menuInfoFrom').serialize();
	var url = "";
	if(type == "addMenu"){
		url = root + '/menu.do?method=addMenu';
	}else if (type == "updateMenu"){
		url = root + '/menu.do?method=updateMenu';
	}
	$.ajax({ 
		type: 'post', 
        url: url, 
        data: data, 
        success: function(msg){
	        if(msg.code=='1'){
	        	alert("保存成功！"); //弹出成功msg
	            //刷新表格数据
	        	parent.search();
	            //parent.$('#menuModal').modal('hide');
	        	closWindow();
	        }else{
	        	alert(msg.message); //弹出失败msg
	        }
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
	});
}
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
/**
 * 校验
 */
function validateForm(){
	$("#menuInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			menuName: {required: true},
			menuOrder: {required: true},
		},
		messages: {
			menuName: {required: '请输入菜单名称！'},
			menuOrder: {required: '请输入排序次号！'}
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
//显示下拉控件表格，并定位位置
function showSys(){
	var appName = $("#appName");
	var appNameOffset = $("#appName").offset();
	$("#system").css({left:appNameOffset.left + "px" , top:appNameOffset.top + appName.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown",onBodyDownS);
}

function hideSys (){
	$("#system").fadeOut("fast");
	$("body").unbind("mousedown",onBodyDownS);
}

function onBodyDownS(event){
	if(!(event.target.id == "appName" || event.target.id == "system" || $(event.target).parents("#system").length>0)){
		hideSys();
	}
}
var zTree;
function showMenu() {
	if(!zTree){ //只加载一次
		//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
		zTree = $.fn.zTree.init($("#clsTree"), setting);
	}
	var obj = $("#if_pMenuId");
	var offset = $("#if_pMenuId").offset();
	$("#menuContent").css({left: offset.left + "px", top: offset.top + obj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

/**
 * 显示报表的下拉树
 */
function showReportTree(){
	if(!reportMetaTree){ //只加载一次
		//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
		reportMetaTree = $.fn.zTree.init($("#reportMetaTree"), repSetting);
	}
	var obj = $("#if_menuParam2");
	var offset = $("#if_menuParam2").offset();
	$("#reportMetaTree").css({left: offset.left + "px", top: offset.top + obj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDownRep);
}

function hideReportMetaTree() {
	$("#reportMetaTree").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownRep);
}

function onBodyDownRep(event) {
	if (!(event.target.id == "reportMetaTree" || $(event.target).parents("#reportMetaTree").length>0)) {
		hideReportMetaTree();
	}
}


/**
 * 显示分类导航搜索的树
 */
function showSearchClsTree(){
	if(!searchClsTree){ //只加载一次
		//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
		searchClsTree = $.fn.zTree.init($("#searchClsTree"), searchClsSetting,rootNodes);
	}
	var obj = $("#if_menuParam3");
	var offset = $("#if_menuParam3").offset();
	$("#searchClsTree").css({left: offset.left + "px", top: offset.top + obj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDownSearchCls);
}

function hideSearchClsTree() {
	$("#searchClsTree").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDownSearchCls);
}

function onBodyDownSearchCls(event) {
	if (!(event.target.id == "searchClsTree" || $(event.target).parents("#searchClsTree").length>0)) {
		hideSearchClsTree();
	}
}


/**
 * 禁用form表单中所有的input[文本框、复选框、单选框],select[下拉选],多行文本框[textarea]
 */
function disableForm(formId,isDisabled) {
  
  var attr="disable";
	if(!isDisabled){
	   attr="enable";
	}
	$("form[id='"+formId+"'] :text").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] textarea").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] select").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] :radio").attr("disabled",isDisabled);
	$("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled);
	
	//禁用jquery easyui中的下拉选（使用input生成的combox）

	$("#" + formId + " input[class='combobox-f combo-f']").each(function () {
		if (this.id) {alert("input"+this.id);
			$("#" + this.id).combobox(attr);
		}
	});
	
	//禁用jquery easyui中的下拉选（使用select生成的combox）
	$("#" + formId + " select[class='combobox-f combo-f']").each(function () {
		if (this.id) {
		alert(this.id);
			$("#" + this.id).combobox(attr);
		}
	});
	
	//禁用jquery easyui中的日期组件dataBox
	$("#" + formId + " input[class='datebox-f combo-f']").each(function () {
		if (this.id) {
		alert(this.id)
			$("#" + this.id).datebox(attr);
		}
	});
}