<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <title>登录</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/LoginServlet">
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="username" value="${form.username}">
                <span>${form.errors.username}</span></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" value="${form.password}">
                <span>${form.errors.password}</span></td>
        </tr>
    </table>
    <div>
        <input type="reset" value="重置">&nbsp;&nbsp;
        <input type="submit" value="提交">
    </div>
</form>
</body>
</html>
