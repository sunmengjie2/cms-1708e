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
<title>登录专属</title>
<link rel="stylesheet" type="text/css" href="/resource/bootstrap-4.3.1/css/bootstrap.css">
<script type="text/javascript" src="/resource/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/js/bootstrap.js"></script>
</head>
<style>
	.carousel-inner img {
		width:100%;
		height:100%;
	}
	.jumbotron {
		margin-top: 10px;
		margin-bottom: 0px;
		padding-top: 10px;
	}
	#demo {
		width:100%;
		height: 264px;
	}
	.row {
		height: 264px;
	}
	.left {
		float: left;
		width: 70%;
	}
	.right {
		float: left;
		width: 30%;
	}
	.card {
		height: 264px;
	}
	.carousel-inner {
		height: 264px;
	}
	.footer {
		position: absolute;
		bottom: 0;
		height: 60px;
	}
	.jumheight1 {
		height: 140px;
	}
	.jumheight2 {
		height: 260px;
	}
	.end_name {
		margin-bottom: 5px;
	}
	.footer2 {
		padding-top: 210px;
		text-align: center;
	}
	</style>

<script type="text/javascript">
function Toregister() {
	location="register";
}
</script>


<body>
	<div>
		<div class="jumbotron text-info bg-light jumheight1">
			<h3>只有一条路不能选择――那就是放弃。
				<!-- <audio controls="controls"  autoplay="autoplay" loop=-1 preload="auto">
  					<source src="/resource/js/images/chengdu.mp3"  type="audio/mpeg"/>
  					
				</audio> -->
			</h3>
			<p>一个简单的系统</p>
			
		</div>
		<!-- 轮播图 -->
		<!-- 指示符 -->
  		<div class="row">
		<div class="left">
		<div id="demo" class="carousel slide " data-ride="carousel">
			<ul class="carousel-indicators">
				<li data-target="#demo" data-slide-to="0" class="active"></li>
				<li data-target="#demo" data-slide-to="1"></li>
				<li data-target="#demo" data-slide-to="2"></li>
			</ul>
			<!-- 轮播图片 -->
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="http://static.runoob.com/images/mix/img_fjords_wide.jpg">
				</div>
				<div class="carousel-item">
					<img src="http://static.runoob.com/images/mix/img_nature_wide.jpg">
				</div>
				<div class="carousel-item">
					<img src="http://static.runoob.com/images/mix/img_mountains_wide.jpg">
				</div>
			</div>
			<!-- 左右切换按钮 -->
			<a href="#demo" class="carousel-control-prev" data-slide="prev">
				<span class="carousel-control-prev-icon"></span>
			</a>
			<a href="#demo" class="carousel-control-next" data-slide=next>
				<span class="carousel-control-next-icon"></span>
			</a>
		</div>
 		</div>
		<div class="right">
		<!-- 登陆窗口 -->
		<div class="card">
			<div class="card-header">
				用户登陆
			</div>
			<div class="card-body">
				<form action="login" method="post">
					<table style="border-collapse: separate;border-spacing: 0px 10px;">
						<tr>
							<td class="margin-top:10">
								<label>用户名：</label>
							</td>
							<td>
								<input type="text" name="username" >
							</td>
						</tr>
						<tr>
							<td>
								<label>密码:</label>
							</td>
							<td>
								<input type="password" name="password" >
							</td>
						</tr>
					</table>
					
					<div class="form-check">
						<label class="form-check-label">
							<input type="checkbox" name="" class="form-check-input">记住密码
						</label>
					</div>
						<div class="footer">
					<button type="submit" class="btn btn-primary">登陆</button>
					<button type="button" class="btn btn-secondary" onclick="Toregister()">注册</button>
					<button type="button" class="btn btn-link" onclick="updPwd"><small>忘记密码？</small></button>
					</div>
				</form>
			</div>
		</div>
		</div>
 		</div>
		<div class="jumbotron bg-light jumheight2">
			<p class="footer2">@Master_ADCJ</p>
		</div>
	</div>
</body>
</html>