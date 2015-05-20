<%@ page import="com.zhanghui.appface.common.Config" %>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="广告位置管理" scope="request"/>
<c:set var="_underAreaManage" value="active" scope="request"/>
<c:set var="_activeAd" value="active" scope="request"/>
<c:set var="_module" value="ad" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>广告位置管理</h1></div>
	<c:import url="../theme/${_theme}/name_search.jsp">
		<c:param name="action">${basePath}area_manage/list</c:param>
	</c:import>
    
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>位置名称</th>
							<th>广告类型</th>
							<th>位置排序</th>
							<th>位置类型</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td class="checkbox_td"><input type="checkbox" name="ids" value="${data.id}"/></td>
								<td>${fn:escapeXml(data.area)}</td>
								<td>
									<c:choose> 
							            <c:when test="${data.adType  eq '1'}">文字</c:when> 
							            <c:when test="${data.adType  eq '2'}">图片</c:when> 
									</c:choose>
								</td>
								<td>${fn:escapeXml(data.areaNum)}</td>
								<td>
									<c:choose> 
							            <c:when test="${data.areaType  eq '1'}">一行一列</c:when> 
							            <c:when test="${data.areaType  eq '2'}">一行二列</c:when> 
							            <c:when test="${data.areaType  eq '3'}">一行四列</c:when> 
							            <c:when test="${data.areaType  eq '4'}">带图标一行四列</c:when> 
							            <c:when test="${data.areaType  eq '5'}">带图标一行三列</c:when> 
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
			<div class="span4 toolbar">
				<c:import url="../theme/${_theme}/toolbar.jsp">
                    <c:param name="distribute">${basePath}area_manage/distribute</c:param>
                    <c:param name="create">${basePath}area_manage/create</c:param>
					<c:param name="delete">${basePath}area_manage/delete</c:param>
					<c:param name="modify">${basePath}area_manage/modify</c:param>
				</c:import>
			</div>
			<div class="span8 paginator">
				<c:import url="../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
