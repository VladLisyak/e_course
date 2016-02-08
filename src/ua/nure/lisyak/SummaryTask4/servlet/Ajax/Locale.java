package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/locale"})
public class Locale extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String locale = (String) session.getAttribute(Constants.Attributes.CURRENT_LOCALE);

        switch (locale) {
            case "ru":
                req.getRequestDispatcher("locales/ru.json").forward(req,resp);
                break;
            case "en":
                req.getRequestDispatcher("locales/en.json").forward(req,resp);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newLocale = req.getParameter(Constants.Attributes.NEW_LOCALE);
        req.getSession().setAttribute(Constants.Attributes.CURRENT_LOCALE,newLocale);
    }
}
