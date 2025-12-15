<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty sessionScope.librarianEntity}">
	<jsp:forward page="../homepage.jsp" />
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>显示购物车</title>
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
<!-- WRAPPER -->
<div id="wrapper">
	<!-- NAVBAR -->
	<%@ include file="navbar.jsp"%>

	<!-- LEFT SIDEBAR -->
	<%@ include file="sidebar.jsp"%>

	<!-- MAIN -->
	<div class="main">
		<!-- MAIN CONTENT -->
		<div class="main-content">
			<div class="container-fluid">
				<!-- OVERVIEW -->
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">显示购物车</h3>
					</div>
					<div class="panel-body">
						<table align="center" class="table">
							<thead>
							<tr>
								<th>序号</th>
								<th>读者ID</th>
								<th>读者姓名</th>
								<th>图书ID</th>
								<th>图书名称</th>
								<th>提交时间</th>
								<th>操作</th>
							</tr>
							<c:forEach var="item" items="${cartList }" varStatus="i">
								<tr>
									<td>${i.count+current }</td>
									<td>${item.readerId }</td>
									<td>${item.readerName }</td>
									<td>${item.bookId }</td>
									<td>${item.bookName }</td>
									<td>${item.submitTime }</td>
									<td><a
											href="LibrarianLendBook?operate=agree&readerId=${item.readerId}&bookId=${item.bookId}">同意</a>
										or <a
												href="LibrarianLendBook?operate=disagree&readerId=${item.readerId}&bookId=${item.bookId}">拒绝</a>
									</td>
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
