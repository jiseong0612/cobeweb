<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
var add = function(x, y){
	return x + y;
}

$(document).ready(function(){
	if(add){
		console.log(add)
		console.log("있네")
	}else{
		console.log("없네")
	}
});
</script>
</head>
<body>
<h2>있네 없네</h2>
</body>
</html>