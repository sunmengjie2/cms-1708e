<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<c:forEach items="${complianPage.list}" var="complain">
	<div class="row">
		<div class="col-md-3">${complain.user.username}</div>
		<div class="col-md-3">${complain.content}</div>
	</div>

</c:forEach> 