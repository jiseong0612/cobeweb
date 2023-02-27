<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

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

				<form role="form" action="/board/register" method="post">
					<div class="form-group">
						<label>Title</label> 
						<input class="form-control" name='title'>
					</div>

					<div class="form-group">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name='content'></textarea>
					</div>

					<div class="form-group">
						<label>Writer</label> 
						<input class="form-control" name='writer'>
					</div>
					<button type="submit" class="btn btn-default">Submit Button</button>
					<button type="reset" class="btn btn-default">Reset Button</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!--  파일 첨부 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">File Attach</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
				    <input type="file" name='uploadFile' multiple>
				</div>
				<div class='uploadResult'> 
					<ul>
					</ul>
				</div>
			</div>
		</div>
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

var showUploadResult = function(uploadResultArr){
	if(!uploadResultArr || uploadResultArr.lenght === 0) { return; }
	var str = '';
	
	$(uploadResultArr).each(function(i, obj){
		if(obj.image){
			var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
			str += "<li data-path='"+obj.uploadPath+"'data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>";
			str += "	<div>";
			str += "		<span>" + obj.fileName+ "</span>";
			str += "		<button type='button' data-file='" + fileCallPath + "'data-type='image' class='btn btn-warning btn-circle'>";
			str += "			<i class='fa fa-times'></i>";
			str += "		</button><br>";
			str += "		<img src='/display?fileName="+fileCallPath+"'>";
			str += "	</div>";
			str += "</li>";
		}else{
			var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);			      
		    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		      
			str += "<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' >";
			str += "	<div>";
			str += "		<span> "+ obj.fileName+"</span>";
			str += "		<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'>";
			str += "			<i class='fa fa-times'></i>";
			str += "		</button><br>";
			str += "		<img src='/resources/img/attach.jpg'>";
			str += "	</div>";
			str += "</li>";
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
	var formObj = $('form[role="form"]');
	
	$('button[type="submit"]').on('click', function(e){
		e.preventDefault();
		console.log('submit button clicked!!');
		
		var str = '';
		
		$('.uploadResult ul li').each(function(i, obj){
			var jobj = $(obj);
			
			str += '<input type="hidden" name="attachList['+i+'].fileName" value="'+ jobj.data('filename')+'">';
			str += '<input type="hidden" name="attachList['+i+'].uuid" value="'+ jobj.data('uuid')+'">';
			str += '<input type="hidden" name="attachList['+i+'].uploadPath" value="'+ jobj.data('path')+'">';
			str += '<input type="hidden" name="attachList['+i+'].fileType" value="'+ jobj.data('type')+'">';
		});
		
		formObj.append(str);
		formObj.submit();
	});
	
	$('input[type="file"]').on('change', function(){
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
				//$('.uploadDiv').html(cloneObj.html());
				$('input[name="uploadFile"]').val('');
				
				showUploadResult(result)
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
	
	$('.uploadResult').on('click', 'button', function(){
		var targetFile = $(this).data('file');
		var type = $(this).data('type');
		
		console.log(targetFile);
		var targetLi = $(this).closest('li');
		
		$.ajax({
			type : 'post',
			url : '/deleteFile',
			data : {fileName : targetFile, type : type},
			dataType : 'text',
			success : function(result){
				alert(result);
				targetLi.remove();
			}
		});
	});
	
});
</script>
<%@include file="../includes/footer.jsp"%>
