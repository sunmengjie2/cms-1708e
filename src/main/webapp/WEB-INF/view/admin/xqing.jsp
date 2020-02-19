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
<link rel="stylesheet" type="text/css" href="<%=path %>/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="<%=path %>/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
</head>
<body>
	<table>
  <tr>
    <td>姓名</td>
    <td>${user.username }</td>
  </tr>
  <tr>
    <td>投诉类型</td>
    <td>
    	
    	<c:if test="${comp.complaintype==1}">涉及黄色</c:if>
		<c:if test="${comp.complaintype==2}">涉及暴力</c:if>
		<c:if test="${comp.complaintype==3}">涉及违宗教政策</c:if>
		<c:if test="${comp.complaintype==4}">涉及国家安全</c:if>
		<c:if test="${comp.complaintype==5}">抄袭内容</c:if>
		<c:if test="${comp.complaintype==6}">其它</c:if>
    </td>
  </tr>
</table>

</body>
</html>