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
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>


	<link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>
	

</head>
<style>

	
	.ul2{
		background:red ;
	}
	
	.ul1 li:hover{
		background-color: #FFFFFF;
	}
	
	
</style>

<script type="text/javascript">
	function wordspace(obj,url) {
		$(".ul1 li").removeClass("ul2");
		obj.parent().addClass("ul2");
		
		$("#workcontent").load(url);
	}
	
	KindEditor.ready(function(K) {
		window.editor1 = K.create();
		prettyPrint();
	});
	
</script>

<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    <div>
    	<ul class="nav" >
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/pic/${user.url }"> </li>
    		
    		<li class="nav-item nav-link"><font color="red">${user.nickname }</font></li>
    		
    		<!-- <li class="nav-item nav-link">c</li> -->
    		<li class="nav-item nav-link"> <a class="nav-link" href="exit">退出</a></li>
    	</ul>
    </div>
  </div>
</nav>  <!-- 头部结束  -->

<div class="container row">
	<div class="col-md-2" style="height: 750px">  <!-- 左侧菜单 -->
	<nav class="navbar navbar-dark " style="margin-top: 20px;background-color: #333333;" >

		<!-- <a class="nav-link" href="#"><img alt="" src="/resource/css/img/222.jpg" width="80px" height="100px"></a> -->
		<ul class="nav flex-column ul1" style="height: 500px; width:300px; line-height:30px;" >
			<li class="nav-item">
				    <a class="nav-link" href="/">首　　页</a>
			</li>
			<li class="nav-item" style="background-color: ">
			    <a id="postLink" class="nav-link active" href="#" onclick="wordspace($(this),'/user/articles')">我的文章</a>
			</li>
			  
		    <li class="nav-item">
			    <a  class="nav-link" href="#" onclick="wordspace($(this),'/user/postArticle')">发布文章</a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="wordspace($(this),'/user/collect')">我的收藏夹</a>
			</li>
			<li class="nav-item">
			    <a class="nav-link" href="#" onclick="wordspace($(this),'/user/comments')">消息中心</a>
			</li>
			<li class="nav-item">
			    <a class="nav-link disabled" href="#" tabindex="-1" onclick="wordspace($(this),'/user/personal')">个人设置</a>
			</li>
		</ul>
	</nav>
	</div>
	
	<!-- 主体部分 -->
	<div class="col-md-10" id="workcontent"> 
			
	</div>	

</div>


<!-- 尾部开始  -->
<nav class="navbar nav fixed-bottom justify-content-center" style="background-color: #00FFFF;">
  伟大的事业不是靠力气速度和身体的敏捷完成的，而是靠性格意志和知识的力量完成的。
</nav>
</body>
</html>