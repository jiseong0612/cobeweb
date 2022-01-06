<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var replyService = (function(){
		function add(reply, callback){
			$.ajax({
				url : "/replies/new",
				type : "post",
				data : JSON.stringify(reply),
				contentType :"application/json; charset=utf-8",
				success : function(result){
					if(callback){
						callback(result);
					}
				},
				error : function(error){
					console.log("error................");
				}
			});
		}
		
		function getList(param, callback){
			var bno = param.bno;
			var page = param.page;
			$.ajax({
				url : "/replies/pages/"+bno+"/"+page,
				type : "get",
				data : { bno : bno , page : page},
				contentType :"application/json; charset=utf-8",
				success : function(result){
					if(callback){
						callback(result);
					}
				},
				error : function(error){
					console.log("error................");
				}
			});
		}
		
		return {
			add :add,
			getList : getList
		};

	})();
</script>