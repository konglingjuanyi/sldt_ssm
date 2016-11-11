var slfn = "";
var selects;
var classOption = "";
$(document).ready(function() {
	iFrameHeight('mainArea');
	 var $style = $('<style type="text/css"> @font-face{font-family:"FontAwesome";src:url("<%=request.getContextPath()%>/public/fontAwesome/fonts/fontawesome-webfont.eot?v=4.2.0");font-weight:normal;font-style:normal}</style>');
	  $('head').append($style);
	/*$(".sidebar-menu").find("a").click(function() {
		if($(this).attr('url')) $('#mainArea').attr("src",$(this).attr('url'));  
	});*/
	
	$(".nav-in").find("a").click(function() {
		$(".nav-in").find("a").removeClass("cur");
		$(this).attr("class","cur");
		if($(this).attr('url')) {
			var tabUrl = $(this).attr('url');
			/*if(tabUrl.indexOf('page/sm')>=0){
				tabUrl = context+app_sm+"/"+tabUrl;
			}else if(tabUrl.indexOf('page/')>=0){
				tabUrl = context+"/"+tabUrl;
			}else{
				tabUrl = context+"/page/mb/"+tabUrl;
			}*/
			$('#mainArea').attr("src",tabUrl);  
		}
	});
	
    selects=$('#slchoose');//获取select
	for(var i=0;i<selects.length;i++){
		createSelect(selects[i],i);
	}
});


function queryDict(){
	var searchDictName = $('#searchDictName').val();
	if(searchDictName=='' || searchDictName==null){
		return false;
	}
	var searchDictType = '1';
	if(classOption=='数据表'){
		searchDictType = '1';
	}else if(classOption=='数据流'){
		searchDictType = '2';
	}
	$(".nav-in").find("a").removeClass("cur");
	$("#rootMenu_mds_05").attr("class","cur");
	$('#mainArea').attr("src",context+app_mb+"/page/mb/mdquery/mbContent.jsp?searchType="+searchDictType+"&searchName="+searchDictName);  
	return false;
}

function iFrameHeight(iframeId) {  
	var ifm= document.getElementById(iframeId);
	if(ifm){
	    //alert($(window).height());
		if(ifm.src.indexOf("/index/indexContent.jsp")>1){
			ifm.height = $(window).height()+70;
			return;
		}
		 ifm.height = $(window).height()-100;  
	}
}

function createSelect(select_container,index){

		//创建select容器，class为select_box，插入到select标签前
		var tag_select=$('<div></div>');//div相当于select标签
		tag_select.attr('class','select_box');
		tag_select.insertBefore(select_container);

		//显示框class为select_showbox,插入到创建的tag_select中
		var select_showbox=$('<div></div>');//显示框
		select_showbox.css('cursor','pointer').attr('class','select_showbox').appendTo(tag_select);

		//创建option容器，class为select_option，插入到创建的tag_select中
		var ul_option=$('<ul></ul>');//创建option列表
		ul_option.attr('class','select_option');
		ul_option.appendTo(tag_select);
		createOptions(index,ul_option);//创建option

		//点击显示框
		tag_select.toggle(function(){
			ul_option.show();
			ul_option.parent().find(".select_showbox").addClass("active");
		},function(){
			ul_option.hide();
			ul_option.parent().find(".select_showbox").removeClass("active");
		});

		var li_option=ul_option.find('li');
		li_option.on('click',function(){
			$(this).addClass('selected').siblings().removeClass('selected');
			var value=$(this).text();
			select_showbox.text(value);
			classOption = value;
			ul_option.hide();
		});

		li_option.hover(function(){
			$(this).addClass('hover').siblings().removeClass('hover');	
		},function(){
			li_option.removeClass('hover');
		});

}

function createOptions(index,ul_list){
		//获取被选中的元素并将其值赋值到显示框中
		var options=selects.eq(index).find('option'),
			selected_option=options.filter(':selected'),
			selected_index=selected_option.index(),
			showbox=ul_list.prev();
		classOption = selected_option.text();
			showbox.text(selected_option.text());
		
		//为每个option建立个li并赋值
		for(var n=0;n<options.length;n++){
			var tag_option=$('<li></li>'),//li相当于option
				txt_option=options.eq(n).text();
			tag_option.text(txt_option).appendTo(ul_list);

			//为被选中的元素添加class为selected
			if(n==selected_index){
				tag_option.attr('class','selected');
			}
		}
}




function showSlModal(tid,el,fn){
	$('#slModal').html(el);
	$('#'+tid).modal('show');
	slfn = fn;
}

function slModalFn(){
	var slModalHtml = $('#slModal').html();
	mainArea.window.rtSlModal(slModalHtml,slfn);
}

function cfgModalFn(){
	rolePrivlgWin.window.rtCfgModal(slfn);
}

function cfgRoleModalFn(){
	userRoleWin.window.rtCfgModal(slfn);
}

function cfgPrivlgModalFn(){
	userPrivlgWin.window.rtCfgModal(slfn);
}

function cfgBtnPrivlgModalFn(){
	btnPrivlgWin.window.rtCfgModal(slfn);
}

function cfgMenuPrivlgModalFn(){
	menuPrivlgWin.window.rtCfgModal(slfn);
}

function showMenu(){
	mainArea.window.showMenu();
}

function openWin(url,type){
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
	layer.open({
		type:2,
		title:title,
		shadeClose: false,
		maxmin: true, //开启最大化最小化按钮
	    shade: 0.8,
	    area: ['620px', '90%'],
	    content: url
	});
}
function search(){
parent.frames["mainArea"].window.doSearch();
}
