<%@ page import="hu.alkfejl.controller.UserController" %>
<%@ page import="hu.alkfejl.model.User" %><%--
  Created by IntelliJ IDEA.
  User: valaki
  Date: 2023. 04. 27.
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%!
    String token = null;
    User user = null;
%>

<%
    Cookie cookies[] = request.getCookies();
    for (Cookie c : cookies){
        if("token".equals(c.getName())){
            token = c.getValue();
        }
    }
    if (token == null){
        response.sendRedirect("index.jsp");
        return;
    }
    String dbURL =  config.getServletContext().getInitParameter("dbURL");
    UserController uc = UserController.getInstance(dbURL);

    user = uc.getUserByToken(token);
    if (user == null){
        response.sendRedirect("index.jsp");
        return;
    }
%>
<form action="pizzaAdd" method="post">
    Pizza name: <input type="text" name="pizza_name" required> <br>
    Pizza Cost: <input type="number" name="pizza_cost" min="0" required> <br>
    Pizza Toppings: <input type="text" name="pizza_top" required> <br>
    <input type="submit" value="Pizza mentese">
</form>
</body>
</html>
