<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/vendor/jquery/jquery.js"></script>
<script>

var showFileList = function(list){
	console.log(list);
	var resultHtml = '';
	
	list.forEach(function(i, obj){
		console.log(i);
		console.log(obj);
		resultHtml += '<li>'+i.fileName+'</li>';
	});
	
	$(".fileList ul").append(resultHtml);
}
$(document).ready(function(){
	$("button").click(function(){
		var fd = new FormData();
		
		var files = $("#file")[0].files;
		
		for(var i =0; i<files.length; i++){
			fd.append('files', files[i]);
		}
		
		$.ajax({
			url : "/uploadAjax",
			data : fd,
			type : "post",
			contentType : false,
			processData : false,
			success : function(result){
	
				showFileList(result);
			},
			error : function(error){
				console.log("errreeeee.rrr");
			}
		});
	});
});
</script>
</head>
<body>
<h1>ajax fileUpload</h1>
<div> 
	<input type="file" name="file" id="file" multiple>
	<button>submit</button>
</div>
<div class="fileList">
	<ul>
	</ul>
</div>
</body>
</html>