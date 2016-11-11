   var tool = [{ text: ' 添加 ' , iconCls: 'fa fa-plus-circle icolor',btnCls: 'button-gp button-info', handler: function () {
	                 //$('#roleModal').modal('show');
	                  openAddWin('addRole',null);
				}
			}, '-', 
			{
				text: ' 修改 ' , iconCls: 'fa fa-edit icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						//alert(rowData.roleName+"----"+rowData.memo);
						openUpdateWin('updateRole',rowData);
					}else{
						alert("请选择一条记录！");
					}
			   
				}
			}, '-',
			{ text: ' 查看详细' , iconCls: 'fa fa-search icolor',btnCls: 'button-gp button-info', handler: function () {
					var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
					if(ids.length==1){
						var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
						//alert(rowData.roleName+"----"+rowData.memo);
						openViewWin('viewRole',rowData);
					}else{
						alert("请选择一条记录！");
					}
				   
				}
			}, '-', 
			{ text: ' 权限配置' , iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function () {
				var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
				if(ids.length==1){
					var rowData = $("#grid-table").jqGrid('getRowData',ids[0]);
					openCfgWin('cfgRolePrivlg',rowData);
				}else{
					alert("请选择一条记录！");
				}
			   
			}
			},'-', { text: ' 元数据权限配置', iconCls: 'fa fa-magic icolor',btnCls: 'button-gp button-info', handler: function(){
				var ids = $('#grid-table').jqGrid('getGridParam', 'selarrrow');
				if(ids.length == 1){
					var rowData = $("#grid-table").jqGrid('getRowData', ids[0]);
					if(rowData.userId=="admin"){
						alert("admin是系统用户，不能进行权限配置！");
						return;
					}
					openMetaPrivWin("cfgRoleMetaPrivlg", rowData);
				}else{
					alert("请选择一条记录！");
				}
			}
		  }, '-', 
			{ text: ' 批量删除 ' , iconCls: 'fa fa-trash-o icolor',btnCls: 'button-gp button-info', handler: function () {
				var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');//--多个选中的id
				if(ids.length<1){
					layer.msg("请至少选择一个角色！");
					return;
				}
				var idsStr = "";
				if(confirm("您确定要删除选择的角色吗？")){
					  //获取要删除的roleId
					for(var i=0;i<ids.length;i++){
						  var rowData = $("#grid-table").jqGrid('getRowData',ids[i]),
						      roleId = rowData.roleId;
						  idsStr += ","+roleId;
					  };
					  deleteRolesByIds(idsStr.substring(1));
				  }
				//alert("删除----》"+idsStr.substring(1));
				}
			}];
			

$(document).ready(function() {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	var girdHeigth = getGridHeight("grid-table");
	
	jQuery(grid_selector).jqGrid({
		//data: grid_data,
		/*datatype: "json",
		url:'http://localhost:8080/mds_sm/role.do?method=list',*/
		datatype: "local",
		regional : 'cn',
		height: girdHeigth,
		autowidth: true,
		colNames:[ '角色编号','角色名称','所属子系统', '创建时间', '修改时间','描述','备注'],
		colModel:[
			{name:'roleId',index:'roleId', width:90,sortable:false, align:"center"},
			{name:'roleName',index:'roleName',width:90, align:"center"},
			{name:'appId',index:'appId', width:60, align:"center"},
			{name:'setupDt',index:'setupDt', width:70, align:"center"},
			{name:'lastUpdatedDt',index:'lastUpdatedDt', width:70, align:"center",resizable:true},
			{name:'roleDesc',index:'roleDesc', width:150, sortable:false, align:"center",resizable:true},
			{name:'memo',index:'memo', width:80, sortable:false, align:"center",hidden:false}
		], 
		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
		toolbar: [ true, "top" ,tool],
		multiselect: true,
        multiboxonly: true,

		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		}
		/*emptyrecords: "没有数据",
		recordtext: "显示 {0} - {1} 总记录数 {2}",
        loadtext: "正在加载数据...",
        pgtext : "当前{0}页,总共 {1}页",*/
		//editurl: $path_base+"/dummy.html",//nothing is saved
		/*caption: "角色信息表",*/
		/*autowidth: true*/
	}).jqGrid('setGridParam', {
	    page : 1,
	    url:context+'/role.do?method=list',
	    datatype : "json"
	}).trigger("reloadGrid");
	
	//监听窗口变化，调整插件的宽度
    var resizeDelay = 600;
    var doResize = true;
    var resizer = function () {
       if (doResize) {
    	   $(grid_selector).setGridWidth($(window).width()-5);
           doResize = false;
       }
     };
     var resizerInterval = setInterval(resizer, resizeDelay);
     resizer();
     $(window).resize(function() {
       doResize = true;
     });
     
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
		})
}

function doSearch(){
	var roleName = $("#searchRoleNm").val();
	if(roleName == null){
		return ;
	}
	roleName = encodeURI(roleName);
	$("#grid-table").jqGrid('setGridParam', {
	    page : 1,
	    url : context+'/role.do?method=list',
	    postData : {'roleName':roleName},
	    datatype : "json"
	}).trigger("reloadGrid");
}

/**
 * 根据类型打开模态框 
 *  load模窗口的数据
 * @param type
 * @param rowdata
 */
/*function openModalWin(type,rowData){
	 $('#saveUpdateBtn').show();
	 $('#if_roleId_div').show();
	if(type=="addRole"){
		 $('#roleTitle').html("添加角色");
		 $('#if_roleId').attr('value',"");
		 $('#if_setupDt').attr('value',"");
		 $('#if_roleName').attr('value',"");
		 $('#if_roleDesc').attr('value',"");
		 $('#if_appId option').each(function(){
				$(this).attr('selected',false);
		 });
		 $('#if_memo').text("");
	}else if(type=="viewRole"){
		 $('#roleTitle').html("查看角色【"+rowData.roleName+"】");
		 $('#if_roleId').attr('value',rowData.roleId);
		 $('#if_setupDt').attr('value',rowData.setupDt);
		 $('#if_roleName').attr('value',rowData.roleName);
		 $('#if_roleDesc').attr('value',rowData.roleDesc);
		 $('#if_appId option').each(function(){
			 if($(this).val()==rowData.appId) {
				 $(this).attr('selected','selected');
			 }else { 
				 $(this).attr('selected',false);
			 }
		 });
		 $('#if_memo').text(rowData.memo);
		 $('#saveUpdateBtn').hide();
	}else if(type=="updateRole"){
		 $('#roleTitle').html("修改角色【"+rowData.roleName+"】");
		 $('#if_roleId').attr('value',rowData.roleId);
		 $('#if_setupDt').attr('value',rowData.setupDt);
		 $('#if_roleName').attr('value',rowData.roleName);
		 $('#if_roleDesc').attr('value',rowData.roleDesc);
		 $('#if_appId option').each(function(){
			 if($(this).val()==rowData.appId) {
				 $(this).attr('selected','selected');
			 }else { 
				 $(this).attr('selected',false);
			 }
		 });
		 $('#if_memo').text(rowData.memo);
		 $('#if_roleId_div').hide();
	}
	
	var slModalHtml = $('#slModal').html();
	parent.showSlModal('roleModal',slModalHtml,type);
}*/
function openAddWin(type){
	var url= context+"/page/sm/role/addOrUpdateRole.jsp?type="+type;
    parent.openWin(url,"addRole");	
}
function openUpdateWin(type,rowData){
	var roleId = rowData.roleId;
	var roleName = rowData.roleName;
	var setupDt = rowData.setupDt;
	var roleDesc = rowData.roleDesc;
	var appId = rowData.appId;
	var memo = rowData.memo;
	/*roleName = encodeURI(roleName);*/
	var url= context+"/page/sm/role/addOrUpdateRole.jsp?type="+type+"&roleId="+roleId+"&roleName="+encodeURI(encodeURI(roleName))+"&setupDt="+encodeURI(encodeURI(setupDt))+"&roleDesc="+encodeURI(encodeURI(roleDesc))+"&memo="+encodeURI(encodeURI(memo))+"&appId="+appId;
    parent.openWin(url,"updateRole");	
}
function openViewWin(type,rowData){
	var roleId = rowData.roleId;
	var roleName = rowData.roleName;
	var setupDt = rowData.setupDt;
	var roleDesc = rowData.roleDesc;
	var appId = rowData.appId;
	var memo = rowData.memo;
	/*roleName = encodeURI(roleName);*/
	var url= context+"/page/sm/role/addOrUpdateRole.jsp?type="+type+"&roleId="+roleId+"&roleName="+encodeURI(encodeURI(roleName))+"&setupDt="+encodeURI(encodeURI(setupDt))+"&roleDesc="+encodeURI(encodeURI(roleDesc))+"&memo="+encodeURI(encodeURI(memo))+"&appId="+appId;
    parent.openWin(url,"viewRole");
}
/**
 * 根据类型打开模态框 
 *  load模窗口的数据
 * @param type
 * @param rowdata
 */
function openCfgWin(type,rowData){
	//$('#rolePrivlgTitle').html("角色【"+rowData.roleName+"】权限配置");
	var url = context+"/page/sm/role/rolePrivlg.jsp?roleId="+rowData.roleId;
	/*$('#rolePrivlgWin').attr("src",url);
	var slModalHtml = $('#cfgModal').html();
	parent.showSlModal('rolePrivlgModal',slModalHtml,type);*/
	parent.openWin(url,"rolePrivlgModal");
}
/**
 * 接收返回信息
 * @param el
 * @param method
 */
/*function rtSlModal(el,method){
	//$('#roleModal').replaceWith(el);
	var roleName = parent.$('#if_roleName').val();
	var roleDesc = parent.$('#if_roleDesc').val();
	var appId = parent.$('#if_appId').val();
	var memo = parent.$('#if_memo').val();
	
	if(method=='addRole'){
		alert(data);
	}else if(method=='updateRole'){
		alert(data);
	}
	var data = parent.$('#roleInfoFrom').serialize();
	$.ajax({ 
         type:'post', 
         url:context+'/role.do?method='+method, 
         data:data, 
         success:function(msg){
            if(msg.code=='1'){
                alert("保存成功！");        //弹出成功msg
                //刷新表格数据
                doSearch();
                parent.$('#roleModal').modal('hide');
            }else{
                alert(msg.message);  //弹出失败msg
            }
         },
         error:function(msg){
            alert("访问服务器出错！");        
         }
    });
}*/

function openMetaPrivWin(type, rowData){
	var url = context+"/page/sm/role/roleMetaPrivlg.jsp?roleId="+rowData.roleId;
	parent.openWin(url,"metaPrivlgModal");
}

/**
 * 批量删除
 * @param ids
 */
function deleteRolesByIds(ids){
	$.ajax({ 
        type:'post', 
        url:context+'/role.do?method=batchDelete', 
        data:"&ids="+ids, 
        success:function(msg){
           if(msg.code=='1'){
               alert("删除成功！");        //弹出成功msg
               //刷新表格数据
               doSearch();
           }else{
               alert(msg.message);  //弹出失败msg
           }
        },
        error:function(msg){
           alert("访问服务器出错！");        
        }
   });
}
