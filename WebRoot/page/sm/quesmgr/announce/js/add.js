var validator=null;
$(document).ready(function(){
	validInit();
});
function validInit(){	
	validator =$("#saveFrom").validate({
		rules: {
			quesDesc:{
				required:true
			},
			influence:{
				required:true				
			},
			aboutSys:{
				required:true				
			},
			aboutDept:{
				required:true				
			},
			quesPerson:{
				required:true				
			},
			quesType:{
				required:true				
			},
			quesDate:{
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



/*$(document).ready(function(){
	if(idtype=='add')
	{
		$("#fb").hide();
	}
});*/



function valid(){
	if($('#saveFrom').valid()){
			return true;
	}
	return false;
}
function saveQues(){
	if(valid()){
	var form=$("#saveFrom").serialize();
	//alert(form);
	if(idtype=='update'){	
		$.ajax({ 
			type: 'post', 
	        url: context + '/announce.do?method=update', 
	        data: form, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("更新成功！"); //弹出成功msg
		            //刷新表格数据
		        	parent.search();
		        	closWindow();
		        }else{
		        	parent.layer.alert(msg); //弹出失败msg
		        }
	        },
	        error:function(msg){
	        	alert("更新出问题了！");        
	        }
		});		
	}else if(idtype=="add"){
		$.ajax({ 
			type: 'post', 
	        url: context + '/announce.do?method=add', 
	        data: form, 
	        success: function(msg){
		        if(msg==''){
		        	parent.layer.msg("保存成功！"); //弹出成功msg
		        	parent.search();
		            //刷新表格数据
		        	closWindow();
		        }else{
		        	parent.layer.alert(msg); //弹出失败msg
		        }
	        },
	        error:function(msg){
	        	alert("保存出问题了！");        
	        }
		});		
		
	}
	}
}


	
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}