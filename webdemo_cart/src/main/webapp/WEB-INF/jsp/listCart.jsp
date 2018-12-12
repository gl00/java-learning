<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>购物车列表</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            text-align: center;
            text-overflow: ellipsis;
        }

        table {
            margin: 0 auto;
            min-width: 70%;
        }

        tfoot {
            text-align: right;
        }
    </style>
</head>
<body>
<h1>购物车列表</h1>
<c:choose>
    <c:when test="${empty(cart.map)}">
        您没有购买任何商品。
        <a href="${pageContext.request.contextPath}/ListBookServlet">查看商品列表</a>
    </c:when>
    <c:otherwise>
        <table border="1">
            <thead>
            <th>标题</th>
            <th>版本</th>
            <th>作者</th>
            <th>单价</th>
            <th>数量</th>
            <th>操作</th>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${cart.map}">
                <tr>
                    <td>${entry.value.book.title}</td>
                    <td>${entry.value.book.edition}</td>
                    <td>${entry.value.book.author}</td>
                    <td>${entry.value.book.price}</td>
                    <td><input type="number" min="1" value="${entry.value.quantity}"
                               onchange="changeQuantity(this, ${entry.key}, ${entry.value.quantity})"></td>
                    <td><a href="javascript:void(0);" onclick="deleteItem(${entry.key})">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="6">
                    <span>总价：</span>
                    <span>${cart.total}</span>
                    <span><a href="">结账</a></span>
                    <a href="javascript:void(0);" onclick="clearCart()">清空购物车</a>
                </td>
            </tr>
            </tfoot>
        </table>
    </c:otherwise>
</c:choose>

<script>
    function deleteItem(id) {
        if (window.confirm("您确认要从购物车中删除此商品吗？")) {
            window.location.href = "${pageContext.request.contextPath}/DeleteCartItemServlet?id=" + id;
        }
    }

    function clearCart() {
        if (window.confirm("您确定要清空购物车吗？")) {
            window.location.href = "${pageContext.request.contextPath}/ClearCartServlet";
        }
    }

    function changeQuantity(input, id, oldValue) {
        var newValue = input.value;
        if (newValue === null || newValue != parseInt(newValue)) {
            window.alert("请输入正整数");
            input.value = oldValue;
            return;
        }
        window.location.href = "${pageContext.request.contextPath}/ChangeQuantityServlet?id=" + id + "&quantity=" + newValue;
    }
</script>
</body>
</html>
