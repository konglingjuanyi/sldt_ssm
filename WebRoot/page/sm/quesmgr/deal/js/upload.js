var validator=null;
$(document).ready(function(){
	validInit();
//	initSelectVal();
});
function validInit(){
	
	validator =$("#fileuploadForm").validate({
		rules: {
			fileName:{
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
	if($('#fileuploadForm').valid()){
			return true;
	}
	return false;
}



/**
 * 关闭窗口
 */
function closWindow(){
	
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

/**
 * 提交表单数据
 * @param el
 * @param method
 */
function save(el){
	if(valid()){
	var options = {
			
		url:context + '/dealAction.do?method=uploadFile',
		enctype:"multipart/form-data", 
		type:'post',
		dataType:"json",
		processData: false,
		success:function(msg){
			if(msg.code=='1'){
				alert("文件上传成功！"); //弹出成功msg
				parent.search();//刷新表格数据
				closWindow();
			}else{
				alert(msg.message); //弹出失败msg
		    }
		},
		error:function(msg){
			alert("访问服务器出错！");        
		}
	};
    // bind form using 'ajaxForm'
    $('#fileuploadForm').ajaxForm(options);
	$('#fileuploadForm').submit();
}
}