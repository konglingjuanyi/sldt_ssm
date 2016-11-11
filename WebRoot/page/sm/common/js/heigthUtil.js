/**
 * 调整jqgrid表格高度，自适应浏览器高度
 * 兼容性调整
 * 20160308
 */
/**
 * jqgrid适应IE8问题<br>
 * IE8前Array对象没有indexOf方法，导致jqgrid在调用addRowData方法报错<br>
 * 这里为Array对象添加IndexOf方法
 */
(function(){
	if (!Array.prototype.indexOf)
	{
	    Array.prototype.indexOf = function(elt /*, from*/)
	    {    
	        var len = this.length >>> 0;
	        var from = Number(arguments[1]) || 0;
	        from = (from < 0)
	         ? Math.ceil(from)
	        : Math.floor(from);
	         if (from < 0)
	             from += len;
	        for (; from < len; from++)
	         {
	            if (from in this &&
	            this[from] === elt)
	             return from;
	        }
	    return -1;
	    };
	}
})();
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
	var gridHeigth = ifHeight-gridOffsetTop-112;//表格高度，这里要多减去一段距离，用于放分页标签相关的内容
	gridHeigth = gridHeigth>=200?gridHeigth:200;//设置一个最小高度，放置浏览器显示区域过低的情况，表格高度太小
	return gridHeigth;
}
/**
 * 获取浏览器高度（不包括工具栏/滚动条），适用所有浏览器<br>
 * 对于Internet Explorer、Chrome、Firefox、Opera 以及 Safari：window.innerHeight <br>
 * 对于 Internet Explorer 8、7、6、5：document.documentElement.clientHeight
 */
function getWindowHeight(){
	var h=window.innerHeight
	|| document.documentElement.clientHeight
	|| document.body.clientHeight;
	return h;
}
/**
 * 获取当前表格所在的IFrame对象
 * @param f iframe元素
 */
function getSelfIframe(f){
	var frames = document.getElementsByTagName("iframe"); //获取父页面所有iframe
	for(i=0;i<frames.length;i++){ //遍历，匹配时弹出id
		if(frames[i].contentWindow == f){
				return frames[i];
			}
	}
	return null;
}

