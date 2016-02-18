package ua.nure.lisyak.SummaryTask4.repository.JdbcRepository;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Repository;
import ua.nure.lisyak.SummaryTask4.db.QueryStorage;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;
import ua.nure.lisyak.SummaryTask4.repository.CourseRepository;
import ua.nure.lisyak.SummaryTask4.repository.UserRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class JdbcCourseRepository extends JdbcAbstractRepository implements CourseRepository {
    private static final String SAVE_COURSE = "course.save";
    private static final String GET_WITH_COUNT = "course.get.with.count";
    private static final String UPDATE_COURSE = "course.update";
    private static final String DELETE_COURSE = "course.delete";
    private static final String GET_COURSE = "course.get";
    private static final String GET_ALL_SORTED = "course.getAll.sorted";
    private static final String GET_ALL = "course.getAll";
    private static final String GET_ALL_BY_TUTOR_ID = "course.by.tutor";
    private static final String GET_ALL_FINISHED = "course.all.finished";
    private static final String SET_ALL_FINISHED = "course.set.finished";
    private static final String ADD_THEME = "course.theme.add";
    private static final String DELETE_OLD_THEME = "course.theme.delete.old";
    private static final String GET_BY_THEME = "course.getAll.by.theme";
    private static final String GET_THEMES = "course.get.themes.by.id";
    private static final String GET_ALL_BY_STUDENT_ID = "course.get.by.student.id";
    private static final String GET_ALL_EXCEPT_SUBSCRIBED = "course.get.except.subscribed";
    private static final String SUBSCRIBERS_COUNT = "subscribers.get.count";
    private static final String GET_SORTED = "course.get.sorted.";
    private static final String GET_FILTERED = "course.get.filtered";

    private static final String GET_BY_TITLE_AND_TUTOR = "course.get.by.title.and.tutor";
    private static final String SAVE_TUTOR = "course.tutor.save";
    private static final String DELETE_TUTOR = "course.tutor.delete";

    private static final Map<String, String> orders = new HashMap<>();

    @Autowired
    private UserRepository userRep;


    /**
     * Creates a new repository.
     *
     * @param connectionHolder connection holder
     */
    public JdbcCourseRepository(ConnectionHolder connectionHolder) {
        super(connectionHolder);
        orders.put("id", "id");
        orders.put("title", "title");
        orders.put("length", "end_date - start_date");
        orders.put("count", "count");
    }


    @Override
    public Course save(Course course) {
        String sql = QueryStorage.get(SAVE_COURSE);
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, course.getTutorId());
            ps.setString(2, course.getTitle());
            ps.setString(3, course.getDescription());
            ps.setString(4, course.getImage());
            ps.setDate(5, new Date(course.getStartDate().getTime()));
            ps.setDate(6, new Date(course.getEndDate().getTime()));
            ps.setString(7, course.getStatus().toString());
            ps.executeUpdate();


            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);

            updateThemes(course);

            return get(id);
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

   /* public boolean saveTutor(int courseId, int tutorId) {
        String save = QueryStorage.get(SAVE_TUTOR);
        return queryBooleanForTwoParams(courseId, tutorId, save);
    }*/

    @Override
    public List<Course> getAllByTheme(Theme theme) {
        String sql = QueryStorage.get(GET_BY_THEME);
        return getAllByEnum(theme.toString(), sql);
    }


    @Override
    public Course update(Course course) {
        String sql = QueryStorage.get(UPDATE_COURSE);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, course.getTutorId());
            ps.setString(2, course.getTitle());
            ps.setString(3, course.getDescription());
            ps.setString(4, course.getImage());
            ps.setDate(5, new Date(course.getStartDate().getTime()));
            ps.setDate(6, new Date(course.getEndDate().getTime()));
            ps.setString(7, course.getStatus().toString());
            ps.setInt(8, course.getId());

            ps.executeUpdate();

            return get(course.getId());
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = QueryStorage.get(DELETE_COURSE);
        return super.delete(id, sql);
    }

    @Override
    public Course get(int id) {
        String sql = QueryStorage.get(GET_COURSE);
        Course course = (Course) get(id, sql);

        return course;
    }


    @Override
    public List<Course> getAllByTutorId(int id) {
        return getAllBy(id, GET_ALL_BY_TUTOR_ID);
    }

    @Override
    public List<Course> getAllFinished(){
        String sql = QueryStorage.get(GET_ALL_FINISHED);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            return extractListFromPreparedStatement(ps);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }


    @Override
    public boolean setAllFinished(){
        String sql = QueryStorage.get(SET_ALL_FINISHED);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            return ps.executeUpdate()!=0;
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = QueryStorage.get(GET_ALL);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            return extractListFromPreparedStatement(ps);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<Course> getAllByStudentId(int id) {
        String sql = QueryStorage.get(GET_ALL_BY_STUDENT_ID);
        return getAllBy(id, sql);
    }

    @Override
    public List<Course> getAllExceptSubscribed(int id) {
        String sql = QueryStorage.get(GET_ALL_EXCEPT_SUBSCRIBED);
        return getAllBy(id, sql);
    }



    @Override
    public List<Course> getSorted(int offset, int limit, String sort, String order) {
        String query = QueryStorage.get(GET_WITH_COUNT);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, orders.get(sort));
            ps.setString(2, order);
            ps.setInt(3, offset);
            ps.setInt(4, limit);

            return extractListFromPreparedStatement(ps);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, query, e);
            throw new DataAccessException(getMessage(query), e);
        }
    }

    //TODO check getSorted and getFiltered
     @Override
    public List<Course> getFiltered(int offset, int limit, String searchBy, String search, String sortBy, String order) {
        String query = QueryStorage.get(GET_FILTERED);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setString(1, searchBy);
            ps.setString(2, search);
            ps.setString(3, orders.get(sortBy));
            ps.setString(4, order);
            ps.setInt(5, offset);
            ps.setInt(6, limit);

            return extractListFromPreparedStatement(ps);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, query, e);
            throw new DataAccessException(getMessage(query), e);
        }
    }

    @Override
    public Course getByTitleAndTutor(String title, int tutorId) {
        String sql = QueryStorage.get(GET_BY_TITLE_AND_TUTOR);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, tutorId);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                return null;
            }

            return extractFromResultSet(rs);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    protected Course extractFromResultSet(ResultSet resultSet) throws SQLException {
        Course course;

        course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setImage(resultSet.getString("image"));
        course.setStartDate(resultSet.getDate("start_date"));
        course.setEndDate(resultSet.getDate("end_date"));
        course.setStatus(Status.valueOf(resultSet.getString("status")));
        course.setTutorId(resultSet.getInt("tutor_id"));
        int count = resultSet.getInt("count");
        course.setSubscribersCount(count);

        course.setTutor(userRep.get(course.getTutorId()));
        course.setThemes(getThemes(course.getId()));

        return course;
    }

    private List<Course> getAllBy(int id, String query) {
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, id);

            return extractListFromPreparedStatement(ps);
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, query, e);
            throw new DataAccessException(getMessage(query), e);
        }
    }

    private Integer getSubscribersCount(int courseId){
        String query = QueryStorage.get(SUBSCRIBERS_COUNT);
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt("count");
        }catch (SQLException e){
            LOGGER.warn(ERROR_MESSAGE, query, e);
            throw new DataAccessException(getMessage(query), e);
        }
    }


   private boolean updateThemes(Course course) {

       if (!deleteOldThemes(course.getId()))
           return false;


       for (Theme theme : course.getThemes()) {
           addTheme(course.getId(), theme);
       }

       return true;
   }

    private boolean addTheme(int themeId, Theme theme) {
        String sql = QueryStorage.get(ADD_THEME);
        return addEnum(themeId, theme.toString(), sql);
    }

    private boolean deleteOldThemes(int userId){
        return super.delete(userId, DELETE_OLD_THEME);
    }

    private Set<Theme> getThemes(int id) {
        String sql = QueryStorage.get(GET_THEMES);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            Set<Theme> roles = new HashSet<>();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                roles.add(Theme.valueOf(rs.getString("theme")));
            }

            return roles;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    public UserRepository getUserRep() {
        return userRep;
    }

    public void setUserRep(UserRepository userRep) {
        this.userRep = userRep;
    }
}
