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
            <div class="panel-heading">Board Modify</div>
            <div class="panel-body">
                <form role="form" action="/board/modify" method="post">
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
                    <button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
                    <button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>
                    <button type="submit" data-oper='list' class="btn btn-info">List</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Files</div>
            <div class="panel-body">
                <div class='uploadResult'>
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
$(document).ready(function(){
    var bnoValue = '${board.bno}';

    $('button').on('click', function(e){
        e.preventDefault();

        var formObj = $('form');
        var operation = $(this).data('oper');

        if(operation ==='remove'){
            formObj.attr('action', '/board/remove');
        }else if(operation ==='list'){
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
        }

        formObj.submit();
    });

    $.getJSON('/board/getAttachList',{bno : bnoValue},
        function(arr){
            var str = '';

            $(arr).each(function(i, attach){
                if(attach.fileType){
                    if(attach.fileType){
                        var fileCallPath = encodeURIComponent(attach.uploadPath + '/s_' + attach.uuid + '_' + attach.fileName);
                        str += '<li data-path="'+attach.uploadPath+'" data-uuid="'+attach.uuid+'" data-filename="'+attach.fileName+'" data-type="'+attach.fileType+'">';
                        str += '    <div>';
                        str += '        <img src="/display?fileName='+fileCallPath+'">';
                        str += '    </div>';
                        str += '</li>';
                    }else{
                        str += '<li data-path="'+attach.uploadPath+'" data-uuid="'+attach.uuid+'" data-filename="'+attach.fileName+'" data-type="'+attach.fileType+'">';
                        str += '    <div>';
                        str += '        <span>'+attach.fileName+'</span></br>';
                        str += '        <img src="/resources/img/attach.jpg">';
                        str += '    </div>';
                        str += '</li>';
                    }
                }
            });
            $('.uploadResult ul').html(str);
       }
    );


});
</script>
<%@include file="../includes/footer.jsp"%>
