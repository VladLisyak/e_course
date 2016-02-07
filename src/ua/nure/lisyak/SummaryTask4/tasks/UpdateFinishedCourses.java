package ua.nure.lisyak.SummaryTask4.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.service.CourseService;
import ua.nure.lisyak.SummaryTask4.service.JdbcService.JdbcCourseService;

import javax.servlet.ServletContext;

/**
 * Updates orders.
 */
public final class UpdateFinishedCourses implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFinishedCourses.class);
    private CourseService courseService;


    /* * Creates a new {@code UpdateOrdersTask}.
     *
     * @param context servlet context*/
    public UpdateFinishedCourses(ServletContext context) {
        courseService = (CourseService) context.getAttribute(JdbcCourseService.class.getName());
    }

    @Override
    public void run() {
        LOGGER.info("Updating courses...");
        courseService.setAllFinished();
        LOGGER.info("Coursess updated.");
    }

}

