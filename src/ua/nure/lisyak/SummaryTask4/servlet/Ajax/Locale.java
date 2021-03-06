package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Servlet for instant locale changing
 */
@WebServlet(urlPatterns = {Constants.ServletPaths.LOCALE})
public class Locale extends BaseServlet{
    private static final Logger LOGGER = LoggerFactory.getLogger(Locale.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        print(req, resp, getLocale(req));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newLocale = req.getParameter(Constants.Attributes.NEW_LOCALE);
        req.getSession().setAttribute(Constants.Attributes.CURRENT_LOCALE, newLocale);
        req.setAttribute(Constants.Attributes.CURRENT_LOCALE, newLocale);
        LOGGER.debug("locale changed to {}", newLocale);
        if(Arrays.asList(getLocales()).contains(newLocale)){
            req.getRequestDispatcher("locales/"+newLocale + ".json").forward(req,resp);
        }

    }
}
