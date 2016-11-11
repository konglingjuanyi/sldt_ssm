var validator=null;
$(document).ready(function(){
	validInit();
	getKnCatOption();
});


function validInit(){
	
	validator =$("#knoCatsForm").validate({
		rules: {
			kncatName:{required:true,rangelength:[1,20]},
			kncntId:{required:true},
			kncatId:{required:true},
			focusCleanup:true 
	    },
	    errorPlacement : function(error, element) {
	    	 /*if ( element.is(":radio") ){
		        	error.appendTo( element.parent().find(".tipinfo") );
		        }else if ( element.is(":checkbox") ){
		        	error.appendTo ( element.parent().find(".tipinfo") );
		        }else{
		        	 error.appendTo(element.parent());  
		        }*/
		    
	        	 error.appendTo(element.parent());  
	    },
	    errorElement: "span"
	});

}
//由于校验的原因 table 不使用fade 动态效果
function valid(){
	if($('#knoCatsForm').valid()){
			return true;
	}
	return false;
}


function saveKnoCat(){
	if(valid()){
	var data = $("#knoCatsForm").serialize();
	if(option=="add"){	
		$.ajax({ 
			type: 'post', 
	        url: context + '/kno.do?method=addKnoContent', 
	        data: data, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
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
	        url: context + '/kno.do?method=updateKnoContent', 
	        data: data, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
		            //刷新表格数据
		        	//parent.search();
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
function getKnCatOption(){
	$.ajax({ 
		type: 'post', 
        url: context + '/kno.do?method=getKnoCatOption', 
        data: "", 
        success: function(msg){
	        if(msg.length<1){
	        	alert("获取下拉框失败");
	        }else{
	        	setOption($("#kncatId"),msg);
	        }
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
	});
}
function setOption(obj,optionArr){
	var options="";
	 for(var i=0;i<optionArr.length;i++){
		 if(id==optionArr[i].kncatId){
			 options+='<option selected="selected" value="'+optionArr[i].kncatId+'">'+optionArr[i].kncatName+'</option>';
		 }else{
			 options+='<option  value="'+optionArr[i].kncatId+'">'+optionArr[i].kncatName+'</option>';
		 }
		
	 }
	 $(obj).html(options);
	 if(option=="update"){
		 $(obj).val(kncatId);
 	}
}
