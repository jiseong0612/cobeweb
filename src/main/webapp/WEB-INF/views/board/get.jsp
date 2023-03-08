<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>
<%@include file="../includes/header.jsp"%>
<script src="/resources/js/reply.js"></script>

<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Register</h1>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board Read Page</div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label>
                    <input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Title</label>
                    <input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
                </div>

                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="3" name='content' readonly="readonly"><c:out value="${board.content}" /></textarea>
                </div>

                <div class="form-group">
                    <label>Writer</label>
                    <input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
                </div>
                <sec:authentication property="principal" var="pinfo"/>
                <sec:authorize access="isAuthenticated()">
                	<c:if test="${pinfo.username eq board.writer }">
		                <button data-oper='modify' class="btn btn-default">Modify</button>
                	</c:if>
                </sec:authorize>
                <button data-oper='list' class="btn btn-info">List</button>
            </div>
        </div>
    </div>
</div>
<!-- 첨부파일 -->
<div class="bigPictureWrapper">
    <div class="bigPicture"></div>
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

<!-- 댓글 -->
<div class='row'>
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> Reply1232
                <sec:authorize access="isAuthenticated()">
	                <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
                </sec:authorize>
            </div>
            <div class="panel-body">
                <ul class="chat">
                    <li class="left  clearfix" data-rno="12">
                        <div>
                            <div class="header">
                                <strong class="primary-font">user000</strong>
                                <small class="pull-right text-muted">2018-01-01 13: 13</small>
                            </div>
                            <p>good job</p>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="panel-footer"></div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
    aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name='reply' value='New Reply!!!!'>
                </div>
                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name='replyer' value='replyer'>
                </div>
                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name='replyDate' value='2018-01-01 13:13'>
                </div>
            </div>
            <div class="modal-footer">
                <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
                <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
                <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
                <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
            </div>
        </div>
    </div>
</div>
<form id="operForm" action="/board/modify" method="get">
    <input type="hidden" id="pageNum" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
    <input type="hidden" id="amount" name="amount" value='<c:out value="${cri.amount }"/>'>
    <input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno }"/>'>
    <input type="hidden" name="type" value="${cri.type }">
    <input type="hidden" name="keyWord" value="${cri.keyWord }">
</form>
<script>
    $(document).ready(function() {
        var operForm = $('#operForm');
        var bnoValue = '<c:out value="${board.bno}"/>';
        var replyUL = $('.chat');
        var modal = $('.modal');
        var modalInputReply = modal.find('input[name="reply"]');
        var modalInputReplyer = modal.find('input[name="replyer"]');
        var modalInputReplyDate = modal.find('input[name="replyDate"]');

        var modalModBtn = $('#modalModBtn');
        var modalRemoveBtn = $('#modalRemoveBtn');
        var modalRegisterBtn = $('#modalRegisterBtn');

        var pageNum = 1;
        var replyPageFooter = $('.panel-footer');

        showList(1);

        $.getJSON("/board/getAttachList", {bno : bnoValue},
            function(arr){
                var str = '';

                $(arr).each(function(i, attach){
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
                });

                $('.uploadResult ul').html(str);
            }
        );

        $('.uploadResult').on('click', 'li', function(){
            var liObj = $(this);

            var path = encodeURIComponent(liObj.data('path')+'/'+liObj.data('uuid')+'_'+liObj.data('filename'));

            if(liObj.data('type')){
                showImage(path.replace(new RegExp(/\\/g),'/'));
            }else{
                window.location ='/download?fileName='+path;
            }
        });

        $('button[data-oper="modify"]').on('click', function(){
            operForm.submit();
        });

        $('button[data-oper="list"]').on('click', function(){
            operForm.find('#bno').remove();
            operForm.attr('action', '/board/list');
            operForm.submit();
        });

        $('#addReplyBtn').on('click', function(){
            modal.find('input').val('');
            modalInputReplyer.removeAttr('readonly');
            modalInputReplyDate.closest('div').hide();
            modal.find('button[id != "modalCloseBtn"]').hide();
            modalRegisterBtn.show();

            modal.modal('show');
        });

        $('#modalRegisterBtn').on('click', function(){
            replyService.add(
                {
                    bno : bnoValue,
                    reply : modalInputReply.val(),
                    replyer : modalInputReplyer.val()
                },

                function(result){
                    alert(result);
                    modal.find('input').val('');
                    modal.modal('hide');

                    showList(-1);
                }
            );
        });

        $('#modalModBtn').on('click', function(){
            replyService.update(
                {
                    rno : modal.data('rno'),
                    reply : modalInputReply.val()
                },
                function(result){
                    alert(result);
                    modal.modal('hide');
                    showList(pageNum);
                }
            );
        });

        $('#modalRemoveBtn').on('click', function(){
            replyService.remove(
                modal.data('rno'),
                function(result){
                    alert(result);
                    modal.modal('hide');
                    showList(pageNum);
                }
            );
        });

        $('.chat').on('click', 'li', function(){
            var rno = $(this).data('rno');

            replyService.get(
                rno,
                function(reply){
                    modalInputReply.val(reply.reply);
                    modalInputReplyer.val(reply.replyer).attr('readonly', 'readonly');
                    modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr('readonly', 'readonly');
                    modal.data('rno', reply.rno);

                    modal.find('button[id != "modalCloseBtn"]').hide();
                    modalModBtn.show();
                    modalRemoveBtn.show();

                    modal.modal('show');
                }
            );
        });

        $('#modalCloseBtn').on('click', function(){
            modal.modal('hide');
        });

        $('.panel-footer').on('click', 'li a', function(e){
            e.preventDefault();
            var targetPageNum = $(this).attr('href');

            pageNum = targetPageNum;

            showList(pageNum);
        });

        $('.bigPictureWrapper').on('click', function(){
            $('.bigPicture').animate({width : '0%', height : '0%'},500);
            setTimeout(function(){
                $('.bigPictureWrapper').hide();
            });
        });

        function showImage(fileCallPath){
            $('.bigPictureWrapper').css('display', 'flex').show();

            $('.bigPicture')
                .html('<img src="/display?fileName='+fileCallPath+'">')
                .animate({width : '100%', height : '100%'}, 1000);
        }

        function showList(page){
            replyService.getList(
                {
                    bno : bnoValue,
                    page : page|| 1
                },
                function(replyCnt, list){

                    //마지막 페이지를 구하는거
                    if(page == -1){
                        pageNum = Math.ceil(replyCnt/10.0);
                        showList(pageNum);
                        return;
                    }

                    var str = '';

                    if(list ===  null || list.length === 0){
                        replyUL.html('첫 댓글의 주인공이 되세요.');
                        return;
                    }

                    for(var i = 0; i < list.length; i++){
                        str += '<li class="left  clearfix" data-rno="'+list[i].rno+'">';
                        str += '<div>';
                        str += '	<div class="header">';
                        str += '		<strong class="primary-font">'+list[i].replyer+'</strong>';
                        str += '		<small class="pull-right text-muted">'+replyService.displayTime(list[i].replyDate)+'</small>';
                        str += '	</div>';
                        str += '	<p>'+list[i].reply+'</p>';
                        str += '</div>';
                        str += '</li>';
                    }
                    replyUL.html(str);
                    showReplyPage(replyCnt);
                }
            );
        };

        function showReplyPage(replyCnt){
            var endNum = Math.ceil(pageNum / 10.0) * 10;
            var startNum  = endNum - 9;

            var prev = startNum != 1;
            var next = false;

            if(endNum * 10 >= replyCnt){
                endNum = Math.ceil(replyCnt / 10.0);
            }

            if(endNum * 10 < replyCnt){
                next = true;
            }

            var str =  '<ul class="pagination pull-right">';

            if(prev){
                str += '<li class="page-item"><a class="page-link" href="'+(startNum -1)+'">Previous</a></li>';
            }

            for(var i = startNum; i<=endNum; i++){
                var active = pageNum == i ? "active": "";

                str += '<li class="page-item '+active+'"><a class="page-link" href="'+i+'">'+i+'</a></li>';
            }

            if(next){
                str += '<li class="page-item"><a class="page-link" href="'+(endNum +1)+'">Next</a></li>';
            }

            str += '</ul>';

            replyPageFooter.html(str);
        }

    });
</script>
<%@include file="../includes/footer.jsp"%>
