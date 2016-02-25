package ua.nure.lisyak.SummaryTask4.transferObjects;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;

import java.util.Date;
import java.util.Set;

public class CourseWithSubscription extends Course {
    private Boolean subscribed;

    public CourseWithSubscription() {
    }

    public CourseWithSubscription(int id, int count, int tutorId, String title, String description, String image, Date startDate, Date endDate, Status status, User tutor, Set<Theme> themes, boolean subscribed) {
        super(id, count, tutorId, title, description, image, startDate, endDate, status, tutor, themes);
        this.subscribed = subscribed;
    }

    public CourseWithSubscription(Course course, Boolean subscribed) {
        super(
                course.getId(),
                course.getSubscribersCount(),
                course.getTutorId(),
                course.getTitle(),
                course.getDescription(),
                course.getImage(),
                course.getStartDate(),
                course.getEndDate(),
                course.getStatus(),
                course.getTutor(),
                course.getThemes());

        this.subscribed = subscribed;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

}
