package ua.nure.lisyak.SummaryTask4.service.JdbcService;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Service;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;
import ua.nure.lisyak.SummaryTask4.repository.CourseRepository;
import ua.nure.lisyak.SummaryTask4.service.CourseService;

import java.util.List;

@Service
public class JdbcCourseService implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    public List<Course> getAllByTheme(Theme theme) {
        return repository.getAllByTheme(theme);
    }

    @Override
    public Course update(Course course) {
        return repository.update(course);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public Course get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Course> getSorted(int offset, int limit) {
        return repository.getSorted(offset, limit);
    }

    @Override
    public List<Course> getAllByTutorId(int id) {
        return repository.getAllByTutorId(id);
    }

    @Override
    public List<Course> getAllFinished() {
        return repository.getAllFinished();
    }

    @Override
    public boolean setAllFinished() {
        return repository.setAllFinished();
    }

    @Override
    public List<Course> getAll() {
        return repository.getAll();
    }
}
