<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <title>注册</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/RegisterServlet">
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
        <tr>
            <td>确认密码：</td>
            <td><input type="password" name="password2" value="${form.password2}">
                <span>${form.errors.password2}</span></td>
        </tr>
        <tr>
            <td>邮箱：</td>
            <td><input type="email" name="email" value="${form.email}">
                <span>${form.errors.email}</span></td>
        </tr>
        <tr>
            <td>生日：</td>
            <td><input type="date" name="birthday" value="${form.birthday}">
                <span>${form.errors.birthday}</span></td>
        </tr>
        <tr>
            <td>昵称：</td>
            <td><input type="text" name="nickname" value="${form.nickname}">
                <span>${form.errors.nickname}</span></td>
        </tr>
        <tr>
            <td>验证码：</td>
            <td><input type="text" name="captcha" value="${form.captcha}">
                <img src="${pageContext.request.contextPath}/CaptchaServlet" alt="验证码">
                <span>${form.errors.captcha}</span></td>
        </tr>
    </table>
    <div>
        <input type="reset" value="重置">&nbsp;&nbsp;
        <input type="submit" value="提交">
    </div>
</form>
</body>
</html>
