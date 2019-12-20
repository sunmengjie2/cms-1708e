<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	</nav>
	<div class="container">
		<div class="row justify-content-center" >
			<h3>${article.title}</h3>
		</div>
		<div class="row justify-content-center">
			<h5>
			作者：${article.user.username} &nbsp;&nbsp;&nbsp;
			栏目：${article.channel.name}  &nbsp;&nbsp;&nbsp;
			分类：${article.category.name}&nbsp;&nbsp;&nbsp;
			发表时间：<fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/> 
			</h5>
			
		</div>
		<div style="margin-top:30px">
			${article.content}
		</div>
		<div>
			<nav aria-label="...">
					  <ul class="pagination">
					    <li class="page-item ">
					      <input type="button" class="btn btn-primary" onclick="pagearticle(${article.id-1},${article.id })" value="上一篇">
					    </li>
					    <li class="page-item">
					      <input type="button" class="btn btn-primary" onclick="pagearticle(${article.id+1},${article.id })" value="下一篇">
					    </li>
					  </ul>
					</nav>
		</div>
		<div>
			<!-- 发布评论 -->
			<textarea rows="5" cols="160" id="commentText">
				
			</textarea>
			<input type="button" class="btn btn-primary" onclick="addComment()" value="发表评论">
		</div>
		<div id="comment">
			
		</div>
	</div>
</body>
<script type="text/javascript">

function gopage(page){
	showComment(page)
}
function showComment(page){
	$("#comment").load("/articles/comments?id=${article.id}&page="+page)
}

$(function(){
	// 显示第一页的评论
	showComment(1)
})

function addComment(){
	alert($("#commentText").val());
	
	 $.post("/articles/postcomment",
			{articleId:'${article.id}',content:$("#commentText").val()},
		function(msg){
			if(msg.code==1){
				alert('发布成功')
				$("#commentText").val("");
				showComment(1);
			}else{
				alert(msg.error)
			}
			
		},
		"json") 
}


function pagearticle(id,articleId) {
	$.post("/articles/pagearticle",{id:id,articleId:articleId},function(msg){
		if(msg.code==1){
			location="detail?id="+msg.data;
		}else{
			alert(msg.error)
		}
	},"json")
}
</script>
</html>