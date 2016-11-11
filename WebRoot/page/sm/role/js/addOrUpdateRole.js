$(document).ready(function(){
	validateForm();
	if(type=="addRole"){
		 $('#if_roleId').attr('value',"");
		 $('#if_roleName').attr('value',"");
		 $('#if_setupDt').attr('value',"");
		 $('#if_roleDesc').attr('value',"");
		 $('#if_appId option').each(function(){
				$(this).attr('selected',false);
		 });
		 $('#if_memo').text("");
	}else if(type=="updateRole"){
		 $('#if_roleId').attr('value',roleId);
		 $('#if_roleName').attr('value',roleName);
		 $('#if_setupDt').attr('value',setupDt);
		 $('#if_roleDesc').attr('value',roleDesc);
		 $('#if_memo').text(memo);
		 $('#if_appId option').each(function(){
			 if($(this).val()==appId) {
				 $(this).attr('selected','selected');
			 }else { 
				 $(this).attr('selected',false);
			 }
		 });
		 $('#if_roleId_div').hide();
	}else if(type=="viewRole"){
		 $('#if_roleId').attr('value',roleId);
		 $('#if_roleName').attr('value',roleName);
		 $('#if_setupDt').attr('value',setupDt);
		 $('#if_roleDesc').attr('value',roleDesc);
		 $('#if_memo').text(memo);
		 $('#if_appId option').each(function(){
			 if($(this).val()==appId) {
				 $(this).attr('selected','selected');
			 }else { 
				 $(this).attr('selected',false);
			 }
		 });
		 $('#saveUpdateBtn').hide();
	}
});

/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}
/**
 * 接收返回信息
 */
function slModalFn(){
	//校验数据
	if(!$("#roleInfoFrom").valid()){
		return;
	}
	var data = $('#roleInfoFrom').serialize();
	var url = "";
	if(type == 'addRole') {
		url=context +"/role.do?method=addRole";
	}else if(type == 'updateRole'){
		url=context +"/role.do?method=updateRole";
	}
	$.ajax({ 
         type:'post', 
         //url:context+'/role.do?method='+method, 
         url:url,
         data:data, 
         success:function(msg){
            if(msg.code=='1'){
                alert("保存成功！");        //弹出成功msg
                //刷新表格数据
                parent.search();
              //  parent.$('#roleModal').modal('hide');
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
	//验证角色
	jQuery.validator.addMethod("isRuleId", function(value, element) { 
		var regx = /^[a-zA-Z_]+$/;
	    return this.optional(element) || (regx.test(value));
	}, "只能包含字母和下划线");
	$("#roleInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			roleId: {required: true,isRuleId:true},
			roleName: {required: true}
		},
		messages: {
			roleId: {required: '请输入角色编号！'},
			roleName: {required: '请输入角色名称！'}
		}
	});
	
}
