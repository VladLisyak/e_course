package ua.nure.lisyak.SummaryTask4.util.validation;

import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.model.Course;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CourseValidatorTest {

    @Test
    public void testDateValid(){
        Course course = new Course();
        Validator validator = new CourseValidator(course, "en");
        ((CourseValidator) validator).setBundle(ResourceBundle.getBundle("messages", new Locale("en")));
        assertNotNull(validator.getMessages().get("date"));
        course.setStartDate(new Date());
        course.setEndDate(new Date(11, 2, 1995));
        validator = new CourseValidator(course, "en");
        assertNotNull(validator.getMessages().get("date"));
        course.setStartDate(new Date());
        course.setEndDate(null);
        validator = new CourseValidator(course, "en");
        assertNotNull(validator.getMessages().get("date"));

        course.setStartDate(new Date());
        course.setEndDate(new Date(11, 1, 1995));
        validator = new CourseValidator(course, "en");
        assertNotNull(validator.getMessages().get("date"));
        course.setStartDate(new Date(11, 04, 1995));
        course.setEndDate(new Date());
        validator = new CourseValidator(course, "en");
        assertNull(validator.getMessages().get("date"));
    }

}
