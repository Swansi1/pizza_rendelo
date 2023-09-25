<%@ page import="hu.alkfejl.controller.UserController" %>
<%@ page import="hu.alkfejl.model.User" %>
<%@ page import="hu.alkfejl.model.Pizza" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="hu.alkfejl.controller.PizzaController" %><%--
  Created by IntelliJ IDEA.
  User: valaki
  Date: 2023. 04. 27.
  Time: 12:44
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
    List<Pizza> pizzak = new ArrayList<>();
    Integer price = Integer.MAX_VALUE;
%>

<%
    if (request.getParameter("price") != null && !request.getParameter("price").isEmpty()){
        price = Integer.valueOf(request.getParameter("price"));

    }


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
    PizzaController pz = PizzaController.getInstance(dbURL);
    pizzak = pz.getAllPizza();
%>
<form action="pizzaList.jsp">
     Ar <input type="number" name="price" required> <br>
    <input type="submit" value="Szűrés">
</form>
<table>
    <tr>
        <td>Név</td>
        <td>Cost</td>
        <td>Top</td>
    </tr>
    <%  for(Pizza pizza : pizzak) {
        if (pizza.getCost() > price){ continue;}
    %>
    <tr>
        <td><%= pizza.getName()%></td>
        <td><%= pizza.getCost()%></td>
        <td><%= pizza.getToppings()%></td>
    </tr>
    <% } %>
</table>

<a href="home.jsp"><button>Vissza</button></a>
</body>
</html>
