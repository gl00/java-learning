<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>书籍列表</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            text-align: center;
            text-overflow: ellipsis;
        }
    </style>
</head>
<body>
<h1>书籍列表</h1>
<table border="1">
    <thead>
    <th>ISBN</th>
    <th>标题</th>
    <th>版本</th>
    <th>作者</th>
    <th>出版社</th>
    <%--<th>简介</th>--%>
    <th>售价</th>
    <th>操作</th>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${books}">
        <tr>
            <td>${entry.value.isbn}</td>
            <td>${entry.value.title}</td>
            <td>${entry.value.edition}</td>
            <td>${entry.value.author}</td>
            <td>${entry.value.publisher}</td>
            <%--<td id="desc">${entry.value.description}</td>--%>
            <td>${entry.value.price}</td>
            <td><a href="${pageContext.request.contextPath}/BuyBookServlet?id=${entry.value.id}">购买</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
