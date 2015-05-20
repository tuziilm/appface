<%@ page import="com.zhanghui.appface.common.Config" %>
<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="链接PV展示" scope="request"/>
<c:set var="_underLinkNodeAccessLogStatisticsShowLine" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>链接PV展示</h1></div>
	<c:import url="search_chart.jsp">
	</c:import>
    <form style="display: none" id="link_node_form" action="${basePath}link_node_access_log_statistics/show_line" method="POST">
    </form>
	<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
		
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
<script src="${basePath}static/jquery/jquery-ui.js"></script>
<script src="${basePath}static/jquery/jquery.ui.datepicker-zh-TW.js"></script>
<script src="${basePath}static/jquery/highcharts.js"></script>
<script src="${basePath}static/jquery/exporting.js"></script>

<script>
    $(function () {
    	var str = eval("("+'${jsonStr}'+")");
        $( "#startTime" ).datepicker( {
	        	onClose: function( selectedDate ) {
	                $( "#endTime" ).datepicker( "option", "minDate", selectedDate );
	              }} );
        $( "#endTime" ).datepicker( {
        	onClose: function( selectedDate ) {
                $( "#startTime" ).datepicker( "option", "maxDate", selectedDate );
              }} );
        $('#container').highcharts({
            title: {
                text: 'PV/UV统计',
                x: -20 //center
            },
            xAxis: {
                categories: str.date
            },
            yAxis: {
                title: {
                    text: '数量（次）'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '次',
                crosshairs: true,
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: str.series
        });
    });
</script>
