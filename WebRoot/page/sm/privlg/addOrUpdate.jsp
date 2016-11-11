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
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/jquery-validation/css/screen.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />


<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
</head>
<body>
            <form id="PrivInfoFrom" class="form-horizontal">
              <input type="text" style="display: none;" id="priv_id" name="privid" value=""/>
              <div class="control-group">
                <label class="control-label" for="priv_name">权限名称<font color="red">*</font>：</label>
                <div class="controls">
                  <input class="input-xlarge" id ="priv_name" name="privname" type="text"  placeholder="权限名称">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" >权限描述：</label>
                <div class="controls">
                  <input class="input-xlarge" id ="priv_desc" name="privdesc" value="lslslsls" type="text" placeholder="权限描述">
                </div>
              </div>
              
              <div class="control-group">
                <label class="control-label" >父权限：</label>
                <div class="controls">
   					
    				 <input class="input-xlarge" id ="parent_priv" name="parentpriv" value="" type="text" readonly onclick="showMenu();"  placeholder="请选择父级菜单">
				</div>
                <!-- <div class="controls">
                  <select id="parent_priv" name="parentpriv" class="span3"></select>
                </div> -->
             </div>
              
              <div class="control-group" style="display:none">
                <label class="control-label" >权限类型：</label>
                <div class="controls">
                  <select id="priv_type" name="privtype" class="span3">
                    <!-- <option value="1">BIEE内置权限组</option> -->
                    <option value="2">web权限组</option>
                  </select>
                </div>
             </div>
             <div class="control-group">
                <label class="control-label" >权限类别<font color="red">*</font>：</label>
                <div class="controls">
                  <select id="cate_id" name="cateid" class="span3">
                    <option value="">--选择权限类别--</option>
                  </select>
                </div>
             </div>
             
             <div class="control-group">
                <label class="control-label" >状态：</label>
                <div class="controls">
                  <select id="privstat_id" name="privstat" class="span3">
                    <option value="0">下架</option>
                    <option value="1">上架</option>
                  </select>
                </div>
             </div>
             
              <div class="control-group">
                <label class="control-label" >备 注：</label>
                <div class="controls">
                  <textarea rows="4" id="priv_memo" name="memo"  placeholder="请输入备注"></textarea>
                </div>
             </div>
            </form>
       <div>
          <div style="height: 50px"></div>
          <div class="form-actions" align="center" >
            <button id="saveUpdateBtn" class="button button-info ipt_button" onclick="rtSlModal()"><i class="fa fa-save icolor"></i> 确 定</button>
            <button id="cancelBtn" class="button button-info ipt_button" onclick="closWindow()" data-dismiss="modal"><i class="fa fa-close icolor"></i> 取 消</button>
          </div>
       </div>
       <div id="menuContent" class="treeContent" style="display:none; position: absolute;">
                <ul id="clsTree" class="ztree" style="width:287px;"></ul>
       </div>
</body>
<script type="text/javascript">
var context = "<%=request.getContextPath()%>";
var type = '<%=request.getParameter("type")%>';
var privId = "<%=request.getParameter("privId")== null ? "" :new String(request.getParameter("privId")) %>";
var privName = "<%=request.getParameter("privName")==null?"":URLDecoder.decode(request.getParameter("privName"), "UTF-8")%>";
var privDesc = "<%=request.getParameter("privDesc")==null?"":URLDecoder.decode(request.getParameter("privDesc"), "UTF-8")%>";
var pPrivId = "<%=request.getParameter("pPrivId")== null ? "" :new String(request.getParameter("pPrivId")) %>";
var privType = "<%=request.getParameter("privType")==null?"":URLDecoder.decode(request.getParameter("privType"), "UTF-8")%>";
var cateId = "<%=request.getParameter("cateId")== null ? "" :URLDecoder.decode(request.getParameter("cateId"), "UTF-8") %>";
var privStat = "<%=request.getParameter("privStat")==null?"":URLDecoder.decode(request.getParameter("privStat"), "UTF-8")%>";
var memo = "<%=request.getParameter("memo")==null?"":URLDecoder.decode(request.getParameter("memo"), "UTF-8")%>";

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/privlg/addOrUpdate.js"></script>   
</html>