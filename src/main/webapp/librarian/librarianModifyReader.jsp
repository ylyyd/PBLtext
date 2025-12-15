<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${empty sessionScope.librarianEntity}">
	<jsp:forward page="../homepage.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>图书管理员修改读者</title>
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
				<h3 class="page-title">图书管理员修改读者</h3>
				<div class="row">
					<!-- BASIC TABLE -->
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">修改读者</h3>
						</div>
						<div class="panel-body">
							<form method="post" onsubmit="return inputCheck(this)"
								  action="LibrarianModifyReader">
								<table align="center">
									<tr>
										<td width=150px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="readerID">读者ID</label>
											</div>
										</td>
										<td width=350px height=64px>
											<div class="form-group has-success has-feedback">
												<input type="text" readonly="readonly" name="id"
													   class="form-control" value="${readerEntity.id }"
													   placeholder="读者ID">
											</div>
										</td>
									</tr>
									<tr>
										<td width=150px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="readerName">读者姓名</label>
											</div>
										</td>
										<td width=350px height=64px>
											<div class="form-group has-success has-feedback">
												<input type="text" name="name" id="name"
													   class="form-control" value="${readerEntity.name }"
													   placeholder="读者姓名">
											</div>
										</td>
									</tr>
									<tr>
										<td width=150px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="email">邮箱</label>
											</div>
										</td>
										<td width=350px height=64px>
											<div class="form-group has-success has-feedback">
												<input type="text" name="email" id="email"
													   class="form-control" value="${readerEntity.email }"
													   placeholder="邮箱">
											</div>
										</td>
									</tr>
									<tr>
										<td width=150px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="state">状态</label>
											</div>
										</td>
										<td width=350px height=64px>
											<!--  label class="control-label" for="state">${readerEntity.state }</label -->
											<c:if test="${readerEntity.state eq 'unlock'}">
												<input type="radio" name="state" value="blockade" />封锁
												<input type="radio" name="state" value="unlock" checked />解锁
											</c:if> <c:if test="${readerEntity.state eq 'blockade'}">
											<input type="radio" name="state" value="blockade" checked />封锁
											<input type="radio" name="state" value="unlock" />解锁
										</c:if>
										</td>
									</tr>
								</table>
								<div style="width: 300px; margin: auto">
									<button type="submit" class="btn btn-primary btn-block">提交</button>
								</div>
								<br />
								<div style="width: 300px; margin: auto">
									<button type="reset" class="btn btn-success btn-block">重置</button>
								</div>
							</form>
						</div>
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


	var isReaderName = /^[&a-zA-Z0-9\u4e00-\u9fa5 ]{1,}$/;
	var isEmail = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

	function inputCheck(form) {
		if (!isReaderName.test(form.name.value)) {
			alert("读者姓名无效，请重新输入！");
			form.name.focus();
			return false;
		}
		if (!isEmail.test(form.email.value)) {
			alert("邮箱无效，请重新输入！");
			form.email.focus();
			return false;
		}
	}
</script>

</body>
</html>
