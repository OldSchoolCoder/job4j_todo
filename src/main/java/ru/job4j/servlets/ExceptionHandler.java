package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExceptionHandler extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request
                .getAttribute("javax.servlet.error.message");
        HttpSession session = request.getSession();
        session.setAttribute("statusCode", statusCode);
        session.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
