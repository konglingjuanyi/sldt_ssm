$(document).ready(function(){
	if( type == "addUser" || type == "updateUser"){
		validateForm();
	}
	$('#if_userId_div').show();
	$('#if_logonPwd_div').show();
	$('#if_logonPwd_div_').show();
	if(type != "addUser"){
		if("身份证" == certType){
			certType = "0";
		}else if("户口本" == certType){
			certType = "1";
		}else if("护照" == certType){
			certType = "2";
		}
		
		if("已启用" == userStat){
			userStat = "0";
		}else if("未启用" == userStat){
			userStat = "1";
		}else if("作废" == userStat){
			userStat = "2";
		}else if("暂时停用" == userStat){
			userStat = "5";
		}else if("锁定" == userStat){
			userStat = "6";
		}
	}
	if(type=="addUser"){
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
	}else if(type=="updateUser"){
	$('#if_userId').attr('value',userId);
	$('#if_userName').attr('value',userName);
	$('#if_certType option').each(function(){
		if($(this).val() == certType) {
			$(this).attr('selected', 'selected');
		}else { 
			$(this).attr('selected', false);
		}
	});
	$('#if_certNum').attr('value',certNum);
	$('#if_userStat option').each(function(){
		if($(this).val() == userStat) {
			$(this).attr('selected', 'selected');
		}else { 
			$(this).attr('selected', false);
		}
	});
	$('#if_contTel').attr('value',contTel);
	$('#if_contEmail').attr('value',contEmail);
	$('#if_userId_div').hide();
	$('#if_logonPwd_div').hide();
	$('#if_logonPwd_div_').hide();
	}else if(type=="viewUser"){
		$('#if_userId').attr('value',userId).attr("readonly",true);
		$('#if_userName').attr('value',userName).attr("readonly",true);
		$('#if_certType option').each(function(){
			if($(this).val() == certType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_certNum').attr('value',certNum);
		$('#if_userStat option').each(function(){
			if($(this).val() == userStat) {
				$(this).attr('selected', 'selected');
			}else { 
				//$(this).attr('selected', false);
				$(this).remove();
			}
		});
		$('#if_contTel').attr('value',contTel).attr("readonly",true);;
		$('#if_contEmail').attr('value',contEmail).attr("readonly",true);;
		$('#saveBtn').hide();
		$('#if_logonPwd_div').hide();
		$('#if_logonPwd_div_').hide();
	}
	
});
/**
 * 接收返回信息
 * @param el
 * @param method
 */
function rtSlModal(el){
	//校验数据
	if(!$("#userInfoFrom").valid()){
		return;
	}
	var url= "";
	if(type == 'addUser'){
		url =  context + '/user.do?method=addUser';
	}else if(type== 'updateUser'){
		url =  context + '/user.do?method=updateUser';
	}
	var data = $('#userInfoFrom').serialize();
	//获取用户编号
	$.ajax({ 
		type: 'post', 
        url: url,
        data: data, 
        async : false,
        success: function(msg){
        	if(msg.code=='1'){
        		alert("保存成功！"); //弹出成功msg
                //刷新表格数据
        		parent.search();
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
/**
 * 校验
 */
function validateForm(){
	//jq验证器添加自定义验证方法
	jqValidatAddMethod();
	$("#userInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			userId: {required: true,isUserId:true},
			userName: {required: true},
			logonPwd: {required: true,isPassword:true},
			logonPwd_: {required: true, isPassword:true,equalTo: "#if_logonPwd"},
			contEmail:{required:false,email:true},
			contTel:{required:false,isPhone:true}
		},
		messages: {
			userId: {required: '请输入用户编号！'},
			userName: {required: '请输入姓名！'},
			logonPwd: {required: '请输入登录密码！'},
			logonPwd_: {required: '请输入确认密码！',equalTo:"两次输入密码不一致"},
			contEmail:{email:"请输入合法邮箱地址！"}
		}
	});
}

function jqValidatAddMethod(){
	//验证用户编号
	jQuery.validator.addMethod("isUserId", function(value, element) { 
		var regx = /^[0-9a-zA-Z_]+$/;
	    return this.optional(element) || (regx.test(value));
	}, "只能包含数字字母和下划线");
	//证件号码验证,暂不校验
	$.validator.addMethod("isCredentials",function(value,element){
		var creType = $("#if_certType").val();//证件类型--0:身份证 ，1：户口本， 2：护照
		var regx = /.*/;
		if(creType == '0') {
			regx = /\d{15}|\d{18}$/;
		}else if(creType == '1') {
			
		}else if(creType == '2'){
			
		}else {}
		
	},"请输入合法的证件号码！");
	//电话验证
	$.validator.addMethod("isPhone",function(value,element){
		alert(55555);
		var regx = /\d{3}\d{8}/;
		return this.optional(element) || (regx.test(value));
	},"请输入合法的电话号码！");
	//密码验证
	$.validator.addMethod("isPassword",function(value,element){
		var regx = /^[0-9a-zA-Z_]*$/;
		return this.optional(element) || (regx.test(value));
	},"密码只能包含数字、字母、下划线！");
}

/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}