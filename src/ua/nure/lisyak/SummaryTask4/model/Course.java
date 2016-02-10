package ua.nure.lisyak.SummaryTask4.model;

import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;

import java.util.Date;
import java.util.Set;

public class Course extends Entity{

    private int tutorId;
    private String title;
    private String description;
    private Integer subscribersCount;

    private String image;

    private Date startDate;
    private Date endDate;
    private Status status;
    private User tutor;
    private Set<Theme> themes;

    public Course() {
    }

    public Course(int tutorId, String title, String description, String image, Date startDate, Date endDate, Status status, User tutor, Set<Theme> themes) {

        this.tutorId = tutorId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.tutor = tutor;
        this.themes = themes;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public void addTheme(Theme theme){
        themes.add(theme);
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public Integer getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(Integer subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (tutorId != course.tutorId) return false;
        if (title != null ? !title.equals(course.title) : course.title != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (image != null ? !image.equals(course.image) : course.image != null) return false;
        if (startDate != null ? !startDate.equals(course.startDate) : course.startDate != null) return false;
        if (endDate != null ? !endDate.equals(course.endDate) : course.endDate != null) return false;
        if (status != course.status) return false;
        if (tutor != null ? !tutor.equals(course.tutor) : course.tutor != null) return false;
        return !(themes != null ? !themes.equals(course.themes) : course.themes != null);

    }

    @Override
    public int hashCode() {
        int result = tutorId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (tutor != null ? tutor.hashCode() : 0);
        result = 31 * result + (themes != null ? themes.hashCode() : 0);
        return result;
    }
}
