<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>
<script>

$(document).ready(function(){
	$(".btn").on("click", function(e){
		e.preventDefault();
		
		var formObj = $("form");
		var operation = $(this).data("oper");
		
		if(operation === 'list'){
			$("#hddnFrm").attr("method","get").attr("action","/board/list");
			formObj.submit();
		}else if(operation === 'remove'){
			formObj.attr("method","post").attr("action", "/board/remove");
			formObj.submit();
		}else{
			formObj.attr("method","post").attr("action", "/board/modify");
			formObj.submit();
		}
	});
});
</script>
<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Read</h1>
  </div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Modify</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form id="hddnFrm" method="post" action="/board/modify">
					<input type="hidden" name="pageNum" value="${cri.pageNum }">
					<input type="hidden" name="amount" 	value="${cri.amount }">
					<div class="form-group">
						<label>글번호</label> <input class="form-control" name='bno' readonly="readonly" value="${board.bno }">
					</div>
					<div class="form-group">
						<label>작성자</label> <input class="form-control" name='writer' readonly="readonly" value="${board.writer }">
					</div>
					<div class="form-group">
						<label>제목</label> <input class="form-control" name='title' value="${board.title }">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea class="form-control" rows="3" name='content'>${board.content }</textarea>
					</div>
					<button  class="btn btn-default" data-oper="modify">Modify</button>
					<button  class="btn btn-danger"  data-oper="remove">Remove</button>
					<button  class="btn btn-info"    data-oper="list">List</button>
				</form>
			</div>
		</div>
	</div>
</div>
<%@include file="../includes/footer.jsp"%>