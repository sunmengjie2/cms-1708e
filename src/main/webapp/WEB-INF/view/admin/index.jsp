<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
</head>

<style>

	
	.ul2{
		background:red ;
	}
	
	.ul1 li:hover{
		background-color: #FFFFFF;
	}
	
	
</style>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light" style="background:#6600FF">
  <div class="collapse navbar-collapse" id="navbarSupportedContent" style="background:#6600FF">
    
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/css/img/dt1.gif"> </li>
    	
    		<li class="nav-item nav-link"></li>
    		<li class="nav-item nav-link">c</li>
    		<li class="nav-item nav-link"><a class="nav-link" href="/user/exit">退出</a></li>
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->

	<div class="row">
		<div class="col-2" style="height: 700px;width: 400px">  <!-- 左侧菜单 -->
		<nav class="navbar navbar-dark " style="margin-top: 20px;" >

			<!-- <a class="nav-link" href="#"><img alt="" src="/resource/css/img/222.jpg" width="80px" height="100px"></a> -->
			<ul class="nav flex-column ul1" style="height: 400px; " >
			  <li class="nav-item ">
			    <a  class="nav-link active" href="#" onclick="wordspace($(this),'/admin/articles?status=0&page=1')" >文章管理</a>
			  </li>
			  <li class="nav-item">
		 	   <a class="nav-link" href="#" onclick="wordspace($(this),'/admin/comment')" >评论管理</a>
		 	 </li>
		 	 <li class="nav-item">
		  	  <a class="nav-link" href="#" onclick="wordspace($(this),'/admin/link')" >友情链接</a>
		 	 </li>
		 	 <li class="nav-item">
		  	  <a class="nav-link" href="#" onclick="wordspace($(this),'/admin/user')" >用户管理</a>
		 	 </li>
			</ul>
		</nav>
		</div>
		
		<div class="col-10" id="workcontent"> 
		
		    
		</div>	
	</div>
	
<!-- 尾开始 -->
<nav class="nav fixed-bottom justify-content-center "  style="background:#6600FF" height="50px"> 
	         CMS  系统后台管理系统  版权所有 违者必奖 
</nav>

<script type="text/javascript">	
	
	function wordspace(obj,url) {
		$(".ul1 li").removeClass("ul2");
		obj.parent().addClass("ul2");
		
		$("#workcontent").load(url);
	}
</script>
</body>
</html>