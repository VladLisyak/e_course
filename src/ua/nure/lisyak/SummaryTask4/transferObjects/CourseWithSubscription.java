package ua.nure.lisyak.SummaryTask4.transferObjects;

import ua.nure.lisyak.SummaryTask4.model.Course;

public class CourseWithSubscription extends Course {
    private Boolean subscribed;


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
