<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul id="errors">
<c:if test="${errors!=null}">
			<c:forEach var="error" items="${errors.allErrors}">
				<li>${error.defaultMessage}</li>
			</c:forEach>
</c:if>
</ul>
<c:if test="${msg!=null}">
	<ul id="msg">
		<li>${msg}</li>
	</ul>
</c:if>