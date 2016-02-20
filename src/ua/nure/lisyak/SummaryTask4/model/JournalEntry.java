package ua.nure.lisyak.SummaryTask4.model;

import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;

import java.util.Date;
import java.util.Set;

public class JournalEntry extends Entity{
    private int courseId;
    private String courseTitle;
    private int studentId;
    private String studentName;
    private Date startDate;

    private Date endDate;
    Set<Theme> themes;

    private String studentEmail;

    private Status status;
    private int mark;

    public JournalEntry(int courseId, int studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public JournalEntry() {
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
