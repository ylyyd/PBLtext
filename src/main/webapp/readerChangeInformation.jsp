<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<h3 class="page-title">修改信息</h3>
			<div class="row">
				<!-- BASIC TABLE -->
				<div class="panel">
					<div class="panel-body">
						<table class="table">
							<thead>
							<tr>
								<th>读者ID</th>
								<th>读者姓名</th>
								<th>密码</th>
								<th>邮箱</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<!-- Reader change information -->

								<td>${ReaderEntity.id }</td>
								<form onsubmit="return inputCheck(this)" method="post"
									  action="ReaderModifyInformation">
									<td><input type="text" id="namec" name="newName"
											   value="${ReaderEntity.name }" maxlength="15"
											   style="height: 23px; width: 100px"></td>
									<td><input type="password" id="psdc" name="newPassword"
											   maxlength="15" style="height: 23px; width: 100px"></td>
									<td><input type="text" id="emailc" name="newEmail"
											   value="${ReaderEntity.email }" maxlength="15"
											   style="height: 23px; width: 150px"></td>
									<td><div style="width: 150px; margin: auto">
										<button type="submit" name="cis2"
												class="btn btn-primary btn-block">修改</button></td>
								</form>
							</tr>

							</tbody>
						</table>
						<div></div>
					</div>
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
<script src="assets/scripts/klorofil-common.js"></script>
<script type="text/javascript">
	var isName = /^[a-zA-Z0-9\u4e00-\u9fa5 ]{1,50}$/;
	var isPassword = /^\w{1,50}$/;
	var isEmail = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

	function inputCheck(form) {
		if (!isName.test(form.namec.value)) {
			alert("读者姓名必须使用英文或中文字符，少于50个字母且不能为空，请重新输入！");
			form.namec.focus();
			return false;
		}

		if (!isPassword.test(form.psdc.value)) {
			alert("密码必须少于50个字符且不能为空，请重新输入！");
			form.psdc.focus();
			return false;
		}

		if (!isEmail.test(form.emailc.value)) {
			alert("邮箱必须是有效地址，请重新输入！");
			form.emailc.focus();
			return false;
		}

	}
</script>
</body>

</html>
