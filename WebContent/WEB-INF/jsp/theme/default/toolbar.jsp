<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="toolbar_form" method="post" style="display: none">
	<c:forEach var="item" items="${query.items}">
		<input type="hidden" name="${item.name}" value="${item.value}"/>
	</c:forEach>
</form>
<c:if test="${param.distribute !=null}">
<input type="button" class="btn" onclick="javascript:doWithSingleItem('${param.distribute}')" name="_action_distribute" value="分配广告位置">
</c:if>
<c:if test="${param.addUpdatePkg !=null}">
<input type="button" class="btn" onclick="javascript:doWithNoneItem('${param.addUpdatePkg}');return false;" name="_action_add_update_pkg" value="创建">
</c:if>
<c:if test="${param.create !=null}">
<input onclick="javascript:location.href='${param.create}'" type="button" class="btn" name="_action_create" value="创建">
</c:if>
<c:if test="${param.modify !=null}">
<input type="button" class="btn" onclick="javascript:doWithSingleItem('${param.modify}')" name="_action_modify" value="修改">
</c:if>
<c:if test="${param.delete !=null}">
<input type="button" class="btn" onclick="javascript:doWithMultiItem('${param.delete}','请确认是否要删除？')" name="_action_delete" value="删除">
</c:if>
<c:if test="${param.typeDelete !=null}">
<input type="button" class="btn" onclick="javascript:doWithMultiItemAjax('${param.typeDelete}','请确认是否要删除？');return false;" name="_action_type_delete" value="删除">
</c:if>
<c:if test="${param.goBack !=null}">
<input type="button" class="btn" onclick="javascript:doGoBack()" name="_action_go_back" value="返回">
</c:if>
<script>
	var toolbar_form=document.getElementById("toolbar_form");
	function getIds(){
		var id="";
		var ids=document.getElementsByName("ids");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked)
				id+=ids[i].value+"-"; 
		}
		if(id!="")
			id=id.substring(0,id.length-1);
		return id;
	}
	function getId(){
		var id=-1;
		var ids=document.getElementsByName("ids");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				if(id==-1) 
					id=ids[i].value; 
				else 
					return -2;
			}
		} 
		return id;
	}
    function setQueryString(){
        var queryString="";
        $.each($("#search_form input"), function(i,param){
            if(param.type!="submit" && param.value!="")
                queryString+=param.name+"="+param.value+"&";
        });
        $.each($("#search_form select"), function(i,param){
            if(param.value!="")
                queryString+=param.name+"="+param.value+"&";
        });
        queryString+="_page="+$("#paginator .active a").html();
        $('<input />').attr('type', 'hidden')
                .attr('name', "queryString")
                .attr('value', encodeURI(queryString))
                .appendTo('#toolbar_form');
    }
    function selectSingleItems(){
        var id=getId();
        if(id==-1){
            alert("请选择相应的操作项！");
            return;
        }else if(id==-2){
            alert("只能选择一个操作项！");
            return;
        }
        return id;
    }
    function doWithNoneItem(path){
        toolbar_form.action=path;
        setQueryString();
        toolbar_form.submit();
    }
    function doWithSingleItem(path){
        var id=selectSingleItems();
        if(id){
            toolbar_form.action=path+"/"+id;
            setQueryString();
            toolbar_form.submit();
        }
    }
    function selectMultiItems(confirmMsg){
        var ids=getIds();
        if(ids==""){
            alert("请选择相应的操作项！");
            return;
        }
        if(confirmMsg && !confirm(confirmMsg)){
            return;
        }
        return ids;
    }
    function doWithMultiItem(path, confirmMsg){
        var ids=selectMultiItems(confirmMsg);
        if(ids){
            toolbar_form.action=path+"/"+ids;
            setQueryString();
            toolbar_form.submit();
        }
    }
    function doWithMultiItemAjax(path, confirmMsg){
        var ids=selectMultiItems(confirmMsg);
        if(ids){
            var request = $.ajax({
                type : "POST",
                url : path+"/"+id,
                data : {}
            });
            request.done(function(msg) {
                var result=eval(msg);
                if(result.success){
                    gotoPage($("#paginator .active a").html());
                }else{
                    alert(result.msg);
                }
            });
            request.fail(function(msg) {
                alert("服务器繁忙，请稍后再试!");
            });
        }
    }
</script>
<c:if test="${param.goBack !=null}">
<script>
function doGoBack(){
	var param="${param.goBack}";
	if(param=="true"){
		history.go(-1);
	}else{
		location.href=param;
	}
}
</script>
</c:if>