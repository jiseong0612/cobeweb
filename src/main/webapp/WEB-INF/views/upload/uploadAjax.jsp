<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="/resources/css/style.css">
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

var hideImage = function(){
	$(".bigPicture").animate({width:'0%', height:'0%'}, 1000);
	setTimeout(function(){
		$(".bigPictureWrapper").hide();
	}, 1000);
}

var showImage = function(fileCallPath){
	$(".bigPictureWrapper").css("display","flex").show();
	 $(".bigPicture")
	  .html("<img src='/display?fileName="+fileCallPath+"'>")
	  .animate({width:'100%', height: '100%'}, 1000);
}

var showUPloadedFile = function(arr){
	var str = '';
	console.log("showUPloadedFile() >>> ", arr);
	arr.forEach(function(obj, i){
		var fileCallPath = encodeURIComponent(obj.uploadPath + "/" +obj.fileName);
		
		//이미지 아님
		if(obj.image == false){
			str += '<li><a href="/download?fileName='+fileCallPath+'"><img src="/resources/img/normal.PNG" title="'+obj.fileName+'"></a>';
			str += '<span data-file="'+fileCallPath+'" data-type="file" >[x]</span></li>';
		}
		//이미지
		else{
			var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" +obj.fileName);
			var originPath = obj.uploadPath.replace(/\\/g,'/') + "/"+ obj.fileName;
			str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a>";
			str += '<span data-file=\"'+fileCallPath+'\" data-type="image" >[x]</span></li>';
		}
	});
	return str;
}

$(document).ready(function(){
	$(".uploadResult").on("click","span",function(){
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		console.log(targetFile);
		console.log(type);
		
		$.ajax({
			url: "/deleteFile",
			data : {fileName : targetFile,  type : type},
			dataType : "text",
			type : "post",
			success : function(result){
				alert(result);
				console.log($(this));
			}
		}); //ajax end
	});
	
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
<div class='bigPictureWrapper' onclick="hideImage()">
  <div class='bigPicture'>
  </div>
</div>
<div class="uploadResult">
	<ul></ul>
</div>
</body>
</html>