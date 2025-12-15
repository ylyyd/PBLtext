<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>图书管理员删除图书</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet"
		  href="assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		  href="assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
	<link rel="stylesheet"
		  href="assets/vendor/chartist/css/chartist-custom.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link
			href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
			rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76"
		  href="assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96"
		  href="assets/img/favicon.png">
</head>

<body>
<c:if test="${empty sessionScope.librarianEntity}">
	<jsp:forward page="../homepage.jsp" />
</c:if>
<!-- WRAPPER -->
<div id="wrapper">
	<%@ include file="navbar.jsp"%>
	<%@ include file="sidebar.jsp"%>
	<!-- MAIN -->
	<div class="main">
		<!-- MAIN CONTENT -->
		<div class="main-content">
			<div class="container-fluid">
				<h3 class="page-title">删除图书</h3>
				<div class="row">
					<div class="col-md-12">

						<!-- INPUTS -->
						<form method="POST" onsubmit="return inputCheck(this)"
							  action="SearchBookById">
							<div class="panel">
								<div class="panel-heading">
									<h3 class="panel-title">输入图书ID</h3>
								</div>
								<div class="panel-body">
									<div class="col-md-12">
										<div class="input-group">
											<!-- 在此输入book_ID -->
											<input class="form-control" type="text" name="book_id"
												   id="bookID" placeholder="图书ID"> <span
												class="input-group-btn"><button
												class="btn btn-primary" type="SUBMIT">搜索</button></span>
										</div>
									</div>
								</div>
							</div>
						</form>
						<div class="panel">
							<div class="panel-heading">
								<h3 class="panel-title">查找结果</h3>
							</div>
							<div class="panel-body no-padding">
								<table class="table">
									<thead>
									<tr>
										<th>ISBN</th>
										<th>书名</th>
										<th>出版社</th>
										<th>作者</th>
										<th>图书ID</th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td>${bookEntity.ISBN }</td>
										<td>${bookEntity.name}</td>
										<td>${bookEntity.publisher.name }</td>
										<td>${bookEntity.authors}</td>
										<td>${bookEntity.id }</td>
										<form method="POST" action="LibrarianDeleteBook">
											<input class="form-control" type="hidden" name="book_id"
												   value="${bookEntity.id}">
											<th>
												<button type="SUBMIT" class="btn btn-danger">
													<i class="fa fa-trash-o"></i> 删除
												</button>
											</th>
										</form>
									</tr>
									</tbody>
								</table>
							</div>
						</div>
						<!-- END TABLE NO PADDING -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END MAIN CONTENT -->
</div>
<!-- END MAIN -->
<div class="clearfix"></div>
<jsp:include page="Footer.jsp" />
<!-- END WRAPPER -->
<!-- Javascript -->
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script
		src="assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
<script src="assets/vendor/chartist/js/chartist.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>

<script>

	var isBookId = /^\d{1,10}$/;

	function inputCheck(form) {
		if (!isBookId.test(form.bookID.value)) {
			alert("图书ID无效（应为1-10位数字！），请重新输入！");
			form.bookID.focus();
			return false;
		}
	}
</script>

</body>

</html>
