<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String unexpiredTgts = "";
	  unexpiredTgts = com.sldt.dmc.ssm.online.statics.util.OnlineStaticsUtil.getOnlinePersons();
%>

当前在线人数为：<%=unexpiredTgts %>
