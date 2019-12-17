<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<table class="table" style="width: 1100px;margin-top: 30px;margin-left: 20px">
  <thead>
    <tr>
	    <th>id</th>
	    <th>标题</th>             
	    <th>栏目</th>             
	    <th>分类</th>             
	    <th>发布时间</th>         
	    <th>状态</th>             
	    <th>操作</th>                          
    </tr>
  </thead>
  <tbody>
     <c:forEach items="${articlesPage.list }" var="articles">
  <tr>
    <td>${articles.id }</td>
    <td>${articles.title }</td>
    <td>${articles.channel.name }</td>
    <td>${articles.category.name }</td>
    <td><fmt:formatDate value="${articles.created }" pattern="yyyy年MM月dd日"/> </td>
    <td>
    	<c:choose>
    		<c:when test="${articles.status==0 }">待审核</c:when>
    		<c:when test="${articles.status==1 }">已审核</c:when>
    		<c:when test="${articles.status==2 }">审核失败</c:when>
    		<c:otherwise>未知</c:otherwise>
    	</c:choose>
    </td>
    <td width="180px">
    	<input type="button" class="btn btn-danger" onclick="del(${articles.id})" value="删除" />
    	<input type="button" class="btn btn-info" onclick="update(${articles.id})" value="修改" />
    </td>
  </tr>
  </c:forEach>
  </tbody>
</table>


	<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item">
		      <a class="page-link" href="#"  onclick="gopage(1)">首页</a>
		    </li>
		    <li class="page-item">
		    <a class="page-link" href="#" onclick="gopage(${articlesPage.prePage==0?1:articlesPage.prePage})">上一页</a>
		    </li>
		   	<c:forEach begin="1" end="${articlesPage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${articlesPage.nextPage==0?articlesPage.pages:articlesPage.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${articlesPage.pages})">尾页</a>
		    </li>
		  </ul>
	</nav>

<script type="text/javascript">

/**
* 翻页
*/
function gopage(page){
	$("#workcontent").load("articles?pageNum="+page);
}

//删除
function del(id){
	alert(id)
	if(!confirm("您确认删除么？")){
		return;
	}
	
	
	$.post('/user/deletearticle',{id:id},function(flag){
		if(flag==true){
			alert("刪除成功")
			//location.href="#"
			$("#workcontent").load("/user/articles");
		}else{
			alert("刪除失敗")
		}
	},"json")
}
 
//修改
function update(id) {
	
	$("#workcontent").load("/user/updateArticles?id="+id)
}

</script>
