package ua.nure.lisyak.SummaryTask4.servlet.Simple.admin;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.Admin.COURSES)
public class CoursesServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        req.setAttribute(Constants.Attributes.TUTORS, getUserService().getAllByRole(Role.TUTOR, user!=null?user.getId():0));
        forward(Constants.Pages.Admin.COURSES, req, resp);
    }
}
