package ua.nure.lisyak.SummaryTask4.servlet.Simple.tutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.validation.UserValidator;
import ua.nure.lisyak.SummaryTask4.util.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.Tutor.LOGIN)
public class LoginServlet extends BaseServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if(user!=null && user.getRoles().contains(Role.TUTOR)){
            redirectToAction(Constants.ServletPaths.Tutor.JOURNAL, req, resp);
            return;
        }
        forward(Constants.Pages.Tutor.LOGIN, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if (user!=null && user.getRoles().contains(Role.TUTOR)){
            LOGGER.error("Tutor already logged in");
            redirectToAction(Constants.ServletPaths.Tutor.JOURNAL, req, resp);
            return;
        }

        String login = getStringParam(req, Constants.Attributes.LOGIN);
        String password = getStringParam(req, Constants.Attributes.PASSWORD);

        Validator validator = new UserValidator(login, password, getLocale(req));

        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
        }

        user = getUserService().getByLogin(login);
        if ( user == null || !user.getLogin().equals(login)) {
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

        if(!user.getRoles().contains(Role.TUTOR)){
            validator.putIssue("rights", "validator.noRights");
            sendError(req, resp, validator);
            return;
        }

        setCurrentUser(req, user);

        redirectToAction(Constants.ServletPaths.Tutor.JOURNAL, req, resp);
    }
}
