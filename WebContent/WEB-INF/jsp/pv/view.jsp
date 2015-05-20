<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="页面统计" scope="request"/>
<%request.setAttribute("_under"+request.getAttribute("module"), "active"); %>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
	<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>页面统计</h1></div>
		<div>
			<form class="form-inline pull-right" onsubmit="javascript:location.href='${basePath}pv/view/${module}/'+$('#day').val();return false;">
				<input id="day" name="day" value="${fn:escapeXml(day)}">
				<input type="submit" class="btn" value="展示">
			</form>
		</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div id="list">
			<c:choose>
				<c:when test="${!hasData}">
					没有数据可以展示!
				</c:when>
				
				
				<c:otherwise>
				 <div id="chart_div" style="width: 900px; height: 500px;"></div>
					<script type="text/javascript" src="${basePath}static/google/jsapi.js"></script>
				    <script type="text/javascript">
				      google.load("visualization", "1", {packages:["corechart"]});
				      google.setOnLoadCallback(drawChart);
				      function drawChart() {
				        var data = google.visualization.arrayToDataTable(${data});
				
				        var options = {
				          title: '${yName}',
				          hAxis: {title: '${xName}',  titleTextStyle: {color: 'red'}}
				        };
				
				        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
				        chart.draw(data, options);
				      }
				    </script>
				    <script src="${basePath}static/google/visual.js" type="text/javascript"></script>
				    <script src="${basePath}static/google/format.js" type="text/javascript"></script>
				</c:otherwise>
			</c:choose>
		</div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>