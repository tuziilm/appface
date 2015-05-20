<%@page import="org.springframework.validation.BindingResult"%>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="分配广告位置" scope="request"/>
<c:set var="_underAreaManage" value="active" scope="request"/>
<c:set var="_activeAd" value="active" scope="request"/>
<c:set var="_module" value="ad" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>分配广告位置</h1></div>
		<div id="pageContent">
			<c:import url="../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}area_manage/distributeSave" method="post" class="form-horizontal">
				<input type="hidden" id="id" name="id" value="${areaId}">
				<input type="hidden" id="areaNum" name="areaNum" value="${areaNum}">
				<!-- 根据行列生成表单 -->
				<c:forEach begin="1" end="${areaType}" varStatus="status">
					<div class="control-group required-field">
					  <label class="control-label">广告类别:</label>
					  <div class="controls">
					  	<select id="adType_sel${status.index}" name="adTypeIds" class="input-large"  onchange="javascript:loadAd(${status.index})">
					  		<c:forEach var="adType" items="${adTypes}">
					  			<option value="${adType.id}" ${distributeArea.adTypeIdsObject[status.index-1]==adType.id?'selected="selected"':"" }>${adType.name}</option>
					  		</c:forEach>
					  	</select>
					  </div>
					</div>	
					<div class="control-group required-field">
					  <label class="control-label">广告名称:</label>
					  <div class="controls">
					  	<select id="ad_sel${status.index}" name="adIds" class="input-large">
					  	</select>
					  </div>
					</div>				
				</c:forEach>
				<div class="form-actions">
				  <input class="btn btn-primary" type="submit" value="确认">
				  <button type="button" class="btn" onclick="javascript:history.go(-1)">取消</button>
				</div>
			</form>
        </div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
<script>
	$(document).ready(function(){
		<c:forEach begin="1" end="${areaType}" varStatus="status">
			loadAd(${status.index});
			$("#ad_sel${status.index}").val(${distributeArea.adIdsObject[status.index-1]});
		</c:forEach>
	});
	var ads = {};
	<c:forEach var="ad" items="${ads}">
		if(ads[${ad.type}]==undefined){
			ads[${ad.type}]=[];
		}
	</c:forEach>
	<c:forEach var="ad" items="${ads}">
		if(ads[${ad.type}]!=undefined){
			ads[${ad.type}].push({"name":"${ad.name}","id":${ad.id}});
		}
	</c:forEach>
	//级联加载广告
	function loadAd(id){
		var adTypeId=document.getElementById("adType_sel"+id).value;
		var items = ads[adTypeId];
		var sel = document.getElementById("ad_sel"+id);
		sel.innerHTML="";
		if(items!=undefined){
			var idx = 0;
			for(;idx< items.length;idx++){
				sel.add(new Option(items[idx].name, items[idx].id));
			}
		}
	}
</script>