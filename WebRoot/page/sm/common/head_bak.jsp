<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/public/fontAwesome/css/font-awesome-ie7.min.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/public/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/bootstrap/js/bootstrap.min.js"></script>

<div class="navbar navbar-fixed-top">
       <div class="navbar-sunline">
       <div class="header-in">
                <div class="logo">
                    <a href="javascript:void(0)">
                        <img src="<%=request.getContextPath()%>/page/sm/common/images/logo.png" >
                    </a>
                    <span>系统管理</span>
                </div>
                <div class="nav">
                <!--  
                    <ul>
                        <li><a href="javascript:void(0)"><i class="fa fa-home"></i> 首页</a></li>
                        <li><a href=""><i class="fa fa-database"></i> 元数据管理</a></li>
                        <li><a href="javascript:void(0)"><i class="fa fa-list-ol"></i> 数据质量</a></li>
                        <li><a href="javascript:void(0)"><i class="fa fa-ioxhost"></i> 数据标准</a></li>
                        <li class="curr"><a href="#sysMgr"><i class="fa fa-globe"></i> 系统管理</a></li>
                    </ul>
                    -->
                    <ul>
                    <li><a href="javascript:void(0)"><i class="fa fa-home"></i> 首页</a></li>
                    <c:if test="${activeUser.menudataInfoList!=null }">
                       <c:forEach items="${activeUser.menudataInfoList }" var="tmenuList">
                          <c:if test="${tmenuList.firstMenu != null}">
                            <li><a href="javascript:void(0)"><i class="fa fa-list-ol"></i> ${tmenuList.firstMenu.menuName }</a></li>
                            </c:if>
                       </c:forEach>
                    </c:if>
                    </ul>
                </div>
                <div class="wel" style="float: right;">
                    <ul>
                        <li>欢迎访问，<font color="red">${activeUser.username}</font> ！</li>
                        <li class="xg"><a href="javascript:void(0)">修改密码</a></li>
                         <li class="tc"><a href="<%=request.getContextPath()%>/logout.do">注销</a></li>
                    </ul>
                </div>
        </div>
       </div>
</div>