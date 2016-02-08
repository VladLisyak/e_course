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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.User.LOGIN})
public class LoginServlet extends BaseServlet{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.error("User already logged in");
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
        LOGGER.error("in Post");

        String login = getStringParam(req, Constants.Attributes.LOGIN);
        String password = getStringParam(req, Constants.Attributes.PASSWORD);

        Validator validator = new UserValidator(login, password, getLocale(req));

        if (validator.hasErrors()) {
            setErrors(req, resp, validator, login, password);
            return;
        }

        User user = getUserService().getByLogin(login);
        if (!user.getLogin().equals(login)) {
            validator.putIssue("login", "validator.invalidLogin");
            setErrors(req, resp, validator, login, password);
            return;
        }
        LOGGER.debug("User found. The id is {} and login is {}", user.getId(), user.getLogin());

        if (!user.getPassword().equals(password)) {
            validator.putIssue("password", "validator.invalidPassword");
            setErrors(req, resp, validator, login, password);
            return;
        }

        switch (user.getEnabled()){
            case WAITING:{
                validator.putIssue("enabled", "validator.confirmation");
                forward(Constants.Pages.ENABLED_ISSUE, req, resp);
                break;
            }
            case BANNED:{
                validator.putIssue("enabled", "validator.banned");
                forward(Constants.Pages.ENABLED_ISSUE, req, resp);
            }
        }

        setCurrentUser(req, user);

        redirectToAction(Constants.ServletPaths.User.COURSE_LIST, req, resp);

    }


    /**
     * Sets errors which prevents logging and
     * forward back to validation form
     *
     * @param validator {@link Validator} entity that contains errors to be set.
     * @param login login of account that the user trying to get access to.
     * @param password password of account that the user trying to get access to
     *
     * @throws ServletException
     * @throws IOException
     */
    private void setErrors(HttpServletRequest request, HttpServletResponse response, Validator validator, String login, String password)
            throws ServletException, IOException {
        //request.setAttribute("messages", validator.getMessages());

        LOGGER.trace("Errors found while logging in. With login {}", login);

        List<String> messages = new ArrayList<>();

        /*for (Map.Entry<String, String> message : validator.getMessages().entrySet()) {
            messages.add(message.getValue());
        }*/

        print(request, response, validator.getMessages());
/*
        request.setAttribute("login", login);
        request.setAttribute("password", password);*/

        //forward(PagesPaths.Main.LOGIN, request, response);
    }

}
