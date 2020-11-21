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
		<td>id</td><td>姓名</td><td>年龄</td><td>性别</td>
	</tr>
	<c:forEach items="${list}" var="student">
	<tr>
		<td>${student.getId()}</td>
		<td>${student.getName() }</td>
		<td>${student.getAge() }</td>
		<td>${student.getGender() }</td>
		
	</tr>
	</c:forEach>
</table>
</body>
</html>