<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqueryLayout/css/layout-default.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/tree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/icheck/skins/all.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/metaPrivlg.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/sunlineUI/js/appClient.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/jqueryLayout/js/jquery-ui-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/jqueryLayout/js/jquery.layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>    
<script type="text/javascript" src="<%=request.getContextPath()%>/public/icheck/icheck.js"></script>  
	
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	var roleId = "<%=request.getParameter("roleId")%>";
</script>

</head>
<body>
	<div class="contentArea">
		<div class="ui-layout-west">
	 		<ul id="rolePrivlgTree" class="ztree"></ul>
	 	</div>
	 	<div class="ui-layout-center">
	 	   <div id="metaInfoTitle" class="alert">
	 	       请选择左侧元数据节点！
	 	   </div>
	 	   <div id="privlg_from">
	 	    <input type="checkbox" id="privAllCk" value="all"> 全选
	      	<hr style="border:1px dashed #E06363;margin:10px">
		 	   <ul>
		 		  <li><input id="md_view_btn" name="privlgbtn" value="view" type="checkbox"> 浏览</li>
	              <li><input id="md_add_btn" name="privlgbtn" value="add" type="checkbox"> 新增</li>
		 		  <li><input id="md_update_btn" name="privlgbtn" value="update" type="checkbox"> 修改</li>
		 		  <li><input id="md_delete_btn" name="privlgbtn" value="delete" type="checkbox"> 删除</li>
		 		  <li><input id="md_mapping_btn" name="privlgbtn" value="mapping" type="checkbox"> 维护映射关系</li>
	              <li><input id="md_version_btn" name="privlgbtn" value="version" type="checkbox"> 版本管理</li>
		 		  <li><input id="md_analysis_btn" name="privlgbtn" value="analysis" type="checkbox"> 影响分析</li>
		 		  <li><input id="md_blood_btn" name="privlgbtn" value="blood" type="checkbox"> 血统分析</li>
		 		  <li><input id="md_detail_btn" name="privlgbtn" value="detail" type="checkbox"> 查看详细</li>
		 		  <li><input id="md_favourite_btn" name="privlgbtn" value="favourite" type="checkbox"> 收藏</li>
		 		</ul>
	 	   </div>
	 	</div>
 	</div>	
 	<div>
 	  	<div class="form-actions" >
        	<button id="rolePrivlgBtn" class="button button-info ipt_button" onclick="rtCfgModal()"><i class="fa fa-save icolor"></i> 确 定</button>
            <button class="button button-info ipt_button" onclick="closWindow()" data-dismiss="modal"><i class="fa fa-close icolor"></i> 取 消</button>
      	</div>
 	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/role/js/roleMetaPrivlg.js"></script>
</html>