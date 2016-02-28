package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.transferObjects.CourseWithSubscription;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.Tuple;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.util.validation.CourseValidator;
import ua.nure.lisyak.SummaryTask4.util.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        String byParam = getStringParam(req, Constants.Attributes.BY_PARAM);
        Role roleOfQuery = Role.valueOf(getStringParam(req, Constants.Attributes.ROLE_OF_QUERY));
        List<Course> studentSubscriptions = new ArrayList<>();
        User user = getCurrentUser(req);

        if(user!=null) {
            studentSubscriptions = getCourseService().getAllByStudentId(user.getId());
        }
        /*if(true){*/
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

        if(id!=null){
            Course course = getCourseService().get(id);
            if(course!=null){
                if(user!=null){
                    print(req,resp,studentSubscriptions.contains(course)?
                            new CourseWithSubscription(course, true)
                            :new CourseWithSubscription(course,false));
                }else{
                    print(req, resp, getCourseService().get(id));
                    return;
                }
            }

            String locale = getLocale(req);
            LocaleUtil translator = getTranslator();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, translator.translate("object.notFound",locale));
            return;
        }

        if(roleOfQuery!=null){
            switch (roleOfQuery){
                case STUDENT:{
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
                case TUTOR:{
                    if(user!=null && user.getRoles().contains(Role.TUTOR) && byParam!=null){

                        courses = getCourseService().getByStatusAndTutorId(byParam.toUpperCase(), user.getId());

                        print(req, resp, courses);
                        return;
                    }
                }
                case ADMIN:{
                    if(user!=null && user.getRoles().contains(Role.ADMIN)){
                        List<Course> allCourses = getCourseService().getAll();
                        print(req, resp, allCourses);
                    }
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = (Course) getEntityFromRequest(req, Course.class);
        String locale = getLocale(req);

        LocaleUtil translator = getTranslator();

        course = resolveStatus(course);

        if(!isCourseUnique(course)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, translator.translate("object.existing", locale));
            return;
        }
        if(course.getTutor()!=null){
            course.setTutorId(course.getTutor().getId());
        }

        Validator courseValidator = new CourseValidator(course, locale);

        String imagePart = getStringParam(req, Constants.Attributes.IMAGE);

        if(imagePart==null){
            imagePart = course.getImage();
        }


        if (courseValidator.hasErrors()) {
            sendError(req, resp, courseValidator);
            return;
        }

        checkCourseUniqueness(course, courseValidator);

        if (courseValidator.hasErrors()) {
            sendError(req, resp, courseValidator);
            return;
        }

        if (saveCourse(imagePart, course)!=null){
            print(req, resp,  translator.translate("object.saved", locale));
            return;
        }

        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, translator.translate("object.error", locale));
        return;

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = (Course) getEntityFromRequest(req, CourseWithSubscription.class);
        String locale = getLocale(req);
        LocaleUtil translator = getTranslator();

        course = resolveStatus(course);

        if(course.getTutor()!=null){
            course.setTutorId(course.getTutor().getId());
        }

        Validator courseValidator = new CourseValidator(course, locale);

        String imagePart = getStringParam(req, Constants.Attributes.IMAGE);

        if(imagePart==null && !(course.getImage().equals(getCourseService().get(course.getId()).getImage()))){
            imagePart = course.getImage();
        }

        if (courseValidator.hasErrors()) {
            sendError(req, resp, courseValidator);
            return;
        }

        if (courseValidator.hasErrors()) {
            sendError(req, resp, courseValidator);
            return;
        }

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
        course.setImage("");
        if (image != null && image.length()==0) image = null;
        Course savedCourse = getCourseService().save(course);
        if (image != null) {
            String imageName = getFileService().saveFile(savedCourse.getId(), SettingsAndFolderPaths.getUploadCoursesDirectory(), image);
            savedCourse.setImage(imageName);
            return getCourseService().update(savedCourse);
        }
        return savedCourse;
    }


    private boolean updateCourse(String image, Course course) {
        if (image != null) {
            String imageName = getFileService().saveFile(course.getId(), SettingsAndFolderPaths.getUploadCoursesDirectory(), image);
            course.setImage(imageName);
        }

        return getCourseService().update(course)!=null;
    }

    private Course resolveStatus(Course course){
        Course courseToResolve = course;

        if(course !=null && course.getStartDate()!= null && course.getEndDate()!=null){
            if(course.getStartDate().before(new Date())){
                if(course.getEndDate().before(new Date()) || course.getEndDate().equals(new Date())){
                    course.setStatus(Status.FINISHED);
                }else{
                    if (course.getEndDate().after(new Date())){
                        course.setStatus(Status.IN_PROGRESS);
                    }else{
                        if (course.getStartDate().after(new Date())){
                            course.setStatus(Status.BEFORE_START);
                        }
                    }

                }
            }
        }

        return course;
    }

}
