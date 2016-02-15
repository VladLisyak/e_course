package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.validation.UserValidator;
import ua.nure.lisyak.SummaryTask4.util.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {Constants.ServletPaths.User.LOGIN})
public class LoginServlet extends BaseServlet{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if (user != null) {
            redirectToAction(Constants.ServletPaths.User.COURSE_LIST, req, resp);
            return;
        }
        forward(Constants.Pages.User.LOGIN, req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getCurrentUser(req)!=null){
            LOGGER.error("User already logged in");
            redirectToAction(Constants.ServletPaths.User.COURSE_LIST, req, resp);
            return;
        }

        String login = getStringParam(req, Constants.Attributes.LOGIN);
        String password = getStringParam(req, Constants.Attributes.PASSWORD);

        Validator validator = new UserValidator(login, password, getLocale(req));

        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
        }

        User user = getUserService().getByLogin(login);
        if ( user== null || !user.getLogin().equals(login)) {
            validator.putIssue("login", "validator.invalidLogin");
            sendError(req, resp, validator);
            return;
        }
        LOGGER.debug("User found. The id is {} and login is {}", user.getId(), user.getLogin());

        if (!user.getPassword().equals(password)) {
            validator.putIssue("password", "validator.invalidPassword");
            sendError(req, resp, validator);
            return;
        }

        switch (user.getEnabled()){
            case WAITING:{
                validator.putIssue("enabled", "validator.confirmation");
                sendError(req, resp, validator);
                /*forward(Constants.Pages.ENABLED_ISSUE, req, resp);*/
                return;
            }
            case BANNED:{
                validator.putIssue("enabled", "validator.banned");
                sendError(req, resp, validator);
                /*forward(Constants.Pages.ENABLED_ISSUE, req, resp);*/
                return;
            }
        }

        setCurrentUser(req, user);

        redirectToAction(Constants.ServletPaths.User.COURSE_LIST, req, resp);

    }

}
