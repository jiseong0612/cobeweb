<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

<script>
$(document).ready(function(){
	history.replaceState({}, null, null);
	
	var result = '<c:out value="${result}"/>';
	if(result.length >0){
		alert(result);
	}
	$("#regBtn").on("click", function() {
		location.href = "/board/register";
	});
	
	//페이지 번호 클릭시
	$(".page-link").on("click", function(e){
		e.preventDefault();
		
		var pageNum = $(this).attr("href");
		$("#pageNum").val(pageNum);
		
		$("#hddnFrm").submit();
	});
	
	//글번호 클릭시
	$(".move").on("click", function(e){
		var bno = $(this).attr("href");
		console.log(bno);
		e.preventDefault();
		$("#hddnFrm").append('<input type="hidden" id="bno" 	name=bno	value="'+bno+'">');
		$("#hddnFrm").attr("action","/board/get");
		$("#hddnFrm").submit();
	});
	
	$("#searchBtn").on("click", function(e){
		e.preventDefault();
		$("#pageNum").val(1); 						//검색시 1페이지로 이동
		$("#type").val($("#searchSelc").val());
		$("#keyword").val($("#searchInput").val());
		$("#hddnFrm").submit();
	});
	
	$("#searchInput").on("keyup",function(event){
		console.log(event);
		if(event.keyCode  === 13){
			$("#pageNum").val(1); 						//검색시 1페이지로 이동
			$("#type").val($("#searchSelc").val());
			$("#keyword").val($("#searchInput").val());
			$("#hddnFrm").submit();
		}
	})
	
});
</script>
<form id="hddnFrm" method="get" action="/board/list">
	<input type="hidden" id="pageNum" 	name="pageNum" 	value="${pageMaker.cri.pageNum }">
	<input type="hidden" id="amount" 	name="amount"	value="${pageMaker.cri.amount }">
	<input type="hidden" id="type" 		name="type"		value="${pageMaker.cri.type }">
	<input type="hidden" id="keyword" 	name="keyword"	value="${pageMaker.cri.keyword }">
</form>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tables</h1>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                DataTables Advanced Tables
                <button id='regBtn' type="button" class="btn btn-xs pull-right">Register
					New Board</button>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>bno</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>수정일</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="board">
                        <tr class="odd gradeX">
                            <td>${board.bno }</td>
                            <td><a class="move" href="${board.bno }">${board.title }<b>[ <c:out value="${board.replyCnt }"/> ]</b></a></td>
                            <td>${board.writer }</td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate }"/></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                	<select id="searchSelc">
                		<option value="TCW">전체</option>
                		<option value="T">제목</option>
                		<option value="C">내용</option>
                		<option value="W">작성자</option>
                	</select>
                	<input type="text" id="searchInput">
                	<button id="searchBtn" class="btn btn-default">search</button>
                <ul class="pagination">
	                <c:if test="${pageMaker.prev}">
	                	<li class="page-item">
	                		<a class="page-link" href="${pageMaker.startPage-1}" tabindex="-1">Previous</a>
	                	</li>
	                </c:if>
					<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num">
						<li class="page-item ${pageMaker.cri.pageNum == num? "active" : "" }">
							<a class="page-link" href="${num }">${num } </a>
						</li>	
					</c:forEach>
	                <c:if test="${pageMaker.next}">
	                	<li class="page-item">
	                		<a class="page-link" href="${pageMaker.endPage+1}" tabindex="-1">Next</a>
	                	</li>
	                </c:if>
                </ul>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>