package ru.job4j.servlets;

import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;
import ru.job4j.store.HbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HbStore store = new HbStore();
        User user = (User) req.getSession().getAttribute("user");
        String overview = req.getParameter("overview");
        List<Category> categoryList = new ArrayList<>();
        String[] categories = req.getParameterValues("category");
        List<String> stringList = Arrays.asList(categories);
        stringList.forEach(s -> categoryList.add(store.getCategoryById(Integer.parseInt(s))));
        Item item = new Item(overview, false, user, categoryList);
        store.add(item);
    }
}
