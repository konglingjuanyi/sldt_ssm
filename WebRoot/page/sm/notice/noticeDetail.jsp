<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告维护</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/public/jquery-validation/css/screen.css"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/public/sunlineUI/css/sunline.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/cgrid.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/zTree.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/common.css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/page/sm/common/css/system.css"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/js/jquery.ztree.core-3.5.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery-validation/localization/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqgrid/js/i18n/grid.locale-cn.js"></script>
   
<link href="<%=request.getContextPath()%>/public/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/public/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/public/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/umeditor/lang/zh-cn/zh-cn.js"></script>

<style type="text/css">
table{width:100%;}
td {
    font-size: 14px;
    font-weight: normal;
    height: 40px;
    color: #333;
    font-family: "宋体";
}
td:nth-of-type(odd){text-align: right;}
</style>

</head>
<body>
        <form id="noticeInfoFrom" class="form-horizontal">
        <input type="hidden"  id="noticeId" name="noticeId" value="${notice.noticeId }"/>
        <input type="hidden"  id="subTitle" name="subTitle" value="${notice.subTitle }"/>
        <input type="hidden"  id="crtTime" name="crtTime" value="${notice.crtTime }"/>
        <input type="hidden"  id="crtUser" name="crtUser" value="${notice.crtUser }"/>
        <input type="hidden"  id="updTime" name="updTime" value="${notice.updTime }"/>
        <input type="hidden"  id="updUser" name="updUser" value="${notice.updUser }"/>
        <table style="margin-top: 15px;">
           <tr>
               <td width="10%" >标题<font color="red">*</font>:</td><td width="40%">     	
               <input type="text" class="input-xlarge"  id="title" name="title" value="${notice.title}" placeholder="请输入排序次号">
               </td>
               <td width="10%" >状态:</td><td width="40%">
               <select class="input-xlarge" id="state" name="state" >
                	<option value="1" <c:if test="${notice.state=='1' }">selected="selected"</c:if> >草稿</option>
                	<option value="2" <c:if test="${notice.state=='2' }">selected="selected"</c:if> >发布</option>
                	<option value="3" <c:if test="${notice.state=='3' }">selected="selected"</c:if> >废弃</option>
               </select> </td>
           </tr>
           <tr>
               <td width="10%" >发布人:</td><td width="40%">     	
               <input type="text" class="input-xlarge" readonly="readonly"  id="publishUser" name="publishUser" value="${notice.publishUser}">
               </td>
               <td width="10%" >发布时间:</td><td width="40%"><input type="text" class="input-xlarge" readonly="readonly" id="publishTime" name="publishTime" value="${notice.publishTime}"> </td>
           </tr>
        </table>
        <script type="text/plain" id="noticeEditor" style="width:960px;height:95%;">${notice.content }</script>   
        </form>          
     <div>
       <div style="height: 50px"></div>
       <div class="form-actions" align="center">
            <button id="saveUpdateBtn" class="button button-info" onclick="save()"><i class="fa fa-save icolor"></i> 确 定</button>
            <button id="cancelBtn" class="button button-info" onclick="closWindow()" ><i class="fa fa-close icolor"></i> 取 消</button>
        </div>
     </div>   
</body>

<script type="text/javascript">
var type = '<%=request.getParameter("type")%>';
var root = '<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/page/sm/notice/js/noticeDetail.js"></script>   

</html>