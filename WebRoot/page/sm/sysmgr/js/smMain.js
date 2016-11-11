var mainTab;
$(document).ready(function(){
	//布局设置
	var myLayout = $('body').layout({
		west__minSize:280,	
		west__resizable:true
	});
	
    mainTab = $('#homeTab').sunlineTab({
    	maxSize:14,
    	defaultClose:true,//默认可以关闭
    	step_rang:60 //移动量，默认20，单位px
    });  
    mainTab.sunlineTab('initTabs');
    
    //监听窗口变化，调整插件的宽度
    var resizeDelay = 800;
    var doResize = true;
    var resizer = function () {
       if (doResize) {
    	   mainTab.sunlineTab('changeTabSyle');
           doResize = false;
       }
     };
     var resizerInterval = setInterval(resizer, resizeDelay);
     resizer();
     $(window).resize(function() {
       doResize = true;
     });
     
    //调整首页高度
    var contentAreaH = $("#contentArea").height();
    var homeTabH = $("#homeTab").height();
    var fH = contentAreaH - homeTabH-30;
    $("#home_f").height(fH);
});

/**
 * 创建并展示新的tab标签界面
 * @returns
 */
function addTabPg(tabId,tabUrl,tabTitle,tabIcon){
	/*var tabIconClass ="fa fa-database";
	if(tabIcon!=null && tabIcon!=""){
		tabIconClass = tabIcon;
	}*/
	if(tabUrl.indexOf('http:')>=0){
		
	}else if(tabUrl.indexOf('page/sm')>=0){
		tabUrl = context+app_sm+"/"+tabUrl;
	}else if(tabUrl.indexOf('page/activiti')>=0){
		tabUrl = context+app_sm+"/"+tabUrl;
	}else if(tabUrl.indexOf('page/')>=0){
		tabUrl = context+app_mb+"/"+tabUrl;
	}else{
		tabUrl = context+app_mb+"/page/mb/"+tabUrl;
	}
	if((!$("#" + tabId)[0]) )//是否已经存在
	{
		//var tabs = $("#homeTab").children();
		if(mainTab.sunlineTab('getCurrSize')>15){
			layer.msg("选项卡太多，请先关闭部分选项卡在进行操作！",{shift:6});
			return false;
		}
		mainTab.sunlineTab('addTab',tabId,tabUrl,tabTitle,tabIcon);
		/*$('.nav-tabs').append('<li><a href="#' + tabId + '"><i class="'+tabIconClass+'"></i> '+tabTitle+'&nbsp;&nbsp; <i class="'+tabCloseClass+'" ></i></a></li>');
		$('.tab-content').append('<div class="tab-pane" id="' + tabId + '"></div>');
		//craeteNewTabAndLoadContent(treeNode, tabId);
		if(tabUrl && tabUrl!=""){
			 $("#" + tabId).html("<div style='tab_top'><iframe frameborder='0' height='500px' scrolling='yes' width='100%' src='"+context+"/page/mb/"+tabUrl+"'></iframe></div>");
		}
		$(this).tab('show');
		showTab(tabPanelId,tabId);
		registerCloseEvent(tabPanelId,tabCloseClass);*/
	}else{//激活点击的
		//showTab(tabPanelId,tabId);
		mainTab.sunlineTab('showTab',tabId);
	}
}

function openWin(url,type,width,height){
	width = width == "" || width === undefined || width == null ? "620px" : width;
	height = height == "" || height === undefined || height == null ? "90%" : height;
	var title="";
	if(type == "addRole"){
		title = "添加角色";
	}
	if(type == "viewRole"){
		title = "查看角色";
	}
	if(type == "updateRole"){
		title = "修改角色";
	}
	if(type == "rolePrivlgModal"){
		title = "角色权限配置";
	}
	if(type == "addUser"){
		title = "添加用户";
	}
	if(type == "updateUser"){
		title = "修改用户";
	}
	if(type == "viewUser"){
		title = "用户信息查看";
	}
	if(type == "userPrivlgModal"){
		title = "权限配置";
	}
	if(type == "metaPrivlgModal"){
		title = "元数据权限配置";
	}
	if(type == "userRoleModal"){
		title = "角色配置";
	}
	if(type == "addPrivCate"){
		title = "查看权限类别";
	}
	if(type == "viewPrivCate"){
		title = "添加权限类别";
	}
	if(type == "updatePrivCate"){
		title = "修改权限类别";
	}
	if(type == "addPriv"){
		title = "添加权限";
	}
	if(type == "viewPriv"){
		title = "查看权限";
	}
	if(type == "updatePriv"){
		title = "修改权限";
	}
	if(type == "addMenu"){
		title = "添加菜单";
	}
	if(type == "viewMenu"){
		title = "查看菜单";
	}
	if(type == "updateMenu"){
		title = "修改菜单";
	}
	if(type == "menuPrivlgModal"){
		title = "权限配置";
	}
	if(type == "addBtn"){
		title = "添加按钮";
	}
	if(type == "viewBtn"){
		title = "查看按钮";
	}
	if(type == "updateBtn"){
		title = "修改按钮";
	}
	if(type == "btnPrivlgModal"){
		title = "权限配置";
	}
	/*add by liumeng*/
	if(type == "addModel"){
		title = "添加模型";
	}
	if(type == "addDefinition"){
		title = "流程定义配置";
	}
	if(type == "delegateUser"){
		title = "指定任务处理人";
	}
	layer.open({
		type:2,
		title:title,
		shadeClose: false,
		maxmin: true, //开启最大化最小化按钮
	    shade: 0.8,
	    area: [width, height],
	    content: url
	});
}
function search(){
	var activeTabId  ;
	var tabs = $("#homeTab").children();
	for(var i=0;i<tabs.length;i++){
		if($(tabs[i]).hasClass('active')){
			activeTabId = $(tabs[i]).find("a").attr("href").substring(1);
		};
	}
	activeTabId = activeTabId+"_f";
	frames[activeTabId].window.doSearch();
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