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
	<title>图书管理员搜索读者</title>
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
				<h3 class="page-title">搜索读者</h3>
				<div class="row">
					<!-- BASIC TABLE -->
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">图书管理员搜索读者</h3>
						</div>
						<div class="panel-body">
							<form method="post" onsubmit="return inputCheck(this)"
								  action="LibrarianSearchReader">
								<table align="center">
									<tr>
										<td width=250px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="readerName">读者ID</label>
											</div>
										</td>
										<td width=350px height=64px>
											<div class="form-group has-success has-feedback">
												<input type="text" id="readerID" name="id"
													   class="form-control"
													   placeholder="请输入读者ID">
											</div>
										</td>
									</tr>
									<tr>
										<td width=250px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="readerName">读者姓名</label>
											</div>
										</td>
										<td width=350px height=64px>
											<div class="form-group has-success has-feedback">
												<input type="text" id="readerName" name="name"
													   class="form-control"
													   placeholder="请输入读者姓名">
											</div>
										</td>
									</tr>
									<tr>
										<td width=150px height=64px>
											<div class="form-group has-success has-feedback">
												<label class="control-label" for="state">状态</label>
											</div>
										</td>
										<td width=350px height=64px><input type="radio"
																		   name="state" value="unlock" />解锁 <input type="radio"
																													 name="state" value="blockade" /> 封锁 <input
												type="radio" name="state" value="unknown" checked />未指定</td>
									</tr>
								</table>
								<div style="width: 300px; margin: auto">
									<button type="submit" class="btn btn-primary btn-block">搜索</button>
								</div>
							</form>
							<br />
							<c:if test="${not empty readerEntity }">
								<table align="center" class="table">
									<tr>
										<td>ID</td>
										<td>姓名</td>
										<td>邮箱</td>
										<td>状态</td>
										<td>借阅车</td>
										<td>借阅历史</td>
										<td>当前借阅</td>
									</tr>
									<tr>
										<td>${readerEntity.id }</td>
										<td>${readerEntity.name }</td>
										<td>${readerEntity.email }</td>
										<td>${readerEntity.state }</td>
										<td><a
												href="ShowReaderInfo?reader_id=${readerEntity.id }&param=cart">详情</a></td>
										<td><a
												href="ShowReaderInfo?reader_id=${readerEntity.id }&param=history">详情</a></td>
										<td><a
												href="ShowReaderInfo?reader_id=${readerEntity.id }&param=current">详情</a></td>
									</tr>
								</table>
							</c:if>
							<c:if test="${not empty readerList }">
								<table align="center" class="table">
									<tr>
										<td>序号</td>
										<td>ID</td>
										<td>姓名</td>
										<td>邮箱</td>
										<td>状态</td>
										<td>借阅车</td>
										<td>借阅历史</td>
										<td>当前借阅</td>
									</tr>
									<c:forEach var="reader" items="${readerList }" varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${reader.id }</td>
											<td>${reader.name }</td>
											<td>${reader.email }</td>
											<td>${reader.state }</td>
											<td><a
													href="ShowReaderInfo?reader_id=${reader.id }&param=cart">详情</a></td>
											<td><a
													href="ShowReaderInfo?reader_id=${reader.id }&param=history">详情</a></td>
											<td><a
													href="ShowReaderInfo?reader_id=${reader.id }&param=current">详情</a></td>
										</tr>
									</c:forEach>
								</table>
							</c:if>
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
	var isName = /^[a-zA-Z0-9\u4e00-\u9fa5 ]{1,50}$/;
	var isReaderId = /^\d{4,10}$/;

	function inputCheck(form) {

		if (!isReaderId.test(form.readerID.value)
				&& !isName.test(form.name.value)) {
			alert("读者ID无效（应为4-10位数字！），请重新输入！");
			form.readerID.focus();
			return false;
		} else if (!isName.test(form.name.value)
				&& !isReaderId.test(form.readerID.value)) {
			alert("读者姓名必须使用英文或中文字符，少于50个字母且不能为空，请重新输入！");
			form.name.focus();
			return false;
		}

	}
</script>
</body>
</html>
