<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生列表</title>
</head>
<body>
<p>下列表格显示所有学生</p>
<table border="1" width="500">
	<tr>
		<td>学号</td><td>姓名</td><td>年龄</td>
	</tr>
	<c:forEach items="${ students}" var="student">
	<tr>
		<td>${student.id }</td>
		<td>${student.name }</td>
		<td>${student.age }</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>