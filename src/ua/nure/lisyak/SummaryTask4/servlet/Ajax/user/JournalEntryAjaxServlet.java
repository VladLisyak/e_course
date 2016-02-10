package ua.nure.lisyak.SummaryTask4.servlet.Ajax.user;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.servlet.Ajax.BaseAjaxServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.User.SUBSCRIPTION})
public class JournalEntryAjaxServlet extends BaseAjaxServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getCurrentUser(request);
        Integer courseId = getEntityId(request);
        if(courseId != null){
            Course courseToSubscribe = getCourseService().get(courseId);
            boolean isSaved = false;
            if(courseToSubscribe!=null && !(courseToSubscribe.getStartDate().compareTo(new Date())>0)){
                JournalEntry entry = new JournalEntry();
                entry.setStudentId(user.getId());
                entry.setCourseId(courseId);
                isSaved = getJournalEntryService().save(entry)!=null;
            }
            print(request, response, isSaved);
        }else{
            response.sendError(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getCurrentUser(request);
        boolean isDeleted = getJournalEntryService().deleteByStudent(user.getId(), getIntParam(request, Constants.Attributes.COURSE_ID, 0));
        print(request, response, isDeleted);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getCurrentUser(request);
        List<JournalEntry> userCourses = getJournalEntryService().getAllByStudentId(user.getId());
        print(request, response, userCourses);
    }




}
