<%@page import="org.springframework.validation.BindingResult"%>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建广告" scope="request"/>
<c:set var="_underAdManage" value="active" scope="request"/>
<c:set var="_activeAd" value="active" scope="request"/>
<c:set var="_module" value="ad" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>新增广告</h1></div>
		<div id="pageContent">
			<c:import url="../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}ad_manage/save" method="post" class="form-horizontal" enctype="multipart/form-data">
				<input name="id" type="hidden" value="${form.id}">
				<input name="code" type="hidden" value="${form.code}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
				<div class="control-group required-field">
				  <label class="control-label">广告名称:</label>
				  <div class="controls">
				    <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">显示名称:</label>
				  <div class="controls">
				    <input name="showname" value="${fn:escapeXml(form.showname)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
					  <label class="control-label">广告分类:</label>
					  <div class="controls">
					  	<select id="adType_sel" name="type" class="input-large">
					  		<c:forEach var="adType" items="${adTypes}">
					  			<option value="${adType.id}" ${form.type==adType.id?'selected="selected"':"" }>${adType.name}</option>
					  		</c:forEach>
					  	</select>
					  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">广告图片:</label>
				  <div class="controls">
				    <input type="file" name="adPic" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">广告banner:</label>
				  <div class="controls">
				    <input type="file" name="adIcon" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">广告地址:</label>
				  <div class="controls">
				    <input name="link" value="${fn:escapeXml(form.link)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">备注:</label>
				  <div class="controls">
				    <input name="remark" value="${fn:escapeXml(form.remark)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="form-actions">
				  <input class="btn btn-primary" type="submit" value="保存">
				  <button type="button" class="btn" onclick="javascript:history.go(-1)">取消</button>
				</div>
			</form>
        </div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>