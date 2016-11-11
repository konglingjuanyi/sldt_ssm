//按钮类型
var BtnType = {'新增':'1','删除':'2','修改':'3','查询':'4','上传':'5','下载':'6','导入':'7','打印':'8','default':""};
$(document).ready(function(){
	validateForm();
	if(type=="addBtn"){
		$('#if_btnId').attr('value',"");
		$('#if_menuId').attr('value',menuId);
		$('#if_btnText').attr('value',"");
		$('#if_btnNickname').attr('value',""); 
		$('#if_btnDesc').attr('value',"");
		$('#if_btnType option').each(function(){
			$(this).attr('selected',false);
		});
		$('#if_btnIcon').attr('value',"");
		$('#if_btnAct').attr('value',"");

	}else if(type=="viewBtn"){
		$('#if_btnId').attr('value',btnId);
		$('#if_menuId').attr('value',menuId);
		$('#if_btnText').attr('value',btnText);
		$('#if_btnNickname').attr('value',btnNickname); 
		$('#if_btnDesc').attr('value',btnDesc);
		$('#if_btnType option').each(function(){
			var val = $(this).val()
			if( val== BtnType[btnType]) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_btnIcon').attr('value',btnIcon);
		$('#if_btnAct').attr('value',btnAct);
	
		$('#saveBtn').hide();
		disableForm('btnInfoFrom',true);
	}else if(type=="updateBtn"){	
		$('#if_btnId').attr('value',btnId);
		$('#if_menuId').attr('value',menuId);
		$('#if_btnText').attr('value',btnText);
		$('#if_btnNickname').attr('value',btnNickname); 
		$('#if_btnDesc').attr('value',btnDesc);
		$('#if_btnType option').each(function(){
			var val = $(this).val()
			if( val== BtnType[btnType]) {
				$(this).attr('selected', 'selected');
			}else { 
				$(this).attr('selected', false);
			}
		});
		$('#if_btnIcon').attr('value',btnIcon);
		$('#if_btnAct').attr('value',btnAct);
	}
});
/**
 * 接收返回信息
 * 
 */
function rtSlModal(){
	//校验数据
	if(!$("#btnInfoFrom").valid()){
		return;
	}
	var data = $('#btnInfoFrom').serialize();
	var url = "";
	if (type == "addBtn"){
		url = context + '/btn.do?method=addBtn'; 
	}else if(type == "updateBtn"){
		url = context + '/btn.do?method=updateBtn'; 
	}
	$.ajax({ 
		type: 'post', 
        url: url, 
        async : false,
        data: data, 
        success: function(msg){
        	if(msg.code=='1'){
        		alert("保存成功！"); //弹出成功msg
                //刷新表格数据
                //doSearch();
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
	$("#btnInfoFrom").validate({
		submitHandler:function(form){    
        } ,
		rules: {
			btnText: {required: true},
		},
		messages: {
			btnText: {required: '请输入按钮名称！'},
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