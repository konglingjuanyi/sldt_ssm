<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/zTree.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript">
	var context = '<%=request.getContextPath()%>';
	var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pId"
				}
			},
			async: {
				enable: true, //异步加载
		        url: context + "/menu.do?method=dropDownTree",
		        autoParam: ["id"] //自动提交的一个参数
			},
			callback: {
				onClick: onClick
			}
		};
	
	function onClick(event, treeId, treeNode, clickFlag){
		var zTree = $.fn.zTree.getZTreeObj("clsTree");
		var nodes = zTree.getSelectedNodes();
		var value = "";
		nodes.sort(function compare(a,b){
			return a.id-b.id;
		});
		for (var i=0, l=nodes.length; i<l; i++) {
			value += nodes[i].name + ",";
		}
		if (value.length > 0 ) {
			value = value.substring(0, value.length-1);
		}
		var menuObj = $("#if_pMenuId");
		menuObj.attr("value", value);
	}
	
	var zTree;
	function showMenu() {
		if(!zTree){//只加载一次
			//要放在这里处理，不然在层后面点击没反应（事件失效），但是这样子每次都会请求一次
			zTree = $.fn.zTree.init($("#clsTree"), setting);
		}
		//$.fn.zTree.init($("#clsTree"), setting, zNodes);
		var cityObj = $("#if_pMenuId");
		var cityOffset = $("#if_pMenuId").offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
</script>
</head>
<body>
<div id="menuModal" class="modal hide fade">
	<input id ="if_pMenuId" name="pMenuId" value="" type="text" onclick="showMenu(); return false;">
	<div id="menuContent" class="hide">
		<ul id="clsTree" class="ztree" style="margin-top:-8px; margin-left:0px; width:270px; height: 250px; z-index: 110;"></ul>
	</div>
</div>
<button type="button button-info ipt_button" data-toggle="modal" data-target="#menuModal">弹出</button>
</body>

</html>