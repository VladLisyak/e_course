package ua.nure.lisyak.SummaryTask4.servlet.user;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {Constants.ServletPaths.LOGIN})
public class UserHomeServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        if (user != null) {
            redirectToAction(Constants.ServletPaths.COURSE_LIST, req, resp);
            return;
        }
        forward(Constants.Pages.User.LOGIN, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /* String login = req.getParameter("login");
        String password = req.getParameter("password");
        String locale = getLocale(req);
        Validator validator = new UserValidator(login, password, getTranslator(), locale);
        if (validator.hasErrors()) {
            forwardBack(req, resp, validator, login, password);
            return;
        }
        User user = getUserService().getByLogin(login);
        if (user == null) {
            validator.put("login", "validate.invalidLogin");
            forwardBack(req, resp, validator, login, password);
            return;
        }
        if (!user.getPassword().equals(hash(password))) {
            validator.put("password", "validate.invalidPassword");
            forwardBack(req, resp, validator, login, password);
            return;
        }
        if (user.getRole() != UserRole.USER) {
            validator.put("error", "validate.noRights");
            forwardBack(req, resp, validator, login, password);
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("dsd", "dsd");
        setCurrentUser(req, user);
        redirectToAction(Actions.Main.INDEX, req, resp);
    }

    private void forwardBack(HttpServletRequest req, HttpServletResponse resp, Validator validator, String login, String password)
            throws ServletException, IOException {
        req.setAttribute("messages", validator.getMessages());
        req.setAttribute("login", login);
        req.setAttribute("password", password);
        forward(Pages.Main.LOGIN, req, resp);
    }*/

    }

}
