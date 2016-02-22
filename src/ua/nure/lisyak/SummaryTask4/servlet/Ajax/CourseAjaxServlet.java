package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.transferObjects.CourseWithSubscription;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.Tuple;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.AJAX_COURSE_LIST})
/*@MultipartConfig(
        location = SettingsAndFolderPaths.Images.TEMP_DIRECTORY,
        fileSizeThreshold = SettingsAndFolderPaths.Images.SIZE_THRESHOLD,
        maxFileSize = SettingsAndFolderPaths.Images.MAX_SIZE
)*/
public class CourseAjaxServlet extends BaseAjaxServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CourseWithSubscription> courses = new ArrayList<>();
        Integer id = getEntityId(req);
        Integer tutorId = getIntParam(req, Constants.Attributes.TUTOR_ID);
        List<Course> studentSubscriptions = new ArrayList<>();
        User user = getCurrentUser(req);

        String param = getStringParam(req, Constants.Attributes.SORT_BY);

        if(user!=null){
                studentSubscriptions = getCourseService().getAllByStudentId(user.getId());
            if(tutorId != null){
                List<Course> tutorCourses = getCourseService().getAllByTutorId(tutorId);

                for (Course course : tutorCourses) {
                    courses.add(studentSubscriptions.contains(course)
                            ? new CourseWithSubscription(course, true)
                            : new CourseWithSubscription(course, false));
                }

                print(req, resp, courses);
                return;
            }

            if(user.getRoles().contains(Role.TUTOR) && param!=null){

                courses = getCourseService().getByStatusAndTutorId(param.toUpperCase(), user.getId());

                print(req, resp, courses);
                return;
            }

            if(id!=null){
                Course course = getCourseService().get(id);
                if(course!=null){
                     print(req, resp, getCourseService().get(id));
                     return;
                }

                    String locale = getLocale(req);
                    LocaleUtil translator = getTranslator();
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, translator.translate("object.notFound",locale));
                    return;
            }
            else{
                String searchBy = getStringParam(req, Constants.Attributes.SEARCH_BY);
                String search = getStringParam(req, Constants.Attributes.SEARCH_PARAM);
                Integer limit = getIntParam(req, Constants.Attributes.LIMIT, SettingsAndFolderPaths.getCoursesPerPage());
                Integer offset = getIntParam(req, Constants.Attributes.OFFSET, 0);
                String sort = getStringParam(req, Constants.Attributes.SORT_BY, "id");
                String order = getStringParam(req, Constants.Attributes.ORDER, "asc");


                Tuple<List<? extends Course>, Integer> coursesWithCount = getCourseService().getFiltered(offset,limit,searchBy,search,sort,order);

                for (Course course : coursesWithCount.getFirstEntity()) {
                    courses.add(studentSubscriptions.contains(course)
                            ? new CourseWithSubscription(course, true)
                            : new CourseWithSubscription(course, false));
                }
                coursesWithCount.setFirstEntity(courses);

                print(req, resp, coursesWithCount);
                return;
            }
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "validator.error500");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = (Course) getEntityFromRequest(req, Course.class);
        String locale = getStringParam(req,Constants.Attributes.LANG);

        LocaleUtil translator = getTranslator();

        if(!isCourseUnique(course)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, translator.translate("object.existing", locale));
            return;
        }

        String imagePart = getStringParam(req,Constants.Attributes.IMAGE);

        if (saveCourse(imagePart, course)!=null){
            print(req, resp,  translator.translate("object.saved", locale));
            return;
        }

        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, translator.translate("object.error", locale));
        return;

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = (Course) getEntityFromRequest(req, Course.class);
        String locale = getStringParam(req,Constants.Attributes.LANG);
        LocaleUtil translator = getTranslator();
        String imagePart = getStringParam(req, Constants.Attributes.IMAGE);

        if(updateCourse(imagePart, course)){
            print(req, resp,  translator.translate("object.saved", locale));
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getEntityId(req);
        String locale = getStringParam(req,Constants.Attributes.LANG);
        LocaleUtil translator = getTranslator();

        if(id!=null){
            if(getCourseService().delete(id)){
                print(req, resp,  translator.translate("object.deleted", locale));
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));

    }

    private boolean isCourseUnique(Course course) {
        Course existingCourse = getCourseService().getByTitleAndTutor(course.getTitle(), course.getTutorId());

        return existingCourse==null;
    }

    private Course saveCourse(String image, Course course) {
        Course savedCourse = getCourseService().save(course);
        if (image != null) {
            String imageName = getFileService().saveFile(savedCourse.getId(), SettingsAndFolderPaths.getUploadCoursesDirectory(), image);
            savedCourse.setImage(imageName);
            return getCourseService().update(savedCourse);
        }
        return null;
    }


    private boolean updateCourse(String image, Course course) {
        Course savedCourse = getCourseService().get(course.getId());
        if (image != null) {
            String imageName = getFileService().saveFile(savedCourse.getId(), SettingsAndFolderPaths.getUploadCoursesDirectory(), image);
            savedCourse.setImage(imageName);
        }

        return getCourseService().update(savedCourse)!=null;
    }

}
