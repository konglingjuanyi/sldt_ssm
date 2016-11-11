$(document).ready(function(){
	initPrivCate();
	initPPrivId();//初始化父级权限
	validateForm();
	
if(type != "addPriv"){
		if("上架" == privStat){
			privStat = "1";
		}else if("下架" == privStat){
			privStat = "0";
		}
		
		if("BIEE内置权限组" == privType){
			privType = "1";
		}else if("web权限组" == privType){
			privType = "2";
		}
	}
	if(type=="addPriv"){
		$('#priv_id').attr('value',"");
		$('#priv_name').attr('value',"");
		$('#priv_desc').attr('value',"");
		$('#parent_priv option').each(function(){
			$(this).attr('selected',false);
		});
		$('#priv_type option').each(function(){
			$(this).attr('selected',false);
		});
		$('#cate_id option').each(function(){
			if($(this).val()==cateId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		$('#privstat_id').each(function(){
			$(this).attr('selected',false);
		});
		$('#priv_memo').text("");
	}else if(type=="viewPriv"){
		$('#priv_id').attr('value',privId);
		$('#priv_name').attr('value',privName);
		$('#priv_desc').attr('value',privDesc);
		 
		$('#parent_priv').val(pPrivId);
		$('#parent_priv option').each(function(){
			if($(this).val()==pPrivId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_type option').each(function(){
			if($(this).val()==privType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#cate_id option').each(function(){
			if($(this).val()==cateId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#privstat_id option').each(function(){
			if($(this).val()==privStat) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		 });
		$('#priv_memo').text(memo);
		$('#saveUpdateBtn').hide();
		
		disableForm('PrivInfoFrom',true);
	}else if(type=="updatePriv"){
		$('#priv_id').attr('value',privId);
		$('#priv_name').attr('value',privName);
		$('#priv_desc').attr('value',privDesc);
		
		$('#parent_priv').val(pPrivId);
		
		$('#parent_priv option').each(function(){
			if($(this).val()==pPrivId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_type option').each(function(){
			if($(this).val()==privType) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#cate_id option').each(function(){
			if($(this).val()==cateId) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#privstat_id option').each(function(){
			if($(this).val()==privStat) {
				$(this).attr('selected','selected');
			}else { 
				$(this).attr('selected',false);
			}
		});
		 
		$('#priv_memo').text(memo);
	}
});
/**
 * 接收返回信息
 * @param el
 * @param method
 */
function rtSlModal(){
	//校验数据
	if(!$("#PrivInfoFrom").valid()){
		return;
	}
	
	var data = $('#PrivInfoFrom').serialize();
	var url = "";
	if (type == "addPriv"){
		 url = context+'/privlg.do?method=addPriv';
	}else if (type == "updatePriv"){
		url = context+'/privlg.do?method=updatePriv';
	}
	$.ajax({ 
         type:'post', 
         url:url,
         data:data, 
         success:function(msg){
            if(msg.code=='1'){
                alert("保存成功！");        //弹出成功msg
                //刷新表格数据
                parent.search();
                closWindow();
            }else{
                alert(msg.message);  //弹出失败msg
            }
         },
         error:function(msg){
            alert("访问服务器出错！");        
         }
    });
}
/**
 * 校验
 */
function validateForm(){
	$("#PrivInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			cateid:{required:true},
			privname: {required: true}
		},
		messages: {
			privname: {required: '请输入权限名称！'},
			cateid:{required:"请选择权限类别！"}
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

/*
 * 初始化父权限编号ID
 */
function initPPrivId(){
	$('#parent_priv').html("<option value=''>无</option>");
	$.ajax({ 
		type:'post', 
        url:context+'/privlg.do?method=initPPrivId', 
        async:false,
        success:function(data){
        	var options = "<option value=''>无</option>";
        	for(var i=0;i<data.length;i++){
        		var privId = data[i].privId;
        		var privName = data[i].privName;
        		options += "<option value="+privId+">"+privName+"</option>";
        	}
        	$('#parent_priv').html(options);
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
   });
}
/*
 * 初始化权限类别
 */
function initPrivCate(){
	$.ajax({ 
		type:'post', 
		async : false,
        url:context+'/privlg.do?method=initPrivCate', 
        success:function(data){
        	var options = "";
        	for(var i=0;i<data.length;i++){
        		var privCateName = data[i].privCateName;
        		options = "<option value="+privCateName+">"+privCateName+"</option>";
        		$('#cate_id').append(options);
        	}
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
   });
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
	        url: context + "/exPriv.do?method=queryExPriv",
	        autoParam: ["id"] //自动提交的一个参数
		},
		callback: {
			onClick: onClick
		}
	};
function onClick(event, treeId, treeNode, clickFlag){
	//设置实际的菜单编号
	$("#parent_priv").val(treeNode.name);
	/*$("#if_pMenuId").val(treeNode.name);*/
	hideMenu();
}

var zTree;
function showMenu() {
	if(!zTree){ //只加载一次
		//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
		zTree = $.fn.zTree.init($("#clsTree"), setting);
	}
	var obj = $("#parent_priv");
	var offset = $("#parent_priv").offset();
	$("#menuContent").css({left: offset.left + "px", top: offset.top + obj.outerHeight() + "px"}).slideDown("fast");
	$("body").bind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

