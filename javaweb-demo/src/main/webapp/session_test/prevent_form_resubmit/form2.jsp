<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>通过服务器端阻止表单重复提交</title>
</head>
<body>
<form method="post" action="/javaweb-demo/SubmitFormServlet">
    <input type="hidden" name="token" value="${token}">
    用户名：<input type="text" name="username"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
