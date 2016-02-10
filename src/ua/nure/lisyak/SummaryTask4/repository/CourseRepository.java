package ua.nure.lisyak.SummaryTask4.repository;

import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;

import java.util.List;

public interface CourseRepository {

    Course save(Course course);

    List<Course> getAllByTheme(Theme theme);

    Course update(Course course);

    boolean delete(int id);

    Course get(int id);

    List<Course> getAllByTutorId(int id);

    List<Course> getAllFinished();

    boolean setAllFinished();

    List<Course> getAll();

    List<Course> getAllByStudentId(int id);

    List<Course> getAllExceptSubscribed(int id);

    List<Course> getSorted(int offset, int limit, String sort, String order);

    List<Course> getFiltered(int offset, int limit, String searchBy, String search, String sortBy, String order);

    Course getByTitleAndTutor(String title, int tutorId);
}
