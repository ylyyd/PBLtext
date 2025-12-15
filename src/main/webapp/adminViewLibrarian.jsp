<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>查看图书管理员</title>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">

<!-- GOOGLE FONTS -->
<link
		href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
		rel="stylesheet">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	  href="assets/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	  href="assets/img/favicon.png">
<body>
<c:if test="${empty sessionScope.AdministratorEntity}">
	<jsp:forward page="homepage.jsp" />
</c:if>
<!-- WRAPPER -->
<div id="wrapper">
	<!-- NAVBAR -->
	<jsp:include page="adminNavbar.jsp" />

	<!-- LEFT SIDEBAR -->
	<jsp:include page="adminLeftSlider.jsp" />

	<!-- MAIN -->
	<div class="main">
		<!-- MAIN CONTENT -->
		<div class="main-content">
			<div class="container-fluid">
				<!-- OVERVIEW -->
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">查看图书管理员</h3>
					</div>
					<div class="panel-body">
						<form class="navbar-form navbar-left" role="search"
							  align="center" method="post" action="SearchLibrarian">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;</form>

						<br /> <br /> <br />
						<table class="table">
							<thead>
							<tr>
								<th>图书管理员ID</th>
								<th>图书管理员姓名</th>
								<th>图书管理员状态</th>
								<th>操作</th>
							</tr>

							<!-- 展示Librarian信息 -->


							<c:forEach var="lib" items="${libs}">
								<tr>
									<th>${lib.id}</th>
									<th>${lib.name}</th>
									<th>${lib.state}</th>
									<th>
										<button class="btn btn-danger"
												onclick="window.location.href='adminDeleteLibrarian.jsp?id=${lib.id}&name=${lib.name}'">删除</button>
										<button class="btn btn-primary"
												onclick="window.location.href='adminModifyLibrarianBefore.jsp?id=${lib.id}'">修改</button>
									</th>
								</tr>
							</c:forEach>

							</thead>

						</table>
						<nav>
							<ul class="pager">
								<li><a href="?start=0">首页</a></li>
								<li><a href="?start=${pre}">上一页</a></li>
								<li><a href="?start=${next}">下一页</a></li>
								<li><a href="?start=${last}">尾页</a></li>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END MAIN CONTENT -->
	<!-- END MAIN -->
	<div class="clearfix"></div>

	<!-- END WRAPPER -->
	<jsp:include page="Footer.jsp" />
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
			src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
	<script src="assets/vendor/chartist/js/chartist.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
