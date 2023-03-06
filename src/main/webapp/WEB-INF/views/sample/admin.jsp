<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
sample/admin<br>

<p>principal : <sec:authentication property="principal"/></p>
<p>memberVO : <sec:authentication property="principal.member"/></p>
<p>사용자 이름 : <sec:authentication property="principal.member.userName"/></p>
<p>아이디 : <sec:authentication property="principal.member.userid"/></p>
<p>권한 리스트 : <sec:authentication property="principal.member.authList"/></p>

<a href="/customLogout">logout!</a>
</body>
</html>