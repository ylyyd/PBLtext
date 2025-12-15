<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html>
<div id="wrapper">
	<!-- 导航栏 -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="brand">
			<a href="readerIndex.jsp"><img
					src="assets/img/BiblioSoft Logo.png" alt="BiblioSoft Logo"
					class="img-responsive logo"></a>
		</div>
		<div class="container-fluid">
			<div class="navbar-btn">
				<button type="button" class="btn-toggle-fullwidth">
					<i class="lnr lnr-arrow-left-circle"></i>
				</button>
			</div>
			<form class="navbar-form navbar-left"></form>
			<div id="navbar-menu">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown"> <img src="assets/img/user.png"
																		 class="img-circle" alt="头像"> <span>${sessionScope.ReaderEntity.name}</span></a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- 结束导航栏 -->
	<!-- 左侧边栏 -->
	<div id="sidebar-nav" class="sidebar">
		<div class="sidebar-scroll">
			<nav>
				<ul class="nav">
					<li><a href="readerIndex.jsp" class=""><i
							class="lnr lnr-home"></i> <span>读者首页</span></a></li>
					<li><a href="readerChangeInformation.jsp" class=""><i
							class="lnr lnr-code"></i> <span>修改信息</span></a></li>
					<li><a href="readerSearchBook.jsp" class=""><i
							class="lnr lnr-chart-bars"></i><span>搜索图书</span></a></li>
					<li><a href="#subPages1" data-toggle="collapse"
						   class="collapsed"><i class="lnr lnr-cog"></i> <span>借阅记录</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>
						<div id="subPages1" class="collapse ">
							<ul class="nav">
								<li><a href="ReaderBorrowHistory" class=""><i
										class="lnr lnr-cog"></i> <span>借阅历史</span></a></li>
								<li><a href="ReaderReturnHistory" class=""><i
										class="lnr lnr-alarm"></i><span>归还历史</span></a></li>
							</ul>
						</div></li>

					<li><a href="ReaderBorrowCart" class=""><i
							class="lnr lnr-linearicons"></i> <span>借阅车</span></a></li>
					<li><a href="#" class="" onclick="logout();"><i
							class="lnr lnr-linearicons"></i> <span>退出登录</span></a></li>
				</ul>
			</nav>
		</div>
	</div>
	<script>
		function logout() {
			var result = confirm("请确认是否退出登录！");
			if (result == true) {
				window.location.href = "DestorySession";
			} else {

			}
		}
	</script>
</html>