<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<jsp:include page="readerNavbar.jsp" />
<body>
<c:if test="${empty sessionScope.ReaderEntity}">
	<jsp:forward page="homepage.jsp" />
</c:if>
<!-- WRAPPER -->
<jsp:include page="readerLeftSlider.jsp" />
<!-- END LEFT SIDEBAR -->
<!-- MAIN -->
<div class="main">
	<!-- MAIN CONTENT -->
	<div class="main-content">
		<div class="container-fluid">
			<h3 class="page-title">借阅历史</h3>
			<div class="row"></div>
		</div>

		<div class="row">

			<div class="panel">
				<div class="panel-heading">
					<h3 class="panel-title">您的借阅历史</h3>
				</div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead>
						<tr>
							<th>#</th>
							<th>图书ID</th>
							<th>图书名称</th>
							<th>图书管理员ID</th>
							<th>开始时间</th>
							<th>截止时间</th>
						</tr>
						<c:forEach items="${borrowHistory}" var="borrowhistory"
								   varStatus="num">
							<tr>
								<th>${num.count}</th>
								<th>${borrowhistory.bookId}</th>
								<th>${borrowhistory.bookName}</th>
								<th>${borrowhistory.borrowLibrarianId}</th>
								<th>${borrowhistory.borrowTime}</th>
								<th>${borrowhistory.returnTime}</th>
							</tr>
						</c:forEach>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>


	<!-- END MAIN CONTENT -->
</div>
<!-- END MAIN -->
<div class="clearfix"></div>

</div>
<!-- END WRAPPER -->
<jsp:include page="Footer.jsp" />
<!-- Javascript -->
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>

</html>
