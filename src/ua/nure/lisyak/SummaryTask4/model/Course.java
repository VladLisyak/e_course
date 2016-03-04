package ua.nure.lisyak.SummaryTask4.model;

import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a course of certain tutor
 * @see User
 */
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
    private Set<Theme> themes = new HashSet<>();

    public Course() {
    }

    public Course(int id, int count, int tutorId, String title, String description, String image, Date startDate, Date endDate, Status status, User tutor, Set<Theme> themes) {

        this.tutorId = tutorId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.tutor = tutor;
        this.themes = themes;
        this.id = id;
        this.subscribersCount = count;
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
        if (o == null) return false;

        Course course;
        try{
            course = (Course) o;
        }catch (Exception e){
            return false;
        }

        return this.getId()==course.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }
}
