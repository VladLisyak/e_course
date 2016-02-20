package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.service.CourseService;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(Constants.ServletPaths.User.PROFILE)
public class ProfileServlet extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(getCurrentUser(req)!=null){
        User currentUser = getCurrentUser(req);
        CourseService cs = getCourseService();
        req.getSession().setAttribute(Constants.Attributes.COURSES_OF_CURRENT_USER_COUNT, cs.getAllByStudentId(currentUser.getId()).size());
        req.getSession().setAttribute(Constants.Attributes.AVERAGE_MARK, getCourseService().getStudentAverageMark(getCurrentUser(req).getId()));

            List<JournalEntry> userEntries = getJournalEntryService().getAllByStudentId(currentUser.getId());
            List<JournalEntry> finishedCourses = new ArrayList<>();
            List<JournalEntry> activeAndBeforeStartCourses = new ArrayList<>();

            for (JournalEntry entry:userEntries) {
                Date currDate = new Date();
                if(entry.getEndDate().compareTo(currDate)==0 || entry.getEndDate().compareTo(currDate)<0){
                    finishedCourses.add(entry);
                }else{
                    activeAndBeforeStartCourses.add(entry);
                }
            }
            req.getSession().setAttribute(Constants.Attributes.FINISHED_USER_COURSES, finishedCourses);
            req.getSession().setAttribute(Constants.Attributes.ELSE_USER_COURSES, activeAndBeforeStartCourses);
        forward(Constants.Pages.User.PROFILE, req, resp);
        }else{
            redirectToAction(Constants.ServletPaths.User.LOGIN, req, resp);
        }
    }
}
