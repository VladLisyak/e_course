package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CourseServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Constants.Attributes.TUTORS, getUserService().getAllByRole(Role.TUTOR));
        req.setAttribute(Constants.Attributes.SEARCH_BY, Constants.SEARCH_TYPES);
        req.setAttribute(Constants.Attributes.SORT_BY, Constants.SORT_TYPES);
        req.setAttribute(Constants.Attributes.THEMES, Theme.values());

        forward(Constants.Pages.User.COURSES, req, resp);
    }
}
