package ru.job4j.servlets;

import ru.job4j.filter.AuthFilter;
import ru.job4j.model.User;
import ru.job4j.store.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbStore store = new HbStore();
        String email = req.getParameter("email");
        Optional<User> userOptional = store.findByEmail(email);
        if (userOptional.isEmpty()) {
            int id = 0;
            User user = new User();
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            user.setPassword(password);
            user.setEmail(email);
            user.setName(username);
            user.setId(id);
            store.add(user);
            req.getRequestDispatcher("login.html").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, " Registration Error! User already exists! ");
        }
    }
}
