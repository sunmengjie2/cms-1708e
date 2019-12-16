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
<title>注册专属</title>
<link rel="stylesheet" type="text/css" href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
	
	<!-- 插入样式 -->
	<style type="text/css">
		body {
		 width: 80%;
			margin: 40px auto;
			font-family: 'trebuchet MS', 'Lucida sans', Arial;
			font-size: 14px;
			color: #444;
			background: url("/resource/css/img/a1.jpg");
			background-repeat: no-repeat;
		 	background-size:cover
		  }
		  
		.form{background: rgba(255,255,255,0.2);width:400px;margin:100px auto;}
	</style>
</head>
<body>
<!-- 注册 -->
<form:form action="register" method="post" min="2" max="8" id="form" modelAttribute="user">	
<div class="form row">  
	
	<h2 class="form-title">Register your username</h2>	
			<h4>物急必反，和气为贵</h4>
			<div class="col-sm-9 col-md-9">					
				<div class="form-group">
					<i class="fa fa-user fa-lg">用户名</i>						
					<form:input class="form-control required" path="username" 
						placeholder="输入用户名" remote="checkname" />
					<form:errors path="username" ></form:errors>					
				</div>					
				<div class="form-group">							
					<i class="fa fa-lock fa-lg">密码</i>							
					<form:password class="form-control required" path="password"  placeholder="输入你的密码" />
					<form:errors path="password"></form:errors>					
				</div>					
				<div class="form-group">						
					<input type="submit" class="btn btn-primary"  value="注册"/>
					<a href="login">登录页面</a>
				</div>				
			</div>	
		
</div>
</form:form>	
<script type="text/javascript">
	 $("#form").validate();
	function add() {
		$("#form").valid();
	} 
</script>
</body>
</html>