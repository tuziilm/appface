<%@ include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" action="${basePath}link_node_access_log_statistics/list" method="post"
          class="form-inline pull-right">
        <label>链接:</label>
        <select name="code" id="code_sel" class="input-small">
            <option value="all">所有链接</option>
            <c:forEach items="${linkNodes}" var="ln">
                <option value="${ln.code}">${ln.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("code_sel").value = '${appface:defVal(param.code,"all")}';
        </script>
        <label>国家:</label>
        <select name="country" id="country_sel" class="input-small">
            <option value="all">所有国家</option>
            <c:forEach items="${countries}" var="c">
                <option value="${c.shortcut}">${c.chineseName}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("country_sel").value = '${appface:defVal(param.country,"all")}';
        </script>
        <label>客户:</label>
        <input value="${param.from}" type="text" name="from" class="input-small"/>
        <label>时间:</label>
        <input value="${appface:defVal(param.startTime,appface:yesterdayString("yyyy/MM/dd"))}" type="text" name="startTime" class="input-small" id="startTime"/>
        <input type="submit" class="btn" value="查询"/>
    </form>
</div>
