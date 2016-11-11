<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/icheck/skins/all.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/jquery-validation/css/screen.css"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/system.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/sunlineUI/js/appClient.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/icheck/icheck.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
<style type="text/css">
	form.cmxform label.error, label.error{
		margin-left: 160px;
	}
</style> 
</head>
<body>
        <form id="menuInfoFrom" class="form-horizontal">
            <input type="text" style="display: none;" id="if_menuId" name="menuId" value=""/>
            <div class="control-group">
                <label class="control-label" >菜单类型<font color="red">*</font>：</label>
                <div class="controls">
                    <select id="menuType" name="menuType" class="span3 formInput" style="width: 120px;">
						<option value="01">系统菜单</option>
						<option value="02">功能菜单</option>
						<option value="03" selected>业务菜单</option>
				    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputEmail">菜单名称<font color="red">*</font>：</label>
                <div class="controls">
                    <input class="input-xlarge" id ="if_menuName" name="menuName" type="text" id="inputEmail" placeholder="请输入菜单名称">
                </div>
            </div>
            
            <div class="control-group">
               <label class="control-label">子系统：</label>
               <div class="controls">
                   <input class="input-medium" id="appId" name="appId" type="text" value="" style="display:none">
                   <input class="input-medium" id="appName" name="appName" type="text" readonly onclick="showSys()">
               </div>
            </div>
            
            <div class="control-group">
                <label class="control-label" >父级菜单编号：</label>
                <div class="controls">
                	<input id="pMenuId" name="pMenuId" type="hidden">
                    <input class="input-xlarge" id ="if_pMenuId" name="if_pMenuId" value="" type="text" readonly onclick="showMenu();"  placeholder="请选择父级菜单">
                </div>
            </div>
            <!-- 
            <div class="control-group">
                <label class="control-label">菜单层级<font color="red">*</font>：</label>
                <div class="controls">
                    <input class="input-xlarge" id ="if_menuLevel" name="menuLevel" value="" type="text" placeholder="请输入菜单层级">
                </div>
            </div>
             -->
            <div class="control-group">
                <label class="control-label" >菜单归属：</label>
                <div class="controls">
                   <input type="radio" name="belong"  id="belong_1" checked> 普通菜单
                   <input type="radio" name="belong"  id="belong_2"> 报表链接
                   <input type="radio" name="belong"  id="belong_3"> 检索类型
                </div>
            </div>
            <div id="menuUrlInfo"  class="control-group">
                <label class="control-label" >菜单路径：</label>
                <div class="controls">
                    <input class="input-xlarge" id ="if_menuUrl" name="menuUrl" value="" type="text" placeholder="请输入菜单路径">
                </div>
            </div>
            <div id="ReportInfo" class="control-group" style="display:none;">
                <label class="control-label">报表元数据(ID)：</label>
                <div class="controls">
                   <input class="input-xlarge" id ="if_menuParam2" name="menuParam2" value="" readonly onclick="showReportTree();" type="text" placeholder="请选择报表">
                </div>
            </div>
           <div id="metaClassInfo" class="control-group" style="display:none;">
                <label class="control-label">类型(ClassID)：</label>
                <div class="controls">
                   <input class="input-xlarge" id ="if_menuParam3" name="menuParam3" value="" type="text" readonly onclick="showSearchClsTree();"  placeholder="请输入ClassID">
                </div>
            </div>
            
            <div class="control-group">
                <label class="control-label" >排序次号<font color="red">*</font>：</label>
                <div class="controls">
                    <input class="input-xlarge" id ="if_menuOrder" name="menuOrder" value="" type="text" placeholder="请输入排序次号" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')">
                </div>
            </div>
           
            <div class="control-group">
                <label class="control-label" >菜单描述：</label>
                <div class="controls">
                    <textarea class="form-control" rows="5" id ="if_menuDesc" name="menuDesc" value="" style="width:50%"></textarea>
                </div>
            </div>
           
        </form>
            <div id="menuContent" class="treeContent" style="display:none; position: absolute;">
                <ul id="clsTree" class="ztree" style="width:287px;"></ul>
            </div>
     <div>
       <div style="height: 50px"></div>
       <div class="form-actions" align="center">
            <button id="saveUpdateBtn" class="button button-info ipt_button" onclick="rtSlModal()"><i class="fa fa-save icolor"></i> 确 定</button>
            <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" data-dismiss="modal"><i class="fa fa-close icolor"></i> 取 消</button>
        </div>
     </div> 
     
     <ul id="reportMetaTree" class="ztree repTreeContent" style="display:none;position: absolute;"></ul> 
     <ul id="searchClsTree" class="ztree repTreeContent" style="display:none;position: absolute;"></ul> 
</body>
<div id="system" class="classMappingTb" style="display:none; position: absolute;">
  <table id="system-table"></table>
  <div id="system-pager"></div>
</div>
<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var root = '<%=request.getContextPath()%>';
var menuId = "<%=request.getParameter("menuId")== null ? "" :new String(request.getParameter("menuId")) %>";
var menuName = "<%=request.getParameter("menuName")==null?"":URLDecoder.decode(request.getParameter("menuName"), "UTF-8")%>";
var menuDesc = "<%=request.getParameter("menuDesc")==null?"":URLDecoder.decode(request.getParameter("menuDesc"), "UTF-8")%>";
var menuLevel = "<%=request.getParameter("menuLevel")== null ? "" :new String(request.getParameter("menuLevel")) %>";
var menuUrl = "<%=request.getParameter("menuUrl")== null ? "" :new String(request.getParameter("menuUrl")) %>";
var menuOrder = "<%=request.getParameter("menuOrder")== null ? "" :new String(request.getParameter("menuOrder")) %>";
var pMenuId = "<%=request.getParameter("pMenuId")== null ? "" :new String(request.getParameter("pMenuId")) %>";
var appId = "<%=request.getParameter("appId")== null ? "" :new String(request.getParameter("appId")) %>";
var pMenuName = "<%=request.getParameter("pMenuName")==null?"":URLDecoder.decode(request.getParameter("pMenuName"), "UTF-8")%>";
var menuType = "<%=request.getParameter("menuType")== null ? "" :new String(request.getParameter("menuType")) %>";
var menuParam2 = "<%=request.getParameter("menuParam2")== null ? "" :new String(request.getParameter("menuParam2")) %>";
var menuParam3 = "<%=request.getParameter("menuParam3")== null ? "" :new String(request.getParameter("menuParam3")) %>";
	menuUrl = menuUrl.replace('$','http://');
	menuUrl = menuUrl.replace(/\@/g,"&");
	menuUrl = menuUrl.replace(/\*/g,"?");
	menuDesc =menuDesc.replace(/\@/g,'\n');
var appName = "<%=request.getParameter("appName")==null?"":URLDecoder.decode(request.getParameter("appName"), "UTF-8")%>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/menu/js/addOrUpdateMenu.js"></script>   

</html>