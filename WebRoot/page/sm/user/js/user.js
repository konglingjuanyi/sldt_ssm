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

var tool = [{ text: ' 查看 ',iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				openViewWin('viewUser', rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, 
     /*'-', { text: ' 添加 ', iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
			if(orgId == null){
				alert("您好，请选择左侧的机构树中的一个机构！");
				return;
			}
			if(orgId == "0000"){
				alert("您好，添加用户时所选机构不能为所有机构，请重新选择！");
				return;
			}
			openAddWin('addUser', null);
		}
	}, '-', { text: ' 修改 ', iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				if(rowData.userId=="admin"){
					alert("admin是系统用户，不能修改！");
					return;
				}
				openUpdateWin('updateUser', rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, 
	'-', { text: ' 批量删除 ', iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length < 1){
				alert("请至少选择一条记录！");
				return;
			}
			var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
			if(rowData.userId=="admin"){
				alert("admin是系统用户，不能删除！");
				return;
			}
			var idsStr = "";
			if(confirm("您确定要删除选中的记录吗？")){
				  //获取要删除的userId
				for(var i=0;i<ids.length;i++){
					  var rowData = $("#grid-table").jqGrid('getRowData', ids[i]),
					  userId = rowData.userId;
					  idsStr += "," + userId;
				  };
				  deleteUsersByIds(idsStr.substring(1));
			  }
		}
	},*/
	 '-', { text: ' 角色配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function() {
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				if(rowData.userId=="admin"){
					alert("admin是系统用户，不能进行角色配置！");
					return;
				}
				openRoleWin("cfgUserRole", rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
		
	}, '-', { text: ' 权限配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				if(rowData.userId=="admin"){
					alert("admin是系统用户，不能进行权限配置！");
					return;
				}
				openPrivWin("cfgUserPrivlg", rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}, '-', { text: ' 同步用户信息 ', iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
		var msg = "<font color='red'>同步用户信息前建议先同步机构信息，保证机构信息为最新。是否继续同步？</font>";
		parent.layer.confirm(msg,{btn:["确认","取消"]},function(indx){
			syncUserInfo();
			layer.close(indx);
		});	
		}
	}, '-', { text: ' 元数据权限配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
			var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
			if(ids.length == 1){
				var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
				if(rowData.userId=="admin"){
					alert("admin是系统用户，不能进行权限配置！");
					return;
				}
				openMetaPrivWin("cfgUserMetaPrivlg", rowData);
			}else{
				alert("请选择一条记录！");
			}
		}
	}];

$(document).ready(function() {
	//布局设置
	myLayout = $('body').layout({
		west__minSize:210,	
		west__resizable:true
	});
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	orgTree = $.fn.zTree.init($("#orgTree"), setting, uoNodes);

	var girdHeigth = getGridHeight("grid-table");
	jQuery(grid_selector).jqGrid({
		//direction: "rtl",
		url: context + "/user.do?method=listUser",
		datatype: "json",
		regional: 'cn',
		mtype: "POST",
		height: girdHeigth,
		colNames: ['登录名', '机构编号', '用户名','所属机构', '用户状态', '启用日期', '截至日期', '登录密码', '联系电话', '邮箱地址', '同步时间', '最后维护时间'],
		colModel : [
		            {name:'userId', index:'userId', width:'20%', align:'center'},
		            {name:'orgId', index:'orgId', width:'20%', align:'center'},
		            {name:'name', index:'name', width:'20%', align:'center'},
		          /*  {name:'certType', index:'certType', width:'20%', align:'center', formatter:function(cellValue){
		            	var value = "";
		            	if("0" == cellValue){
		            		value = "身份证";
		            	}else if("1" == cellValue){
		            		value = "户口本";
		            	}else if("2" == cellValue){
		            		value = "护照";
		            	}
		            	return value;
		            }},
		            {name:'certNum', index:'certNum', width:'40%', align:'center'},*/
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
		pager: pager_selector,
		altRows: true,
		//toppager: true,
		//toolbar: [true,"top"],
		toolbar: [ true, "top" ,tool],
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
		loadComplete: function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},
		//editurl: $path_base+"/dummy.html",//nothing is saved
		/*caption: "用户信息表",*/
		autowidth: true
	});
	
	//监听窗口变化，调整插件的宽度
    var resizeDelay = 1000;
    var doResize = true;
    var resizer = function () {
       if (doResize) {
    	  // alert($("#managerContent1").width());
    	   $(grid_selector).setGridWidth($("#managerContent1").width()-5);
           doResize = false;
       }
     };
     var resizerInterval = setInterval(resizer, resizeDelay);
     resizer();
     $(window).resize(function() {
       doResize = true;
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
	})
}

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
}
	
function beforeDeleteCallback(e) {
	var form = $(e[0]);
	if(form.data('styled')) return false;
	form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
	style_delete_form(form);
	form.data('styled', true);
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

/*function openModalWin(type, rowData){
	$('#saveBtn').show();
	$('#if_userId_div').show();
	$('#if_logonPwd_div').show();
	$('#if_logonPwd_div_').show();
	if(type != "addUser"){
		if("身份证" == rowData.certType){
			rowData.certType = "0";
		}else if("户口本" == rowData.certType){
			rowData.certType = "1";
		}else if("护照" == rowData.certType){
			rowData.certType = "2";
		}
		
		if("已启用" == rowData.userStat){
			rowData.userStat = "0";
		}else if("未启用" == rowData.userStat){
			rowData.userStat = "1";
		}else if("作废" == rowData.userStat){
			rowData.userStat = "2";
		}else if("暂时停用" == rowData.userStat){
			rowData.userStat = "5";
		}else if("锁定" == rowData.userStat){
			rowData.userStat = "6";
		}
	}
	
	if(type=="addUser"){
		$('#userTitle').html("添加用户");
		$('#if_userId').attr('value',"");
		$('#if_orgId').attr('value',orgId);
		$('#if_orgNum').attr('value',orgName);
		$('#if_userName').attr('value',"");
		$('#if_certType option').each(function(){
			$(this).attr('selected',false);
		});
		$('#if_certNum').attr('value',"");
		$('#if_userStat option').each(function(){
			$(this).attr('selected',false);
		});
		$('#if_contTel').attr('value',"");
		$('#if_contEmail').attr('value',"");
	}else if(type=="viewUser"){
		$('#userTitle').html("用户信息查看【"+rowData.userId+"】");
		$('#if_userId').attr('value',rowData.userId);
		$('#if_userName').attr('value',rowData.name);
		$('#if_certType option').each(function(){
			if($(this).val() == rowData.certType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_certNum').attr('value',rowData.certNum);
		$('#if_userStat option').each(function(){
			if($(this).val() == rowData.userStat) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_contTel').attr('value',rowData.contTel);
		$('#if_contEmail').attr('value',rowData.contEmail);
		$('#saveBtn').hide();
		$('#if_logonPwd_div').hide();
		$('#if_logonPwd_div_').hide();
	}else if(type=="updateUser"){
		$('#userTitle').html("修改用户【"+rowData.userId+"】");
		$('#if_userId').attr('value',rowData.userId);
		$('#if_userName').attr('value',rowData.name);
		$('#if_certType option').each(function(){
			if($(this).val() == rowData.certType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_certNum').attr('value',rowData.certNum);
		$('#if_userStat option').each(function(){
			if($(this).val() == rowData.userStat) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_contTel').attr('value',rowData.contTel);
		$('#if_contEmail').attr('value',rowData.contEmail);
		$('#if_userId_div').hide();
		$('#if_logonPwd_div').hide();
		$('#if_logonPwd_div_').hide();
	}
	
	var slModalHtml = $('#slModal').html();
	parent.showSlModal('userModal',slModalHtml,type);
}*/
function openAddWin(type,rowData){
	var url= context+"/page/sm/user/addOrUpdateUser.jsp?type="+type+"&orgId="+orgId+'&orgName='+encodeURI(encodeURI(orgName));
    parent.openWin(url,"addUser");	
}
function openUpdateWin(type,rowData){
	var userId = rowData.userId;
	var userName = rowData.name;
	var certType = rowData.certType;
	var certNum = rowData.certNum;
	var userStat = rowData.userStat;
	var contTel = rowData.contTel;
	var contEmail = rowData.contEmail;
    var url= context+"/page/sm/user/addOrUpdateUser.jsp?type="+type+"&userId="+userId+"&userName="+encodeURI(encodeURI(userName))+"&certType="+encodeURI(encodeURI(certType))+"&certNum="+encodeURI(encodeURI(certNum))+"&userStat="+encodeURI(encodeURI(userStat))+"&contTel="+contTel+"&contEmail="+contEmail+'&orgName='+encodeURI(encodeURI(orgName));
    parent.openWin(url,"updateUser");	
 }
function openViewWin(type,rowData){
	var userId = rowData.userId;
	var userName = rowData.name;
	var certType = rowData.certType;
	var certNum = rowData.certNum;
	var userStat = rowData.userStat;
	var contTel = rowData.contTel;
	var contEmail = rowData.contEmail;
    var url= context+"/page/sm/user/addOrUpdateUser.jsp?type="+type+"&userId="+userId+"&userName="+encodeURI(encodeURI(userName))+"&certType="+encodeURI(encodeURI(certType))+"&certNum="+encodeURI(encodeURI(certNum))+"&userStat="+encodeURI(encodeURI(userStat))+"&contTel="+contTel+"&contEmail="+contEmail;
    parent.openWin(url,"viewUser");
}    
function openPrivWin(type, rowData){
	//$('#userPrivlgTitle').html("权限配置【"+rowData.name+"】");
	var url = context+"/page/sm/user/userPrivlg.jsp?userId="+rowData.userId;
/*	$('#userPrivlgWin').attr("src", url);
	var slModalHtml = $('#cfgPrivlgModal').html();
	parent.showSlModal('userPrivlgModal', slModalHtml, type);*/
	parent.openWin(url,"userPrivlgModal");
}

function openMetaPrivWin(type, rowData){
	var url = context+"/page/sm/user/userMetaPrivlg.jsp?userId="+rowData.userId;
	parent.openWin(url,"metaPrivlgModal");
}

function openRoleWin(type, rowData){
	//$('#userRoleTitle').html("角色配置【"+rowData.name+"】");
	var url = context+"/page/sm/user/userRole.jsp?userId="+rowData.userId;
	/*$('#userRoleWin').attr("src", url);
	var slModalHtml = $('#cfgRoleModal').html();
	parent.showSlModal('userRoleModal', slModalHtml, type);*/
	parent.openWin(url,"userRoleModal");
}

/**
 * 批量删除
 * @param ids
 */
function deleteUsersByIds(ids){
	$.ajax({ 
		type: 'post', 
        url: context + '/user.do?method=batchDelete', 
        data: "&ids=" + ids, 
        success: function(msg){
        	if(msg.code == '1'){
        		alert("删除成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		alert(msg.message); //弹出失败msg
        	}
        },
        error: function(msg){
        	alert("访问服务器出错！");        
        }
   });
}

/**
 * 同步用户信息
 * @param ids
 */
function syncUserInfo(){
	var loadIdx = parent.layer.load(1);
	$.ajax({ 
		type: 'post', 
        url: context + '/user.do?method=syncTsmUser', 
        success: function(msg){
        	if(msg.code == '0'){
        		alert("同步成功！"); //弹出成功msg
        		//刷新表格数据
        		doSearch();
        	}else{
        		alert(msg.message); //弹出失败msg
        	}
        	parent.layer.close(loadIdx);
        },
        error: function(msg){
        	alert("访问服务器出错！");   
        	parent.layer.close(loadIdx);
        }
   });
}

/**
 * 接收返回信息
 * @param el
 * @param method
 *//*
function rtSlModal(el, method){
	var data = parent.$('#userInfoFrom').serialize();
	$.ajax({ 
		type: 'post', 
        url: context + '/user.do?method=' + method, 
        data: data, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("保存成功！"); //弹出成功msg
                //刷新表格数据
                doSearch();
                parent.$('#userModal').modal('hide');
            }else{
            	alert(msg.message); //弹出失败msg
            }
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
    });
}*/