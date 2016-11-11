
$(document).ready(function(){
    //实例化编辑器
    var um = UM.getEditor('noticeEditor');
    
	validInit();
	if(type=="add"){
		$('#state').attr("disabled","disabled");
		$('#publishTime').attr("readonly","readonly");
		$('#publishUser').attr("readonly","readonly");
	}else if(type=="view"){
		$('#state').attr("disabled","disabled");
		$('#title').attr("readonly","readonly");
		$('#content').attr("readonly","readonly");
		$('#publishTime').attr("readonly","readonly");
		$('#publishUser').attr("readonly","readonly");
		$('#saveUpdateBtn').hide();
	}else if(type=="update"){	
		$('#state').attr("disabled","disabled");
		$('#publishTime').attr("readonly","readonly");
		$('#publishUser').attr("readonly","readonly");
	}
});

function save(){
	//校验数据
//	if(!$("#noticeInfoFrom").valid()){
//		return;
//	}
	var content=UM.getEditor('noticeEditor').getContent();
	var title=$("#title").val();
	var noticeId=$("#noticeId").val();
	var subTitle=$("#subTitle").val();
	var crtTime=$("#crtTime").val();
	var crtUser=$("#crtUser").val();
	var updTime=$("#updTime").val();
	var updUser=$("#updUser").val();
	if(title=="" || content=="" ){
		layer.msg('标题和公告内容都不可以为空！', {shift: 6});
		return false;
	}
//	var data = $('#noticeInfoFrom').serialize();
	var url = "";
	if(type == "add"){
		url = root + '/notice.do?method=addNotice';
	}else if (type == "update"){
		url = root + '/notice.do?method=updateNotice';
	}
	
	$.ajax({ 
		type: 'post', 
        url: url, 
        data: {
        	title:title,
        	content:content,
        	noticeId:noticeId,
        	subTitle:subTitle,
        	crtTime:crtTime,
        	crtUser:crtUser,
        	updTime:updTime,
        	updUser:updUser
        }, 
        success: function(msg){
	        if(msg.code=='1'){
	        	parent.layer.alert("保存成功！"); //弹出成功msg
	            //刷新表格数据
	        	parent.search();
	        	closWindow();
	        }else{
	        	parent.layer.alert(msg.message); //弹出失败msg
	        }
        },
        error:function(msg){
        	alert("访问服务器出错！");        
        }
	});
}
//调整分页图标
function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'fa fa-angle-double-left',
			'ui-icon-seek-prev' : 'fa fa-angle-left',
			'ui-icon-seek-next' : 'fa fa-angle-right',
			'ui-icon-seek-end' : 'fa fa-angle-double-right'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		});
}
/**
 * 校验
 */
function validInit(){
	
	validator =$("#noticeInfoFrom").validate({
		rules: {
			title:{
				required:true
			},
			content:{
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

/**
 * 关闭窗口
 */
function closWindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 parent.layer.close(index);
}

