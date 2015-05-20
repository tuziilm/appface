<%@page import="org.springframework.validation.BindingResult"%>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建统计链接" scope="request"/>
<c:set var="_underLinkNode" value="active" scope="request"/>
<c:set var="_activeLink" value="active" scope="request"/>
<c:set var="_module" value="linkNode" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>创建/修改统计链接</h1></div>
		<div id="pageContent">
			<c:import url="../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}link_node/save" method="post" class="form-horizontal" enctype="multipart/form-data">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
				<div class="control-group required-field">
				  <label class="control-label">名称:</label>
				  <div class="controls">
				    <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
				  </div>
				</div>
                <div class="control-group required-field">
                    <label class="control-label">链接:</label>
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