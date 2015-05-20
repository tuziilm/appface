<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${_module=='linkNode' }">
        <li class="${_underLinkNode}"><a href="${basePath}link_node/list">链接</a></li>
    </c:when>
    <c:when test="${_module=='ad' }">
        <li class="${_underAdType}"><a href="${basePath}ad_type/list">广告分类管理</a></li>
        <li class="${_underAdManage}"><a href="${basePath}ad_manage/list">广告管理</a></li>
        <li class="${_underAreaManage}"><a href="${basePath}area_manage/list">广告位置管理</a></li>
    </c:when>
	<c:when test="${_module=='system' }">
		<c:if test="${appface:isAdmin()}">
			<li class="${_underSysUser}"><a href="${basePath}sysuser/list">系统用户</a></li>
		</c:if>
		<li class="${_underUserInfo}"><a href="${basePath}sysuser/${isUnderUserInfo?'info_modify':'modify'}/${appface:uid()}">信息修改</a></li>
	</c:when>
	<c:when test="${_module=='statistics' }">
        <li class="${_underLinkNodeAccessLogStatistics}"><a href="${basePath}link_node_access_log_statistics/list">链接PV/UV</a></li>
        <li class="${_underLinkNodeAccessLogStatisticsShowLine}"><a href="${basePath}link_node_access_log_statistics/chart">链接PV展示</a></li>
	</c:when>
</c:choose>