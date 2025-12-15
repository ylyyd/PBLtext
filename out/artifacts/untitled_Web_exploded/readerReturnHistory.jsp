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
			<h3 class="page-title">您的归还历史</h3>
			<div class="row"></div>
		</div>

		<div class="row">

			<div class="panel">
				<div class="panel-heading">
					<h3 class="panel-title">归还历史</h3>
				</div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead>
						<tr>
							<th>#</th>
							<th>图书ID</th>
							<th>图书名称</th>
							<th>图书管理员ID</th>
							<th>归还时间</th>
						</tr>
						<c:forEach items="${borrowitems}" var="returnhistory"
								   varStatus="num">
							<tr>
								<th>${num.count+nums}</th>
								<th>${returnhistory.bookId}</th>
								<th>${returnhistory.bookName}</th>
								<th>${returnhistory.borrowLibrarianId}</th>
								<th>${returnhistory.returnTime}</th>
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
