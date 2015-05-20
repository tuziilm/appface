<%@include file="../include/common.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet"
	href="${basePath}static/nav/styles/main.css?rnd=20140708143234">
<link rel="shortcut icon" href="favicon.ico"
	type="${basePath}static/image/x-icon">
			<script type="text/javascript" src="http://www.google.com/coop/cse/brand?form=cse-search-box&amp;lang=en"></script>			
</head>
<body>
	<!--  导航页统计 -->
	<img
		src="${basePath}callback/link_node/lnc-1fd15760afea4fca8aa4c312b48b80c3?from=${from}"
		style="display: none">
	<!-- end 导航页统计 -->
	<div id="h" class="cf">
		<div id="h-i">
		<!-- 
			<form action="http://m.home.hypersonica.com/search/web/" class="s-f" method="get">
                <input type="hidden" name="vend" value="hv02006302">
				<div class="s-out">
					<input type="search" value="" autocomplete="off" maxlength="2048" autocorrect="off" spellcheck="false" name="q" id="J_HomeSe" class="bxz s-i J_SeIpt">
					<div class="s-in">
						<button class="s-go" type="submit"></button>
						<i class="s-cl J_HomeSe_Clear"></i>
					</div>
				</div>
			</form>
		 -->				
			<form action="http://www.google.com" id="cse-search-box" class="s-f" method="get">
			  <div class="s-out">
			    <input type="hidden" name="cx" value="partner-pub-6164962530989681:7979406113" />
			    <input type="hidden" name="ie" value="UTF-8" />
			    <input type="text" name="q" size="30" id="J_HomeSe" class="bxz s-i J_SeIpt"/>
			    <div class="s-in">
					<input class="s-go" type="submit" value=""></input>
					<i class="s-cl J_HomeSe_Clear"></i>
				</div>
			  </div>
			</form>
						
			
		</div>
		<div id="bg"
			style="background-image: url(${basePath}static/nav/images/bg.jpg);">
			<div id="bgs"></div>
		</div>
	</div>
	  <c:forEach var="distributeArea" items="${distributeAreas}">
		<c:if
			test="${areas[distributeArea.id].areaType eq 0}">
			<div class="picad">
			   <c:forEach var="adId" items="${distributeArea.adIdsObject}">
					<a href="${basePath}callback/link_node/${ads[adId].code}?from=${from}" class="n-l"><i
						class="n-l" style="background:url(${basePath}public/file/${ads[adId].banner});background-origin:content;
						background-position:50%100%;background-size:contain;background-repeat:no-repeat;"></i></a>
				</c:forEach>
			</div>
		</c:if>
	  </c:forEach>	
	<div id="m" class="cf" style="min-height: 395px;">
			<c:forEach begin="1" end="${picSize}" varStatus="status">
				<c:forEach var="distributeArea" items="${distributeAreas}">
					<c:if
						test="${areas[distributeArea.id].areaNum eq status.index && areas[distributeArea.id].areaType eq 4}">
						<div class="n">
							<c:forEach var="adId" items="${distributeArea.adIdsObject}">
								<a href="${basePath}callback/link_node/${ads[adId].code}?from=${from}" class="n-i"><i
									class="n-ico" style="background:url(${basePath}public/file/${ads[adId].picture})"></i><br>${ads[adId].showname}</a>
							</c:forEach>
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>
			<div style="height:1px"></div>
			<c:forEach begin="1" end="${iconSize}" varStatus="status">
				<c:forEach var="distributeArea" items="${distributeAreas}">
					<c:if
						test="${areas[distributeArea.id].areaNum eq status.index && areas[distributeArea.id].areaType eq 5 }">
						<div class="n">
							<c:forEach var="adId" items="${distributeArea.adIdsObject}">
								<a href="${basePath}callback/link_node/${ads[adId].code}?from=${from}" class="n-i"><i
									class="n-ico" style="background:url(${basePath}public/file/${ads[adId].picture})"></i><br>${ads[adId].showname}</a>
							</c:forEach>
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>	
</body>