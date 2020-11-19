<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head>
    <title>文件上传</title>
</head>
<body>
    <h3>文件上传</h3><br><br>
 
    <form action="upload.do" method="post" enctype="multipart/form-data" >
        <input type="file" name="file"/><br>
        <input type="submit" value="上传">
    </form>
 
 
 
</body>
</html>