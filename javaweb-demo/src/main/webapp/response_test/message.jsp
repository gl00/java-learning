<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提示</title>
</head>
<body>
<%
    String msg = (String) application.getAttribute("message");
    out.write(msg);
%>
</body>
</html>
