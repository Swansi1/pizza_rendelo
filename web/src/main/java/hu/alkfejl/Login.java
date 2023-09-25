package hu.alkfejl;


import hu.alkfejl.controller.UserController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String dbURL = getServletContext().getInitParameter("dbURL");
        UserController uc = UserController.getInstance(dbURL);
        String token = uc.loginUser(username,password);
        if(token == null){
            PrintWriter out = resp.getWriter(); // így tudunk majd a válasz objektumba törzsébe írni
            out.println(
                    "<html>\n" +
                            "<head><title>Paraméterek használata</title></head>\n" +
                            "<body>\n" +
                            "Sikertelen bejelentkezés! <br> <a href='index.jsp'>Vissza</a>" +
                            "</body>" +
                            "</html>"
            );
            return;
        }

        Cookie c = new Cookie("token",token);
        resp.addCookie(c);
        resp.sendRedirect("home.jsp");
    }
}
