<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <link rel="stylesheet" type="text/css" href="/resource/css/index_work.css">
<form action="/admin/complain" method="post" id="form">
投诉类型：<select id="typ">
			<option value="0" ${com==0?'selected':'' }>全部</option>
			<option value="1" ${com==1?'selected':'' }>涉及黄色</option>
			<option value="2" ${com==2?'selected':'' }>涉及暴力</option>
			<option value="3" ${com==3?'selected':'' }>涉及违宗教政策</option>
			<option value="4" ${com==4?'selected':'' }>涉及国家安全</option>
			<option value="5" ${com==5?'selected':'' }>抄袭内容</option>
			<option value="5" ${com==6?'selected':'' }>其它</option>
		</select>
		次数大于<input type="text" name="t1" value="${t1 }"  />
		次数大于<input type="text" name="t2" value="${t2 }" />
		<input type="hidden" name = "dis"  id = "dis" value="${dis}" />
		<input type="button" value="查询"  onclick="selAll()"/>
<table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">编号</th>
      <th scope="col">标题</th>
      <th scope="col">文章内容</th>
      <th scope="col">投诉类型</th>
      <th scope="col" onclick="cs()">投诉次数</th>
      <th scope="col">投诉详情</th>
      <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
   	 <c:forEach items="${complainPage.list}" var="complain" varStatus="count">
    		<tr>
	   			<td>${count.count}</td>
	   			<td>${complain.article.title}</td>
	   			<td>${complain.article.content}</td>
	   			<td>
	   				<c:if test="${complain.complaintype==1}">涉及黄色</c:if>
	   				<c:if test="${complain.complaintype==2}">涉及暴力</c:if>
	   				<c:if test="${complain.complaintype==3}">涉及违宗教政策</c:if>
	   				<c:if test="${complain.complaintype==4}">涉及国家安全</c:if>
	   				<c:if test="${complain.complaintype==5}">抄袭内容</c:if>
	   				<c:if test="${complain.complaintype==6}">其它</c:if>
	   			</td>
	   			<td>${complain.article.complainCnt }</td>
	   			<td>
	   				<a href="/admin/xqing?userId=${complain.userId }">投诉详情</a>
	   			</td>
	   			<td>
	   				<c:if test="${complain.article.complainCnt }>50"><button>禁止</button></c:if>
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
		    <a class="page-link" href="#" onclick="gopage(${complainPage.prePage==0?1:complainPage.prePage})">上一页</a>
		    </li>
		   	<c:forEach begin="1" end="${complainPage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		   <li class="page-item">
		   <a class="page-link" href="#" onclick="gopage(${complainPage.nextPage==0?complainPage.pages:complainPage.nextPage})">下一页</a>
		   </li>
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage(${complainPage.pages})">尾页</a>
		    </li>
		  </ul>
	</nav>
</form>

<script type="text/javascript">
function selAll() {
	var com = $("#typ").val();
	var t1 =$("[name=t1]").val();
	var t2 =$("[name=t2]").val();
	//alert(com)
	//alert(t1) 
	//alert(t2)
	$("#workcontent").load("/admin/complain?pageNum=" + '${complainPage.pageNum}' + "&com="+com + "&t1="+t1 + "&t2="+t2)
} 

function cs() {
	var dis=$("#dis").val();
	if(dis=="acs"){
		$("#dis").val("desc")
	}else{
		$("#dis").val("acs")
	}
	//$("#form").submit();
}


/**
* 翻页
*/
function gopage(page){
	$("#workcontent").load("/admin/complain?pageNum="+page );
}

</script>
