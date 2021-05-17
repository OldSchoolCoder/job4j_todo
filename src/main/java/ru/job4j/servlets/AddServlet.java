package ru.job4j.servlets;

import ru.job4j.model.Item;
import ru.job4j.store.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbStore store = new HbStore();
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String overview = req.getParameter("overview");
        Item item = new Item(overview, new Timestamp(System.currentTimeMillis()), false);
        store.add(item);
    }
}
