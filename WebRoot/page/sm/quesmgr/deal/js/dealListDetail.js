var validator=null;
$(document).ready(function(){
	validInit();
});

$(document).ready(function() {
	if(title!="detail"){
		if(quesJjState=="未解决" || quesJjState=="解决方案未通过"){
			$('#quesSolution').removeAttr("readonly");		
		}
	
		if(quesJjState=="已解决" || quesJjState =="解决中" ||quesJjState=="已解决审核未通过"){
			$('#quesJjResult').removeAttr("readonly");
			$('#quesJjDate').removeAttr("readonly");		
		}
	}
	
});


function validInit(){	
	validator =$("#quesForm").validate({
		rules: {
			quesJjDate:{
				required:true
			},

	        focusCleanup:true 
	    },
	    errorPlacement : function(error, element) {
	        	 error.appendTo(element.parent());  
	    },
	    errorElement: "span"
	});

}

function valid(){
	if($('#quesForm').valid()){
			return true;
	}
	return false;
}
function save(){
	if(valid()){
	var data = $("#quesForm").serialize();
	
		$.ajax({ 
			type: 'post', 
	        url: context + '/dealAction.do?method=update', 
	        data: data, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
		            //刷新表格数据
		        	parent.search();
		        	closWindow();
		        }else{
		        	parent.layer.alert(msg); //弹出失败msg
		        }
	        },
	        error:function(msg){
	        	parent.layer.alert("访问服务器出错！");        
	        }
		});
}
	
}

/**
 * 将未定义的字符转换成空字符串
 * @param str
 */
function getIsEmptyStr(str){
	if(str == null || str == undefined) return "";
	return str;
}
/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引	
	parent.layer.close(index);
}






