var fwkUtil={
	// 调整分页图标
	 updatePagerIcons:function(table) {
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
		})
	},
	/**
	 * 宽度+1像素
	 * 解决在chrome浏览器下出现水平滚动条
	 */
	removeHorizontalScrollBar:function(tableId) {

		$("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width", parseInt($("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width")) + 1 + "px");
		$(tableId).closest(".ui-jqgrid-bdiv").css("width", parseInt($(tableId).closest(".ui-jqgrid-bdiv").css("width")) + 1 + "px");
		
	},
	/**
	 * 获取动态下拉数据
	 * id select id
	 * url 请求地址
	 * data 请求数据
	 */
	getSelectUtil:function (id,url,data){
		$.ajax({ 
			type: 'post', 
	        url: url, 
	        data: data,
	        async:false,
	        success: function(data){
		        if(data!=null && data.length!=0){
		        	for(var i=0;i<data.length;i++){
		        		var str="<option value='"+data[i].value+"'>"+data[i].text+"</option>";
			        	$("#"+id).append(str);
		        	}
		        }
	        },
	        error:function(msg){
	        	parent.layer.alert("访问服务器出错！");        
	        }
		});
	},
	/**
	 * 获取当前日期
	 * @param format 日期格式（支持格式：yyyyMMdd,yyyy-MM-dd,yyyy/MM/dd,yyyy-MM-dd hh:mm:ss）
	 * @returns {String}
	 */
	getCurentDate:function(format){
		//当前时间
	    var now = new Date();
	    //年
	    var year = now.getFullYear();
	    //月
	    var month = now.getMonth() + 1;
	    month = month<10?"0"+month:month;
	    //日
	    var day = now.getDate();
	    day = day<10?"0"+day:day;
	    //时
	    var hh = now.getHours();
	    hh = hh<10?"0"+hh:hh;
	    //分
	    var mm = now.getMinutes();
	    mm = mm<10?"0"+mm:mm;
	    //秒
	    var ss = now.getSeconds();
	    ss = ss<10?"0"+ss:ss;
	    
	    if(format == "yyyyMMdd"){
	    	return year + month + day;
	    }else if(format == "yyyy-MM-dd"){
	    	return year + "-" + month + "-" + day;
	    }else if(format == "yyyy/MM/dd"){
	    	return year + "/" + month + "/" + day;
	    }else if(format == "yyyy-MM-dd hh:mm:ss"){
	    	return year + "-" + month + "-" + day + " " + hh + ":" + mm + ":" + ss;
	    }
	} 
}

/**
 * 获取表格自适应高度
 * 页面中不存在jquery的布局
 */
function getGridHeight(gridId){
	//获取当前窗口iframe对象
	var ifram = window.parent.getSelfIframe(this);
	//获取当前表格距离iframe顶部偏移量
	var gridOffsetTop = document.getElementById(gridId).offsetTop;
	var ifHeight = ifram.height.replace("px","");
	var gridHeigth = ifHeight-gridOffsetTop-100;//表格高度，这里要多减去一段距离，用于放分页标签相关的内容
	gridHeigth = gridHeigth>=200?gridHeigth:200;//设置一个最小高度，放置浏览器显示区域过低的情况，表格高度太小
	return gridHeigth;
}
