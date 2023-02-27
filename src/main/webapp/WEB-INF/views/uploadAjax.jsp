<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.uploadResult {
	width: 100%;
	background-color: gray;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img {
	width: 100px;
}
</style>

<style>
.bigPictureWrapper {
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
}

.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}
</style>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>
	<h2>uploadAjax</h2>
	<div class="uploadDiv">
		<input type="file" name="uploadFile"  multiple>
	</div>
	<button id="uploadBtn">submit</button>
	<div class="uploadResult">
		<ul>
		</ul>
	</div>
	
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		</div>
	</div>
<script>
	var maxSize = 1024 * 1024 * 50;
	var regex = new RegExp("(.*?)\.(sh|zip|alz)$");
	var cloneObj = $('.uploadDiv').clone();
	
	var showImage = function(fileCallPath){
		  $(".bigPictureWrapper").css("display","flex").show();
		  
		  $(".bigPicture")
		  .html("<img src='/display?fileName="+fileCallPath+"'>")
		  .animate({width:'100%', height: '100%'}, 1000);

	}
	
	var showUploadedFile = function(uploadResultArr){
		var str = '';
		
		$(uploadResultArr).each(function(i, obj){
			if(!obj.image){
				var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"+
				    "<img src='/resources/img/attach.jpg'>"+obj.fileName+"</a>"+
				    "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
				    "<div></li>"
			}else{
				var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
				var originPath = obj.uploadPath+ "\\"+obj.uuid +"_"+obj.fileName;
				
				originPath = originPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\">"+
				       "<img src='/display?fileName="+fileCallPath+"'></a>"+
				       "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+
				       "<li>";
			}
		});
		
		$('.uploadResult ul').append(str);
	};
	
	
	var checkExtension = function(fileName, fileSize){
		if(fileSize >= maxSize){
			alert('파일 사이즈 초과!');
			return false;
		}
		
		if(regex.test(fileName)){
			alert('해당 종류의 파일은 업로드할 수 없습니다.');
			return false;
		}
		
		return true;
	};

	$(document).ready(function(){
		$('#uploadBtn').on('click', function(){
			var formData = new FormData();
			
			var files = $('input[name="uploadFile"]')[0].files;
			
			console.log(files);
			
			for(var i = 0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				
				formData.append('uploadFile', files[i]);
			}
			
			$.ajax({
				type : 'post',
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false,
				data : formData,
				dataType : 'json',
				success : function(result){
					showUploadedFile(result);
					
					$('.uploadDiv').html(cloneObj.html());
				},
				error : function(error){
					alert(error);
				}
			});
		});
		
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPicture").animate({width:'0%', height: '0%'}, 500);
			setTimeout(function() {
			 $(".bigPictureWrapper").hide();
			},500);
		});
		
		$('.uploadResult').on('click', 'span', function(){
			var targetFile = $(this).data('file');
			var type = $(this).data('type');
			
			console.log(targetFile);
			
			$.ajax({
				type : 'post',
				url : '/deleteFile',
				data : {fileName : targetFile, type : type},
				dataType : 'text',
				success : function(result){
					alert(result);
					}
			});
		});
	});
</script>
</body>
</html>