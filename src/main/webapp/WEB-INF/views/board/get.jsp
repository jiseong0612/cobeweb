<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>
<%@ include file="../reply/replyJs.jsp" %>
<script>
var displayTime = function(timeValue){
	var today = new Date();
	var gap = today.getTime() - timeValue;
	var dateObj = new Date(timeValue);
	var str = '';
	
	console.log(gap >(24* 60 * 60 * 1000));
	
 	if(gap > (24* 60 * 60 * 1000)){
		var yy = dateObj.getFullYear();
		var mm = dateObj.getMonth() + 1;
		var dd = dateObj.getDate();
		mm = ( mm < 9)? '0'+mm : mm;
		dd = ( dd < 9)? '0'+dd : dd;
		
		return yy+"/"+mm+"/"+dd;
	}else{
		var hh = dateObj.getHours();
		var mi = dateObj.getMinutes();
		var ss = dateObj.getSeconds();
		
		return hh+":"+mi+":"+ss;
	}
	
} 

var showList = function(page){
	replyService.getList(
		{
			bno : $("#bno").val(), 
			page : (page > 1)? page : 1
		},
		function(list){
			var resultHtml = '';
			
			list.forEach(function(obj, i){
				resultHtml +="<li class='left clearfix' data-rno='"+obj.rno+"'>";
				resultHtml +="  <div><div class='header'><strong class='primary-font'>"+obj.replyer+"</strong>"; 
				resultHtml +="    <small class='pull-right text-muted'>" +displayTime(obj.replyDate)+"</small></div>";
				resultHtml +="    <p>"+obj.reply+"</p></div></li>";
			});
			
			$(".chat").append(resultHtml)
		}
	);
}


$(document).ready(function(){
	showList(1);
	/* replyService.add(
		{
			reply:"test",
			replyer:"tests,,,,",
			bno : $("#bno").val()
		},
		function(result){
			alert("result : "+ result);
		}
	) */
	
	$(".listBtn").on("click", function(){
		var operation = $(this).data("oper");
		
		if(operation === 'list'){
			$("#bno").remove();
			$("#hddnFrm").submit();
		}else{
			$("#hddnFrm").attr("action","/board/modify");
			$("#hddnFrm").submit();
		}
	});
});
</script>
<form id="hddnFrm" method="get" action="/board/list">
	<input type="hidden" id="pageNum" 		name="pageNum" 		value="${cri.pageNum }">
	<input type="hidden" id="amount"  		name="amount"  		value="${cri.amount}">
	<input type="hidden" id="keyword"  		name="keyword"  	value="${cri.keyword}">
	<input type="hidden" id="type"  		name="type"  		value="${cri.type}">
	<input type="hidden" id="bno" 			name="bno" 			value="${board.bno }">
</form>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read</div>
			<div class="panel-body">
				<div class="form-group">
					<label>글번호</label> 
					<input class="form-control" name='bno' readonly="readonly" value="${board.bno }">
				</div>
				<div class="form-group">
					<label>작성자</label> 
					<input class="form-control" name='writer' readonly="readonly" value="${board.writer }">
				</div>
				<div class="form-group">
					<label>제목</label> <input class="form-control" readonly="readonly" name='title' value="${board.title }">
				</div>
				<div class="form-group">
					<label>내용</label>
					<textarea class="form-control" rows="3" readonly="readonly" name='content'>${board.content }</textarea>
				</div>
				<button type="button" class="btn btn-default listBtn" data-oper="modify">modify</button>
				<button type="button" class="btn btn-info listBtn" 	  data-oper="list">List</button>
			</div>

		</div>
	</div>
</div>
<!-- 댓글 div  시작 -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-comments fa-fw"></i>
							댓글
						<button id="addReplyBtn" class="btn btn-primary btn-xs pull-right"> 댓글 달기</button>
					</div>
					<div class="panel-body">
						<ul class="chat" id="chatList">
							<!-- 댓글이 여기에 달려야한다. -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<!-- 댓글 끝 -->
<%@include file="../includes/footer.jsp"%>
