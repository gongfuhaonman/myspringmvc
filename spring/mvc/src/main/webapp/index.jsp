<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<meta charset="UTF-8">
<head>
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#bt").click(function(){
			$.get("/test2.do?a=1&b=2",function(c){
				c;
				$("#info").html(c);
			});
		})
	});
</script>
</head>
<body>
<input id="bt" type="button" value="显示"> <br>
<span id="info"></span>
</body>
</html>