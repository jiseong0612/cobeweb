<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Custom Login Page</h1>
	<h2>
		<c:out value="${error }" />
	</h2>
	<h2>
		<c:out value="${logout }" />
	</h2>
	<h4>아이디 : user0 ~ user99</h2>
	<h4>비번 : pw0 ~ pw99</h2>
	<h4>권한 : 0~ 79? 일반 : 80~89 ? 멤버 : 관리자(90~99)</h2>
	<form action="/login" method="post">
		<input type="text" name="username" value="user90"> <br>
		<input type="password" name="password" value="pw90"> <br>
		<input type="submit" value="제출">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	</form>
</body>
</html>