<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark " >
  <div class="collapse navbar-collapse" id="navbarSupportedContent" >
    
    <ul class="navbar-nav mr-auto">
    	<li class="nav-item">
           <a class="nav-link" href="#"><img src="/resource/images/logo.png"> </a>
      </li> 
    </ul>
    
    <form class="form-inline my-2 my-lg-0" style="margin-right:30%" >
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/images/guest.jpg"> </li>
    		
    		<c:if test="${sessionScope.loingSessionKey!=null}">
    		<li class="nav-item nav-link">${sessionScope.loingSessionKey.username}</li>
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	用户信息
		        </a>
		            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			          <a class="dropdown-item" href="/user/home">进入个人中心</a>
			          <a class="dropdown-item" href="#">个人设置</a>
			          <div class="dropdown-divider"></div>
			          <a class="dropdown-item" href="/user/logout">登出</a>
			        </div>
		      </li>
		      </c:if>
		     
		      <c:if test="${sessionScope.loingSessionKey==null}">
			       <li class="nav-item nav-link"><a href="/user/login">登录</a></li>
		      </c:if>
      
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->