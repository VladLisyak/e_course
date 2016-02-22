package ua.nure.lisyak.SummaryTask4.service;

import ua.nure.lisyak.SummaryTask4.annotation.Cacheable;
import ua.nure.lisyak.SummaryTask4.annotation.EvictCache;
import ua.nure.lisyak.SummaryTask4.annotation.Transactional;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.transferObjects.CourseWithSubscription;
import ua.nure.lisyak.SummaryTask4.util.Tuple;

import java.util.List;

public interface CourseService {

    @Transactional
    @EvictCache
    Course save(Course course);

    @Transactional
    @EvictCache
    Course update(Course course);

    @Transactional
    @EvictCache
    boolean delete(int id);

    Course get(int id);

   /* @Cacheable
    List<Course> getAllByTheme(Theme theme);*/

   /* List<StudentCourse> getAllByStudentId(int id);*/

    List<Course> getAllByTutorId(int id);

    List<Course> getAllFinished();

    @EvictCache
    boolean setAllFinished();

   /* @Cacheable
    List<Course> getAll();*/

    @Cacheable
    List<Course> getAllByStudentId(int id);

    @Cacheable
    List<Course> getAllExceptSubscribed(int id);

    @Cacheable
    Tuple<List<? extends Course>, Integer> getFiltered(int offset, int limit, String searchBy, String search, String sortBy, String order);

    Course getByTitleAndTutor(String title, int tutorId);

    Float getStudentAverageMark(int id);

    List<CourseWithSubscription> getByStatusAndTutorId(String s, int id);
}
