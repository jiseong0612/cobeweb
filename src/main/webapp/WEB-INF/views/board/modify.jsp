<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@include file="../includes/header.jsp"%>


<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Register</h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board Modify</div>
            <div class="panel-body">
                <form role="form" action="/board/modify" method="post">
                	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
                    <input type="hidden" name="pageNum" value="${cri.pageNum }">
                    <input type="hidden" name="amount" value="${cri.amount }">
                    <input type="hidden" name="type" value="${cri.type }">
                    <input type="hidden" name="keyWord" value="${cri.keyWord }">

                    <div class="form-group">
                        <label>Bno</label>
                        <input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label>Title</label>
                            <input class="form-control" name='title' value='<c:out value="${board.title }"/>'>
                    </div>
                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="3" name='content'><c:out value="${board.content}" /></textarea>
                    </div>
                    <div class="form-group">
                        <label>Writer</label>
                        <input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' readonly="readonly">
                    </div>
                    <sec:authentication property="principal" var="pinfo"/>
                    <sec:authorize access="isAuthenticated()">
                    	<c:if test="${pinfo.username eq board.writer }">
		                    <button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
		                    <button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
                    	</c:if>
                    </sec:authorize>
                    <button type="submit" data-oper='list' class="btn btn-info">List</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 첨부파일  -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Files</div>
            <div class="panel-body">
				<div class="form-group uploadDiv">
					<input type="file" name="uploadFile" multiple>
				</div>
				<div class="uploadResult">
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
var maxSize = 1024 * 1024 * 50;
var regex = new RegExp("(.*?)\.(sh|zip|alz)$");
var formObj = $('form'); 
var csrfHeaderName = '${_csrf.headerName}';
var csrfTokenValue = '${_csrf.token}';

$(document).ready(function(){
   function showImage(fileCallPath){
        $('.bigPictureWrapper').css('display', 'flex').show();

        $('.bigPicture')
            .html('<img src="/display?fileName='+fileCallPath+'">')
            .animate({width : '100%', height : '100%'}, 1000);
    }
   
    var bnoValue = '${board.bno}';
	
    $('.uploadResult').on('click', 'button', function(){
    	console.log('delete file');
    	
    	if(confirm('삭제 하시겠습니까?')){
    		var targetLi = $(this).closest('li');
    		targetLi.remove();
    	}
    });
    
    $.getJSON('/board/getAttachList',{bno : bnoValue},
        function(arr){
            var str = '';

            $(arr).each(function(i, attach){
			    var fileCallPath = encodeURIComponent(attach.uploadPath + '/s_' + attach.uuid + '_' + attach.fileName);
                if(attach.fileType){
				    str += '<li data-path="'+attach.uploadPath+'" data-uuid="'+attach.uuid+'" data-filename="'+attach.fileName+'" data-type="'+attach.fileType+'">';
				    str += '    <div>';
				    str += '        <span>'+attach.fileName+'</span>;'
				    str += '        <button class="btn btn-warning btn-circle" type="button" data-file="'+fileCallPath+'" data-type="image">';
				    str += '            <i class="fa fa-times"></i>';
				    str += '        </button><br>';
				    str += '        <img src="/display?fileName='+fileCallPath+'">';
				    str += '    </div>';
				    str += '</li>';
				}else{
				    str += '<li data-path="'+attach.uploadPath+'" data-uuid="'+attach.uuid+'" data-filename="'+attach.fileName+'" data-type="'+attach.fileType+'">';
				    str += '    <div>';
				    str += '        <span>'+attach.fileName+'</span>';
				    str += '        <button class="btn btn-warning btn-circle" type="button" data-file="'+fileCallPath+'" data-type="image">';
				    str += '            <i class="fa fa-times"></i>';
				    str += '        </button><br>';
				    str += '        <img src="/resources/img/attach.jpg">';
				    str += '    </div>';
				    str += '</li>';
				}
            });
            $('.uploadResult ul').html(str);
       }
    );

    $('.bigPictureWrapper').on('click', function(){
        $('.bigPicture').animate({width : '0%', height : '0%'},500);
        setTimeout(function(){
            $('.bigPictureWrapper').hide();
        });
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
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
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
    
    //$('button[data-oper=modify]').on('click', function(e){
    $('button').on('click', function(e){
    	e.preventDefault();
    	
    	var operation = $(this).data('oper');
    	
    	if(operation === 'remove'){
    		formObj.attr('action', '/borad/remove');
    	}else if(operation === 'list'){
    		var pageNumTag = $('input[name="pageNum"]').clone();
            var amountTag = $('input[name="amount"]').clone();
            var typeTag = $('input[name="type"]').clone();
            var keywordTag = $('input[name="keyWord"]').clone();

            formObj.attr('action', '/board/list').attr('method', 'get');
            formObj.empty();
            formObj.append(pageNumTag);
            formObj.append(amountTag);
            formObj.append(typeTag);
            formObj.append(keywordTag);
    	}else if(operation === 'modify'){
    		console.log('submit clicked!');
    		
    		var str ='';
    		$('.uploadResult ul li').each(function(i, obj){
    			var jObj = $(obj);
    			console.dir(jObj);
				
    			str += '<input type="hidden" name="attachList['+i+'].fileName" value="'+jObj.data('filename')+'">';
    			str += '<input type="hidden" name="attachList['+i+'].uuid" value="'+jObj.data('uuid')+'">';
    			str += '<input type="hidden" name="attachList['+i+'].uploadPath" value="'+jObj.data('path')+'">';
    			str += '<input type="hidden" name="attachList['+i+'].fileType" value="'+jObj.data('type')+'">';
   			});
    		
    		formObj.append(str);
    	}
    	formObj.submit();
    });
});
</script>
<%@include file="../includes/footer.jsp"%>
