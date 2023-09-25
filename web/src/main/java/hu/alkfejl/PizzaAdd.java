package hu.alkfejl;

import hu.alkfejl.controller.PizzaController;
import hu.alkfejl.controller.UserController;
import hu.alkfejl.model.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pizzaAdd")
public class PizzaAdd extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("pizza_name");
        String cost = req.getParameter("pizza_cost");
        String top = req.getParameter("pizza_top");

        if (name.isEmpty() || cost.isEmpty() || top.isEmpty()){
            return;
        }
        if (Integer.valueOf(cost) < 0){
            return;
        }

        String dbURL = getServletContext().getInitParameter("dbURL");
        UserController uc = UserController.getInstance(dbURL);
        String token = null;

        Cookie cookies[] = req.getCookies();
        for (Cookie c : cookies){
            if("token".equals(c.getName())){
                token = c.getValue();
            }
        }

        if(token == null){
            resp.sendRedirect("index.jsp");
            return;
        }
        if (uc.getUserByToken(token) == null){
            resp.sendRedirect("index.jsp");
            return;
        }

        PizzaController pc = PizzaController.getInstance(dbURL);

        Pizza pizz = new Pizza();
        pizz.setName(name);
        pizz.setCost(Integer.valueOf(cost));
        pizz.setToppings(top);

        if (pc.savePizza(pizz)){
            resp.addCookie(new Cookie("token", token));
            resp.sendRedirect("home.jsp");
        }else{
            //todo valami hiba
        }

    }
}
