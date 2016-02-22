package ua.nure.lisyak.SummaryTask4.servlet.Simple.tutor;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.Tutor.JOURNAL)
public class JournalEntryServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(getCurrentUser(req)!=null){
                req.setAttribute(Constants.Attributes.COURSES_OF_CURRENT_USER_COUNT, getCourseService().getAllByTutorId(getCurrentUser(req).getId()).size());
                forward(Constants.Pages.Tutor.JOURNAL, req, resp);
                return;
            }
        redirectToAction(Constants.ServletPaths.Tutor.LOGIN, req, resp);
    }
}
