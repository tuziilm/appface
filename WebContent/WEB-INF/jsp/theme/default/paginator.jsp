<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:if test="${paginator.hasData}">
	<form id="paginator_form" method="post" style="display: none">
        <input type="hidden" name="debug" value="${param.debug}"/>
		<c:forEach var="item" items="${paginator.query.items}">
			<input type="hidden" name="${item.name}" value="${item.value}"/>
		</c:forEach>
	</form>
	<div id="paginator" class="pagination pagination-right">
          <ul>
            <c:if test="${paginator.needTotal}">
                <li><span>共 <em>${paginator.total}</em> 条记录</span></li>
            </c:if>
          	<c:choose>
          	<c:when test="${paginator.hasPrePage}">
            		<li><a href="javascript:gotoPage(${paginator.page-1})">«</a></li>
            	</c:when>
            	<c:otherwise>
            		<li class="disabled"><a href="javascript:void(0)">«</a></li>
            	</c:otherwise>
         	</c:choose>
         	<c:choose>
         		<c:when test="${paginator.page <=5 }">
         			<c:forEach var="curPage" begin="1" end="${paginator.page-1}">
         				<li><a href="javascript:gotoPage(${curPage})">${curPage}</a></li>
         			</c:forEach>
         			<li class="active"><a href="javascript:void(0)">${paginator.page}</a></li>
         		</c:when>
         		<c:otherwise>
         			<li><a href="javascript:gotoPage(1)">1</a></li>
         			<li><a href="javascript:gotoPage(2)">2</a></li>
         			<li><a href="javascript:void(0)">...</a></li>
         			<c:forEach var="curPage" begin="${paginator.page-2}" end="${paginator.page-1}">
         				<li><a href="javascript:gotoPage(${curPage})">${curPage}</a></li>
         			</c:forEach>
         			<li class="active"><a href="javascript:void(0)">${paginator.page}</a></li>
         		</c:otherwise>
         	</c:choose>
         	<c:choose>
          	<c:when test="${paginator.hasNextPage}">
            		<li><a href="javascript:gotoPage(${paginator.page+1})">${paginator.page+1}</a></li>
            		<li><a href="javascript:void(0)">...</a></li>
            		<li><a href="javascript:gotoPage(${paginator.page+1})">»</a></li>
            	</c:when>
            	<c:otherwise>
            		<li class="disabled"><a href="javascript:void(0)">»</a></li>
            	</c:otherwise>
         	</c:choose>
        </ul>
     </div>
     <script>
     	var paginator_form=document.getElementById("paginator_form");
     	function gotoPage(page){
     		paginator_form.action="${basePath}${paginator.path}${param.noSlash!=null?'':'/'}"+page;
     		paginator_form.submit();
     	}
     </script>
    </c:if>