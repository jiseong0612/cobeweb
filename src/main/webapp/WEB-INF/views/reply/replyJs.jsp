<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var replyService = (function(){
		
		function get(rno, callback){
			var rno = rno;
			$.ajax({
				url : "/replies/"+rno,
				type : "get",
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
		function remove(rno, callback){
			$.ajax({
				url : "/replies/"+rno,
				type : "DELETE",
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
						callback(result.replyCnt, result.list);
					}
				},
				error : function(error){
					console.log("error................");
				}
			});
		}
		
		function update(param, callback){
			console.log(param);
			$.ajax({
				url : "/replies/"+param.rno,
				type : "PATCH",
				data : JSON.stringify({ reply : param.reply}),
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
			get : get,
			add :add,
			getList : getList,
			remove : remove,
			update : update
		};

	})();
</script>