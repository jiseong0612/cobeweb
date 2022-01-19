<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.uploadResult {
		width : 100%;
		background-color : gray;
	}
	
	.uploadResult Ul{
		display : flex;
		flex-flow : row;
		justisfy-content : center;
		align-items : center;
	}
	
	.uploadResult ul li{
		list-style : none;
		padding : 10px;
	}
	
	.uploadResult up li img{
		width : 20px;
	}
</style>
<script src="/resources/vendor/jquery/jquery.js"></script>
<script>
var checkExtension = function(fileName, fileSize){
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

var showUPloadedFile = function(arr){
	var resultHtml = '';
	
	arr.forEach(function(obj, i){
		if(obj.image == false){
			resultHtml += '<li><img src="/resources/img/normal.PNG" title="'+obj.fileName+'"></li>';
		}else{
			var fileCallPath = encodeURIComponent(obj.uploadPath +"/s_"+obj.fileName);
			resultHtml += '<li><img src="/display?fileName='+fileCallPath+'"></li>';
		}
	});
	
	return resultHtml
}

$(document).ready(function(){
	$("button").on("click",function(){
		var fd = new FormData();
		var files = $("#uploadFile")[0].files;
		
		console.log(files);
		
		for(var i =0; i<files.length; i++){
			if(checkExtension(files[i].name, files[i].size)){
				return false;
			}
			fd.append("uploadFile", files[i]);			
		}
		
		$.ajax({
			url:"/uploadAjaxAction",
			data : fd,
			type :"POST",
			dataType : "json",
			contentType : false,
			processData : false,
			success : function(result){
				console.log("result >>> ",result);
				
				//화면 출력
				$("ul").append(showUPloadedFile(result));
				
				//file input 태그 초기화
				$("#uploadFile").val('');
			},
			error : function(error){
				console.log("error.....");
				console.log(error);
			}
		});	//ajax end
	});
});
</script>
</head>
<body>
<h2>upload Ajax</h2>
<input type="file" name="uploadFile" id="uploadFile" multiple>
<button>업로드</button>
<div class="uploadResult">
	<ul></ul>
</div>
</body>
</html>