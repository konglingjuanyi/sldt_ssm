var validator=null;
$(document).ready(function(){
	validInit();
});


function validInit(){
	
	validator =$("form").validate({
		rules: {
			kncatName:{required:true,rangelength:[1,20]},
	        focusCleanup:true 
	    },
	    errorPlacement : function(error, element) {
	        	 error.appendTo(element.parent());  
	    },
	    errorElement: "span"
	});

}
//由于校验的原因 table 不使用fade 动态效果
function valid(){
	if($('form').valid()){
			return true;
	}
	return false;
}


function saveKnoCat(){
	if(valid()){
	var data = $("form").serialize();
	if(option=="add"){	
		$.ajax({ 
			type: 'post', 
	        url: context + '/kno.do?method=addKnoCat', 
	        data: data, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
		        	//刷新树
		        //	parent.getParentWindow().doReflashNode();
		        	//刷新表格数据
		        	closWindow();
		        }else{
		        	parent.layer.alert(msg); //弹出失败msg
		        }
	        },
	        error:function(msg){
	        	alert("访问服务器出错！");        
	        }
		});
	}else if(option=="update"){
		$.ajax({ 
			type: 'post', 
	        url: context + '/kno.do?method=updateKnoCat', 
	        data: data, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
		            //刷新表格数据
		     //   	parent.getParentWindow().doReflashNode();
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
	parent.search();
	parent.layer.close(index);
}
function openNewWindow(){

}

