package ru.job4j.servlets;

import ru.job4j.store.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbStore store = new HbStore();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        store.reverseDone(Integer.valueOf(id));
    }
}
