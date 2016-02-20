package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.User.TUTOR_DETAILS)
public class TutorDetailsServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Constants.Attributes.TUTOR, getUserService().get(getEntityId(req)));

        forward(Constants.Pages.User.TUTOR, req, resp);
    }
}
