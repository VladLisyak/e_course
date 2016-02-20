package ua.nure.lisyak.SummaryTask4.service.JdbcService;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Service;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.repository.CourseRepository;
import ua.nure.lisyak.SummaryTask4.service.CourseService;
import ua.nure.lisyak.SummaryTask4.util.Tuple;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import java.util.List;

@Service
public class JdbcCourseService implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

   /* @Override
    public List<Course> getAllByTheme(Theme theme) {
        return repository.getAllByTheme(theme);
    }*/

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
    public Tuple<List<? extends Course>, Integer> getFiltered(int offset, int limit, String searchBy, String search, String sortBy, String order) {
        String sortParam = Constants.SORT_TYPES.contains(sortBy) ? sortBy : Constants.SORT_TYPES.get(0);
        String orderParam = order.equalsIgnoreCase("ASC")
                || order.equalsIgnoreCase("DESC")
                ? order : "ASC";
        if(searchBy!=null && Constants.SEARCH_TYPES.contains(searchBy)){

            return repository.getFiltered(offset, limit, searchBy, search, sortParam, orderParam);
        }

        return repository.getSorted(offset, limit, sortParam, orderParam);
    }

    @Override
    public Course getByTitleAndTutor(String title, int tutorId) {
        return repository.getByTitleAndTutor(title, tutorId);
    }

    @Override
    public Float getStudentAverageMark(int id) {
        return repository.getStudentAverageMark(id);
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

    /*@Override
    public List<Course> getAll() {
        return repository.getAll();
    }*/

    @Override
    public List<Course> getAllByStudentId(int id) {
        return repository.getAllByStudentId(id);
    }

    @Override
    public List<Course> getAllExceptSubscribed(int id) {
        return repository.getAllExceptSubscribed(id);
    }
}
