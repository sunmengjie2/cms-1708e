<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	function login() {
		
	}
</script>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark " >
  <div class="collapse navbar-collapse" id="navbarSupportedContent" >
    
    <ul class="navbar-nav mr-auto">
    	<li class="nav-item">
           <a class="nav-link" href="#"><img src="/resource/images/logo.png"> </a>
      </li> 
    </ul>
    
    <form class="form-inline my-2 my-lg-0" style="margin-right:30%" action="index" method="get">
      <input class="form-control mr-sm-2" name="key" type="search" value="${key }" placeholder="从es索引库搜索" aria-label="搜索">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">搜索</button>
    </form>
    
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/pic/${user.url }"> </li>
    		
    		<c:if test="${user!=null}">
    		<li class="nav-item nav-link"><font color="red">${user.nickname}</font></li>
    		<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	用户信息
		        </a>
		            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			          <a class="dropdown-item" href="/user/home">个人中心</a>	
			          <c:if test="${user.role==1}">
			          	<a class="dropdown-item" href="admin/index">管理页面</a>	
			          </c:if>
			          <a class="dropdown-item" href="#">个人设置</a>
			          <div class="dropdown-divider"></div>
			          <a class="dropdown-item" href="/user/exit">登出</a>
			        </div>
		      </li>
		      </c:if>
		     
		      <c:if test="${user==null}">
			       <li class="nav-item nav-link">
			       	<input type="button" class="btn btn-primary" onclick="login()" value="登录">
			       </li>
		      </c:if>
      
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->

<script type="text/javascript">
	function login() {
		$('#login').modal('show')
	}
	
	function loginUser() {
		var username=$("[name=username]").val();
		var password=$("[name=password]").val();
		
		$.post("/user/loginIndex",{username:username,password:password},function(msg){
			if(msg.code==1){
				alert(msg.data);
				$('#login').modal('hide')
				location="index";
			}else{
				alert(msg.error)
			}
		},"json")
	}
</script>


<div class="modal" id="login" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
    	    用户名：<input type="text" name="username"  >
      </div>
      <div class="modal-body">
      	 密　码：<input type="password" name="password" >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <input type="button" class="btn btn-primary" onclick="loginUser()" value="登录">
        <a href="/user/register"><button type="button" class="btn btn-light">注册</button></a>
      </div>
    </div>
  </div>
</div>