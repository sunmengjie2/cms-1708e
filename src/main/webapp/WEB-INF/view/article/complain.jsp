<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉</title>
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js" ></script>
<link href="/resource/bootstrap-4.3.1/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
</head>
<body>
	<div class="container">
	<form:form  modelAttribute="complain" 
	action="/articles/complain" method="post" enctype="multipart/form-data">
		<div class="form-group">
		   <label >文章标题</label>${article.title}
		  </div>
		<input type="hidden" name="articleId" value="${article.id}">
		
		
		  <div class="form-group">
		   <label >投诉类型</label>
		   		&nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="1"> 涉及黄色
		   		  &nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="2">涉及暴力
		   		  &nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="3"> 涉及违宗教政策
		   		   &nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="4"> 涉及国家安全
		   		    &nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="5"> 抄袭内容
		   		     &nbsp;&nbsp;&nbsp;<input type="checkbox" name="complaintype" value="6"> 其它
		 	 <form:errors path="complaintype" cssStyle="color:red"></form:errors>
		  </div>
		  
		  <div class="form-group">
		   <label >地址</label>
		    <form:input path="urlip" />
		    <form:errors path="urlip" cssStyle="color:red"></form:errors>
		 </div>
		  		  
		<button>提交</button>
	</form:form>	
	
	</div>
</body>
</html>