$(document).ready(function(){
	validateForm();
	//保存
	$("#saveModelButton").click(function(){
		//校验数据
		if(!$("#add-modal-form").valid()){
			return;
		}
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.style(index, {
			  width: '1200',
			  height: '600px',
			  top:'10px',
			  left:'10%'
			});  
		$("#add-modal-form")[0].submit();
	});
});

/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}

/**
 * 校验
 */
function validateForm(){
	//验证角色
	jQuery.validator.addMethod("isChinese", function(value, element) {
		var regx = /[\u4e00-\u9fa5]+/;
	    return this.optional(element) || !(regx.test(value));
	}, "不能含有中文！");
	
	$("#add-modal-form").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			name: {required: true},
			key: {required: true,isChinese:true}
		},
		messages: {
			name: {required: '请输入模型名称！'},
			key: {required: '请输入模型key值！'}
		}
	});
	
}
