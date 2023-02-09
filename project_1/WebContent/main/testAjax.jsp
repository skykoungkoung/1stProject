<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	// jquery 작성 기본구조 
	$(document).ready(function(){
		/* $("#btn").click(function(){ // id속성값이 btn인 요소를 click하면 function() 실행 
			// ajax
		}); */
	});
	//{a : "hello", b : 10, c : function(){}, d:[10,20,30]};
	
	function test() {
		var number = $("#val").val(); // 숨겨진 고유번호 가져오기 
		console.log(number);
		$.ajax({
			type: "POST",
			url: "testAjaxPro.jsp",
			data : "number="+number,
			success: function(data){
				if(data == 1){
					console.log("hahahaha");
				}
				console.log(data);
			},
			error : function(e) {
				console.log(e);
			}
		});
	}

</script>
</head>
<body>

	<button id="btn" onclick="test()">좋아요</button>
	<input type="hidden" id="val"  value="100" />
</body>
</html>