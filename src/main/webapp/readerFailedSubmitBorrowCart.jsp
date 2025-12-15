<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty sessionScope.ReaderEntity}">
	<jsp:forward page="homepage.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>失败</title>
</head>
<body>
<script language='javascript'>alert('提交借阅车失败！该图书已被预约！');window.location.href='readerBorrowCart.jsp';</script>
");
<jsp:include page="Footer.jsp" />
</body>
</html>
