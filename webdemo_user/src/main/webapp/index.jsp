<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: "Microsoft YaHei UI", serif;
            font-size: 1em;
        }

        #header {
            padding: 5px 20px;
            text-align: right;
            background-color: #fafafa;
        }

        main {
            text-align: center;
        }
    </style>
</head>
<body>
<header id="header">
    <c:choose>
        <c:when test="${user != null}">
            欢迎您，${user.nickname}
            <a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/RegisterUIServlet">注册</a>
            <a href="${pageContext.request.contextPath}/LoginUIServlet">登录</a>
        </c:otherwise>
    </c:choose>
</header>
<main>
    <h1>XXXXXXXXXX</h1>
    <p>xxxxxxxxxxxxxxxxxxxxxxxx</p>
</main>
</body>
</html>
