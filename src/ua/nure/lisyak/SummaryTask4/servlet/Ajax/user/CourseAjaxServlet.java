package ua.nure.lisyak.SummaryTask4.servlet.Ajax.user;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.servlet.Ajax.BaseAjaxServlet;
import ua.nure.lisyak.SummaryTask4.transferObjects.CourseWithSubscription;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.User.AJAX_COURSE_LIST})
public class CourseAjaxServlet extends BaseAjaxServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        List<CourseWithSubscription> courses = new ArrayList<>();
        Integer id = getEntityId(req);

        if(id!=null){
            print(req, resp, getCourseService().get(id));
        }
        else{
            String searchBy = getStringParam(req, Constants.Attributes.SEARCH_BY);
            String search = getStringParam(req, Constants.Attributes.SEARCH_PARAM);
            Integer limit = getIntParam(req, Constants.Attributes.LIMIT, SettingsAndFolderPaths.getCoursesPerPage());
            Integer offset = getIntParam(req, Constants.Attributes.OFFSET, 0);
            String sort = getStringParam(req, Constants.Attributes.SORT_BY, "id");
            String order = getStringParam(req, Constants.Attributes.ORDER, "asc");


            List<Course> studentSubscriptions = getCourseService().getAllByStudentId(user.getId());
            List<Course> filteredCourses = getCourseService().getFiltered(offset,limit,searchBy,search,sort,order);

            for (Course course : filteredCourses) {
                courses.add(studentSubscriptions.contains(course)
                        ? new CourseWithSubscription(course, true)
                        : new CourseWithSubscription(course, false));
            }

            print(req, resp, courses);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = (Course) getEntityFromRequest(req, Course.class);
        if(!isCourseUnique(course)){
            print(req, resp, );
        }
    }

    private boolean isCourseUnique(Course course) {
        Course existingCourse = getCourseService().getByTitleAndTutor(course.getTitle(), course.getTutorId());

        return existingCourse==null;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
