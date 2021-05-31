package ru.job4j.servlets;

import ru.job4j.filter.AuthFilter;
import ru.job4j.model.User;
import ru.job4j.store.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AuthServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HbStore store = new HbStore();
        User user = store.findByEmail(email).get(0);
        if (user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            req.getRequestDispatcher("index.html").forward(req, resp);
        } else {
            throw new ServletException("Error! Bed email or password!");
        }
    }
}
