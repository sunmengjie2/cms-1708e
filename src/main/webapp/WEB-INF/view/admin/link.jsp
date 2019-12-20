<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript"
	src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
</head>
<body>
	<table class="table">
		<thead>
			<tr>
				<th>ID</th>
				<th>路径</th>
				<th>网站名</th>
				<th>发布时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${linkPage.list}" var="link">
				<tr>
					<td>${link.id }</td>
					<td>${link.url}</td>
					<td>${link.name}</td>
					<td><fmt:formatDate value="${link.created}"/></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>