<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach items="${commentPage.list}" var="comment">
	<div class="row" style="margin-top:50px">
		${comment.content}
		<br>
		<br>
		${comment.userName}    	 发表于<fmt:formatDate value="${comment.created}" pattern="yyyy-MM-dd" />
		</div> 
</c:forEach>  

<div class="row"> 
	<nav aria-label="Page navigation example" >
					  <ul class="pagination ">
					  
					    <li class="page-item">
					      <a class="page-link" href="javascript:gopage(${commentPage.pageNum-1})" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					    
					    <c:forEach begin="1" end="${commentPage.pages}" varStatus="index">
					    	
					    	<!-- 当前页码的处理 -->
					    	<c:if test="${commentPage.pageNum==index.index}">
					    		<li class="page-item"><a class="page-link" href="javascript:void()"><font color="red"> ${index.index} </font></a>  </li>
					  		</c:if>
					  		
					  		<!-- 非当前页码的处理 -->
							<c:if test="${commentPage.pageNum!=index.index}">
					    		<li class="page-item"><a class="page-link" href="javascript:gopage(${index.index})"> ${index.index}</a></li>
					  		</c:if>
					  
					    </c:forEach>
					    
					    <li class="page-item">
					      <a class="page-link" href="javascript:gopage(${commentPage.pageNum+1})" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					    
					  </ul>
					</nav>
			<!-- 分页结束 -->
</div>






					