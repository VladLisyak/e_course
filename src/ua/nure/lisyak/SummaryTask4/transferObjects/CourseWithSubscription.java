package ua.nure.lisyak.SummaryTask4.transferObjects;

import ua.nure.lisyak.SummaryTask4.model.Course;

public class CourseWithSubscription extends Course {
    private Boolean subsribed;


    public CourseWithSubscription(Course course, Boolean subsribed) {
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

        this.subsribed = subsribed;
    }

    public Boolean getSubsribed() {
        return subsribed;
    }

    public void setSubsribed(Boolean subsribed) {
        this.subsribed = subsribed;
    }

}
