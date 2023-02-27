<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Tables</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				Board List Page
				<button id='regBtn' type="button" class="btn btn-xs pull-right">Register New Board</button>
			</div>

			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>#번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>수정일</th>
						</tr>
					</thead>

					<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}" /></td>
							<td>
								<a class='move' href='<c:out value="${board.bno}"/>'>
									<c:out value="${board.title}" />
									<b>[<c:out value="${board.replyCnt }"></c:out>]</b>
								</a>
							</td>
							<td>
								<c:out value="${board.writer}" />
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" />
							</td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" />
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<div class="row">
					<div class="col-lg-12">
						<form id="searchForm" action="/board/list" method="get">
							<select name="type">
								<option value="" ${pageMaker.cri.type == null? 'selected' : '' }>--</option>
								<option value="T" ${pageMaker.cri.type == 'T' ? 'selected' : '' }>제목</option>
								<option value="C" ${pageMaker.cri.type == 'C' ? 'selected' : '' }>내용</option>
								<option value="W" ${pageMaker.cri.type == 'W' ? 'selected' : '' }>작성자</option>
								<option value="TC" ${pageMaker.cri.type == 'TC' ? 'selected' : '' }>제목 or 내용</option>
								<option value="TW" ${pageMaker.cri.type == 'TW' ? 'selected' : '' }>제목 or 작성자</option>
								<option value="TCW" ${pageMaker.cri.type == 'TCW' ? 'selected' : '' }>제목 or 내용 or 작성자</option>
							</select>				
							<input type="text" name="keyWord" value="${pageMaker.cri.keyWord }">
							<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">		
							<input type="hidden" name="amount" value="${pageMaker.cri.amount }">		
							<button class="btn btn-default">Search</button>
						</form>
					</div>
				</div>
				
				<div class='pull-right'>
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
							<li class="paginate_button previous"><a href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>
						<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
							<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""} ">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>
						<c:if test="${pageMaker.next}">
							<li class="paginate_button next"><a href="${pageMaker.endPage +1 }">Next</a></li>
						</c:if>
					</ul>
				</div>

				<!-- Modal  추가 -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">Modal title</h4>
							</div>
							<div class="modal-body">처리가 완료되었습니다.</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save changes</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="actionForm" action="/board/list" method="get">
	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	<input type="hidden" name="type" value="${pageMaker.cri.type }">
	<input type="hidden" name="keyWord" value="${pageMaker.cri.keyWord }">
</form>

<script>
var checkModal = function(result) {
	if (result === '' || history.state)
		return;

	var bno = parseInt(result);

	if (bno > 0) {
		$('.modal-body').html('게시글' + bno + ' 번이 등록되었습니다.');
	}

	$('#myModal').modal('show');
};
	
$(document).ready(function(){
	var result = '<c:out value="${result}"/>';
	var actionForm = $('#actionForm');
	var searchForm = $('#searchForm');

	checkModal(result);

	history.replaceState({}, null, null);

	$('#regBtn').on('click', function() {
		window.location = '/board/register';
	});
	
	$('.move').on('click', function(e){
		e.preventDefault();
		actionForm.append('<input type="hidden" name="bno" value="'+$(this).attr('href')+'">');
		actionForm.attr('action', '/board/get');
		actionForm.submit();
	});
	
	
	$('.paginate_button a').on('click', function(e){
		e.preventDefault();
		actionForm.find('input[name="pageNum"]').val($(this).attr('href'));
		actionForm.submit();
	});
	
	$('#searchForm button').on('click', function(e){
		e.preventDefault();
		
		if(!searchForm.find('option:selected').val()){
			alert('검색종류를 선택하세요');
			return false;
		}
		
		if(!searchForm.find('input[name="keyWord"]').val()){
			alert('키워드를 입력하세요');
			return false;
		}
		
		searchForm.find('input[name="pageNum"]').val(1);
		searchForm.submit();
	});
});
</script>
<%@include file="../includes/footer.jsp"%>
