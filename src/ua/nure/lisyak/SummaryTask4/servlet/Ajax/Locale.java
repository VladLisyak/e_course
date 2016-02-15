package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {Constants.ServletPaths.LOCALE})
public class Locale extends BaseServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newLocale = req.getParameter(Constants.Attributes.NEW_LOCALE);
        req.getSession().setAttribute(Constants.Attributes.CURRENT_LOCALE, newLocale);

        if(Arrays.asList(getLocales()).contains(newLocale)){
            req.getRequestDispatcher("locales/"+newLocale + ".json").forward(req,resp);
        }

    }
}
