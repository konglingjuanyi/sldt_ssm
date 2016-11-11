<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/public/sunlineUI/js/appClient.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/jqueryLayout/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>

<!-- head 页面内容  -->
    <div class="header">
        <div class="header-in">
            <div class="header-left fl">
                <h1>
                    <a href="javacript:void(0)"><img src="<%=request.getContextPath()%>/public/sunlineUI/img/logo.png" alt="" /></a>
                </h1>
            </div>
            <div class="header-right fr">
                <div class="Hright-top clear">
                    <ul class="fr">
                        <li>欢迎访问，<font color="red"><shiro:principal/></font> ！</li>
                        <li><a class="modify" href="javascript:void(0)">修改密码</a></li>
                        <li><a class="cancellation" href="<%=request.getContextPath()%>/logout">注销</a></li>
                    </ul>
                </div>
                <div class="Hright-bottom clear">
                     <div class="headSearch radius6">
                        <form name="headSearch" method="post" action="" >
                            <input name='ecmsfrom' type='hidden' value='9'>
                            <input type="hidden" name="show" value="title,newstext">
                            <select name="classid" id="slchoose">
                                <option value="0">数据表</option>
                                <option value="1">数据流</option>
                            </select> 
                            <input class="inp_srh" id="searchDictName" name="keyboard"  placeholder="关键字" >
                            <input class="btn_srh" type="button"  onclick="queryDict()" value="">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="nav">
        <div class="nav-in">
            <ul>
            
                    <c:if test="${rootMenus.childs!=null }">
                       <c:forEach items="${rootMenus.childs }" var="rootM" varStatus="vs">
                          <c:if test="${vs.index==0}">
                            <li><a class="cur" id="rootMenu_${rootM.menuId}" href="javascript:void(0)" url="${rootM.menuUrl}">${rootM.menuName }</a></li>
                          </c:if>
                          <c:if test="${vs.index!=0}">
                          <li><a id="rootMenu_${rootM.menuId}" href="javascript:void(0)" url="${rootM.menuUrl}">${rootM.menuName }</a></li>
                           </c:if>
                       </c:forEach>
                    </c:if>
                 
                <shiro:hasPermission name="rootMenu:mds_00"> 
                <li><a class="cur" href="javascript:void(0)" url="/mds/index.jsp">首页</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="rootMenu:mds_01"> 
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/dmv/DataMapView.jsp">数据地图</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="rootMenu:mds_07">
				<li><a class="" href="javascript:void(0)" url="/ssm/page/sm/sysmgr/smContent.jsp">系统管理</a></li>
                </shiro:hasPermission>
                <!-- 
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/view/viewTree.jsp?viewId=ff808081509489410150949b6daa000a&viewName=数据地图">应用系统</a></li>
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/view/viewTree.jsp?viewId=ff808081506ffd7e015070094d9b0001&viewName=大数据应用">大数据平台</a></li>
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/view/viewTree.jsp?viewId=ff808081509489410150948d37ac0003&viewName=数据字典">UAT快照管理</a></li>
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/mdquery/mbContent.jsp">数据库对象查询</a></li>
                <li><a class="" href="javascript:void(0)" url="/../mds_mb/page/mb/home.jsp">统计分析</a></li>
				
                 -->  
            </ul>
        </div>
    </div>
