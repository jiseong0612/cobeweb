<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>
<script>
var checkExtension = function(fileName, fileSize){
	var regex = new RegExp("(.*?)\.(exe|sh|zip|als)$");
	var maxSize = 1024 * 1024 * 5 // 5MB
	
	if(fileSize >= maxSize){
		alert("파일 사이즈 초과");
		return false;
	}
	
	if(regex.test(fileName)){
		alert("해당 종류의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
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
	
	$("#submitBtn").on("click",function(e){
		console.log(e.currentTarget.id)
		e.preventDefault();
	});
	
	$("#uploadFile").change(function(){
		var fd = new FormData();
		
		var files = $("#uploadFile")[0].files;
		
		for(var i = 0; i<files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			fd.append("uploadFile", files[i]);
		}
		
		$.ajax({
			url:"/uploadAjaxAction",
			processData : false,
			contentType : false,
			type : "post",
			data : fd,
			dataType : "json",
			success : function(result){
				$(".ulList").append(showUPloadedFile(result));
			},
			error : function(error){
				console.log(error);
			}
		});
		
	});
	
	$(".uploadResult").on("click","span",function(){
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		var targetLi = $(this).closest("li");
		$.ajax({
			url: "/deleteFile",
			data : {fileName : targetFile,  type : type},
			dataType : "text",
			type : "post",
			success : function(result){
				alert(result);
				targetLi.remove();
			}
		}); //ajax end
	});
});

</script>
<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Register</h1>
  </div>
</div>

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">
      <div class="panel-heading">Board Register</div>
      <div class="panel-body">
        <form action="/board/register" method="post">
          <div class="form-group">
            <label>작성자</label> <input class="form-control" name='writer'>
          </div>
          <div class="form-group">
            <label>제목</label> <input class="form-control" name='title'>
          </div>
          <div class="form-group">
            <label>내용</label>
            <textarea class="form-control" rows="3" name='content'></textarea>
          </div>
          <button type="submit" id="submitBtn" class="btn btn-default">Submit</button>
          <button type="reset" class="btn btn-default">Reset</button>
        </form>
      </div>
    </div>
    
	<!-- 파일첨부 -->
	<div class='bigPictureWrapper' onclick="hideImage()">
 		<div class='bigPicture'>
 		</div>
	</div>

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">File Attach</div>
				<div class="panel-body">
					<div class="form-group uploadDiv">
						<input type="file" id="uploadFile" name="uploadFile" multiple>
					</div>
					<div class="uploadResult">
						<ul class="ulList">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
  </div>
</div>
<%@include file="../includes/footer.jsp"%>
