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
	    <th>时间</th>         
	    <th>操作</th>                          
    </tr>
  </thead>
  <tbody>
     <c:forEach items="${list }" var="l">
  <tr>
    <td>${l.id }</td>
    <td> <a href="${l.url }" target="_blank">${l.name }</a> </td>
    <td><fmt:formatDate value="${l.created }" pattern="yyyy年MM月dd日"/> </td>
    <td width="180px">
    	<input type="button" class="btn btn-danger" onclick="del(${l.id})" value="删除" />
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
		    <a class="page-link" href="#" onclick="gopage(${pageInfo.prePage==0?1:pageInfo.prePage})">上一页</a>
		    </li>
		   	<c:forEach begin="1" end="${pageInfo.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${pageInfo.nextPage==0?pageInfo.pages:pageInfo.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${pageInfo.pages})">尾页</a>
		    </li>
		  </ul>
	</nav>
	
	
<script type="text/javascript">
/**
* 翻页
*/
function gopage(page){
	$("#workcontent").load("collect?pageNum="+page);
}

/*
 * 删除
 */
 function del(id) {
	 alert(id)
		if(!confirm("您确认删除么？")){
			return;
		}
		
		
		$.post('/user/deleteCollect',{id:id},function(flag){
			if(flag){
				alert("刪除成功")
				//location.href="#"
				$("#workcontent").load("/user/collect");
			}else{
				alert("刪除失敗")
			}
		},"json")
}
 
</script>