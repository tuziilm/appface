<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="search">
	<form id="search_form" action="${param.action}" method="post" class="form-inline pull-right">
	<label>${not empty param.label? param.label :'名称'}:</label>
	<input type="text" name="name" value="${fn:escapeXml(param.name)}"/>
	<input type="submit" class="btn" value="查询"/>
	</form>
</div>