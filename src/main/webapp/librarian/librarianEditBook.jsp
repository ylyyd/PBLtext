<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${empty sessionScope.librarianEntity}">
	<jsp:forward page="../homepage.jsp" />
</c:if>
<html lang="en">
<head>
	<title>图书管理员编辑图书</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<!-- VENDOR CSS -->
	<link rel="stylesheet"
		  href="../assets/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet"
		  href="../assets/vendor/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="../assets/vendor/linearicons/style.css">
	<link rel="stylesheet"
		  href="../assets/vendor/chartist/css/chartist-custom.css">
	<!-- MAIN CSS -->
	<link rel="stylesheet" href="../assets/css/main.css">
	<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
	<link rel="stylesheet" href="../assets/css/demo.css">
	<!-- GOOGLE FONTS -->
	<link
			href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
			rel="stylesheet">
	<!-- ICONS -->
	<link rel="apple-touch-icon" sizes="76x76"
		  href="../assets/img/apple-icon.png">
	<link rel="icon" type="image/png" sizes="96x96"
		  href="../assets/img/favicon.png">
</head>

<body>
<!-- WRAPPER -->
<div id="wrapper">
	<%@ include file="navbar.jsp"%>
	<%@ include file="sidebar.jsp"%>

	<!-- MAIN -->
	<div class="main">
		<!-- MAIN CONTENT -->
		<div class="main-content">
			<div class="container-fluid">
				<h3 class="page-title">信息</h3>
				<div class="row">
					<div class="col-md-12">

						<!-- INPUTS -->
						<div class="panel">
							<div class="panel-heading"></div>

							<div class="panel-body">
								ISBN:<input type="text" class="form-control"
											readonly="readonly" name="ISBN" value="${bookEdit.ISBN }" /> <br>
								书名:<input type="text" class="form-control"
											readonly="readonly" name="name" value="${bookEdit.name}" /> <br>
								出版社:<input type="text" class="form-control"
											  readonly="readonly" name="publisher"
											  value="${bookEdit.publisher.name }" /> <br> 作者:<input
									type="text" class="form-control" readonly="readonly"
									name="authors" value="${bookEdit.authors }" /> <br>
								价格:<input type="text" class="form-control"
											readonly="readonly" name="price" value="${bookEdit.price }" />
								<br> 描述:
								<textarea class="form-control" readonly="readonly"
										  name="description">${bookEdit.description }</textarea>
								<br>
							</div>
						</div>
						<!-- END INPUTS -->
						<form method="post" action="LibrarianEditBook">
							<table class="table">
								<thead>
								<tr>
									<th>序号</th>
									<th>图书ID</th>
									<th>图书位置</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach var="item" items="${bookEditList }" varStatus="i">
									<c:if test="${item.id != bookEditId }">
										<tr>
											<td>${i.count }</td>
											<td>${item.id }</td>
											<td>${item.location }</td>
											<td>${item.state }</td>
											<th>
												<button type="button" class="btn btn-primary">
													<i class="fa fa-refresh"></i> 刷新
												</button>
											</th>
										</tr>
									</c:if>
									<c:if test="${item.id == bookEditId }">
										<tr>
											<td>${i.count }</td>
											<td>${item.id }</td>
											<input type="hidden" name="Book ID" value="${item.id }" />
											<td><input type="text" name="Location"
													   value="${item.location }" /></td>
											<td><input type="text" name="State"
													   value="${item.state }"></td>
											<th>
												<button type="submit" class="btn btn-primary">
													<i class="fa fa-refresh"></i> 刷新
												</button>
											</th>
										</tr>

									</c:if>
								</c:forEach>
								</tbody>
							</table>
						</form>
						<!-- END INPUT SIZING -->
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
	<script src="../assets/vendor/jquery/jquery.min.js"></script>
	<script src="../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script
			src="../assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script
			src="../assets/vendor/jquery.easy-pie-chart/jquery.easypiechart.min.js"></script>
	<script src="../assets/vendor/chartist/js/chartist.min.js"></script>
	<script src="../assets/scripts/klorofil-common.js"></script>
</body>

</html>
