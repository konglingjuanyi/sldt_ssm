$(document).ready(function(){
	validateForm();
	if(type=="addPrivCate"){
		$('#if_privCateId').attr('value',"");
		$('#if_privCateName').attr('value',"");
		$('#if_checkType option').each(function(){
			$(this).attr('selected',false);
		});

	}else if(type=="viewPrivCate"){
		$('#if_privCateId').attr('value',privCateId);
		$('#if_privCateName').attr('value',privCateName);
		$('#if_checkType option').each(function(){
			if($(this).val() == checkType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#saveBtn').hide();
	}else if(type=="updatePrivCate"){	
		$('#if_privCateId').attr('value',privCateId);
		$('#if_privCateName').attr('value',privCateName);
		$('#if_checkType option').each(function(){
			if($(this).val() == checkType) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
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
 * @param el
 * @param method
 */
function rtSlModal(el){
	//校验数据
	if(!$("#privCateInfoFrom").valid()){
		return;
	}
	var data = $('#privCateInfoFrom').serialize();
	var url = "";
	if(type == "addPrivCate"){
		url = context + '/privCate.do?method=addPrivCate';
	}else if (type == "updatePrivCate"){
		url = context + '/privCate.do?method=updatePrivCate';
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
	$("#mateInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			privCateName: {required: true},
		},
		messages: {
			privCateName: {required: '请输入类别名称！'},
		}
	});
}