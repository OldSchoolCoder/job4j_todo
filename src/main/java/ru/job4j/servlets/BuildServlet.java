package ru.job4j.servlets;

import ru.job4j.model.Item;
import ru.job4j.store.HbStore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> itemList = new HbStore().findAll();
        ObjectMapper mapper = new ObjectMapper();
        String itemsAsString = mapper.writeValueAsString(itemList);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        resp.getWriter().write(itemsAsString);
    }
}
