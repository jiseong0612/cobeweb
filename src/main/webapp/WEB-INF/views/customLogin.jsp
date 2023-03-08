<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="/resources/vendor/jquery/jquery.min.js"></script>
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>
<script src="/resources/dist/js/sb-admin-2.js"></script>
<link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<title>SB Admin 2 - Bootstrap Admin Theme</title>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please Sign In</h3>
					</div>
					<div class="panel-body">
						<form role="form" action="/login" method="post">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="username" name="username" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password" name="password" type="password" value="">
								</div>
								<div class="checkbox">
									<label> 
										<input name="remember-me" type="checkbox">
											Remember Me 
										</label>
								</div>
								<a href="index.html" class="btn btn-lg btn-success btn-block">Login</a>
							</fieldset>
							<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
	$(document).ready(function(){
		$('.btn-success').on('click', function(e){
			e.preventDefault();
			if($.trim($('input[name=password]').val()) === '') return false;
			
			$('form').submit();
		});
		
		$('input[name=password]').on('keydown', function(e){
			if(e.keyCode === 13){
				if($.trim($('input[name=password]').val()) === '') return false;
				$('form').submit();
			}			
		});
	});
</script>
</body>
</html>