<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="../../include/common.jsp"%>
<%@ page import="com.zhanghui.appface.common.LoginContext" %>
<%@ page import="com.zhanghui.appface.common.SystemUserType" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
    LoginContext.User user = (LoginContext.User)session.getAttribute(LoginContext.USER_SESSION_KEY);
    if(user == null || user.systemUserType != SystemUserType.ADMIN){
        response.sendRedirect("/login");
        return ;
    }
%>
<%!
    WebApplicationContext ctx = null;
%>
<html>
<head>
    <title>后台控制台</title>
</head>
<body>
