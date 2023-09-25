<%@ page import="hu.alkfejl.model.User" %>
<%@ page import="hu.alkfejl.controller.UserController" %><%--
  Created by IntelliJ IDEA.
  User: valaki
  Date: 2023. 04. 27.
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
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

Üdvözöllek az oldalon <%= user.getUsername()%>!

<a href="pizzaAdd.jsp"><button>Pizza hozzaadasa</button></a>
<a href="pizzaList.jsp"><button>Pizza Listazasa</button></a>
</body>
</html>
