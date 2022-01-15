<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/vendor/jquery/jquery.js"></script>
<script>
var checkExtension(fileName, fileSize){
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 1024* 1024*5;
	
	if(fileSize > maxSize){
		alert("파일 사이즈 초과");
		 return false;
	}
	
	if(regex.test(fileName)){
		alert("확장자 제한 파일!!");
		return false;
	}
	
}

$(document).ready(function(){
	$("button").on("click",function(){
		var fd = new FormData();
		var files = $("#uploadFile")[0].files;
		
		console.log(files);
		
		for(var i =0; i<files.length; i++){
			if(!checkExtension(files[i].name, files[i].Size)){
				return false;
			}
			
			fd.append("uploadFile", files[i]);			
		}
		
		$.ajax({
			url:"/uplaodPost",
			data : fd,
			type :"POST",
			contentType : false,
			processData : false,
			success : function(result){
				console.log("success....");
			},
			error : function(error){
				console.log("error....", error);
			}
		});
	});
});
</script>
</head>
<body>
<h2>upload Ajax</h2>
<input type="file" name="uploadFile" id="uploadFile"multiple>
<button>업로드</button>
</body>
</html>