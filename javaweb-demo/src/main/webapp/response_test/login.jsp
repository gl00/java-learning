<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<form method="post" action="/javaweb-demo/ResponseSendRedirectTestServlet">
    <table>
        <tr>
            <td><label for="username">用户名：</label></td>
            <td><input id="username" type="text" name="username"></td>
        </tr>
        <tr>
            <td><label for="password">密&emsp;码：</label></td>
            <td><input id="password" type="password" name="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>
</body>
</html>
