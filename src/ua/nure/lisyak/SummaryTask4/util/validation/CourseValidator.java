package ua.nure.lisyak.SummaryTask4.util.validation;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import java.util.Date;
/**
 * Performs validation of {@link User} objects.
 */
public class CourseValidator extends AbstractValidator {

    /**
     * Instantiates a new UserValidator for validating {@link User}s.
     *
     * @param course       user that needs validation
     * @param locale     current locale value
     */
    public CourseValidator(Course course, String locale) {
        super(locale);
        if (course == null) {
            return;
        }
        putIssue("date", validateDate(course.getStartDate(), course.getEndDate()));
    }




    private String validateDate(Date fdate, Date sdate) {
        if (fdate == null || sdate == null) {
            return Constants.Validation.CANT_BE_EMPTY;
        }
        if (fdate.after(sdate)) {
            return Constants.Validation.START_DATE_GREATER_END_DATE;
        }
        return null;
    }




}
