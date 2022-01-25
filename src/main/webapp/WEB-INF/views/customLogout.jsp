<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>로그아웃 페이지~</h2>
<!-- 시큐리티 로그아웃은 POST 방식 -->
<form action="/customLogout" method="post">
	<!-- csrf 토큰을 히든으로하여 넣어준다. 로그아웃은 시큐리티 내부에서 동작  -->
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
	<button>로그아웃</button>
</form>
</body>
</html>