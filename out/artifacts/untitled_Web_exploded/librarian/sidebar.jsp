<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>
<!-- VENDOR CSS -->
<link rel="stylesheet"
	  href="assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	  href="assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
<link rel="stylesheet"
	  href="assets/vendor/chartist/css/chartist-custom.css">

<!-- LEFT SIDEBAR -->
<div id="sidebar-nav" class="sidebar">
	<div class="sidebar-scroll">
		<nav>
			<ul class="nav">
				<li><a href="librarianHomepage.jsp" class=""> <i
						class="lnr lnr-home"></i> <span>图书管理员首页</span>
				</a></li>
				<li><a href="librarianModifyInfo.jsp" class=""> <i
						class="lnr lnr-home"></i> <span>修改密码</span>
				</a></li>

				<li><a href="#subPages1" data-toggle="collapse"
					   class="collapsed"><i class="lnr lnr-cog"></i> <span>管理图书</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
					<div id="subPages1" class="collapse">
						<ul class="nav">
							<li><a href="librarianAddBook.jsp" class="">添加图书</a></li>
							<li><a href="librarianDeleteBook.jsp" class="">删除图书</a></li>
							<li><a href="librarianSearchBook.jsp" class="">搜索图书</a></li>
							<li><a href="librarianReturnBook.jsp" class="">归还图书</a></li>
						</ul>
					</div></li>

				<li><a href="#subPages2" data-toggle="collapse"
					   class="collapsed"><i class="lnr lnr-cog"></i> <span>管理读者</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
					<div id="subPages2" class="collapse">
						<ul class="nav">
							<li><a href="librarianAddReader.jsp" class="active">添加读者</a></li>
							<li><a href="librarianDeleteReader.jsp" class="">删除读者</a></li>
							<li><a href="searchReaderBeforeEdit.jsp" class="">编辑读者</a></li>
							<li><a href="librarianSearchReader.jsp" class="">搜索读者</a></li>
							<li><a href="librarianLendBook.jsp" class="">借书给读者</a></li>
							<li><a href="ShowCart?start=0" class="">显示借阅车</a></li>
						</ul>
					</div></li>

				<li><a href="#" onclick="logout()" class=""> <i
						class="lnr lnr-linearicons"></i> <span>退出登录</span>
				</a></li>
			</ul>
		</nav>
	</div>
</div>
<!-- END LEFT SIDEBAR -->
<script>
	function logout() {
		var result = confirm("请确认是否要退出登录？");
		if (result == true) {
			window.location.href = "../DestorySession";
		} else {

		}
	}
</script>
