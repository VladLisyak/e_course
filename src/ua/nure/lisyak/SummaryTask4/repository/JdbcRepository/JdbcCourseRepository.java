package ua.nure.lisyak.SummaryTask4.repository.JdbcRepository;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Repository;
import ua.nure.lisyak.SummaryTask4.db.QueryStorage;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;
import ua.nure.lisyak.SummaryTask4.repository.CourseRepository;
import ua.nure.lisyak.SummaryTask4.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcCourseRepository extends JdbcAbstractRepository implements CourseRepository {
    private static final String SAVE_COURSE = "course.save";
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
    private static final String SAVE_TUTOR = "course.tutor.save";
    private static final String DELETE_TUTOR = "course.tutor.delete";

    @Autowired
    private UserRepository userRep;


    /**
     * Creates a new repository.
     *
     * @param connectionHolder connection holder
     */
    public JdbcCourseRepository(ConnectionHolder connectionHolder) {
        super(connectionHolder);
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
        return super.delete(id, DELETE_COURSE);
    }

    @Override
    public Course get(int id) {
        String sql = QueryStorage.get(GET_COURSE);
        Course course = (Course) get(id, sql);
        User tutor = getUserRep().get(course.getTutorId());
        course.setTutor(tutor);
        course.setThemes(getThemes(id));
        return course;
    }

    @Override
    public List<Course> getSorted(int offset, int limit) {
        String sql = QueryStorage.get(GET_ALL_SORTED);
        return queryListForTwoParams(offset, limit, sql);
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
    protected Course extractFromResultSet(ResultSet resultSet) throws SQLException {
        Course course;

        course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setImage(resultSet.getString("image"));
        course.setStartDate(resultSet.getDate("startDate"));
        course.setEndDate(resultSet.getDate("endDate"));
        course.setStatus(Status.valueOf(resultSet.getString("status")));
        course.setTutorId(resultSet.getInt("tutor_id"));



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

    @Override
    protected List<Course> extractListFromPreparedStatement(PreparedStatement ps) throws SQLException{
        List<Course> studentCourses = new ArrayList<>();
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Course course = extractFromResultSet(rs);
            studentCourses.add(course);
        }

        return studentCourses;
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
                roles.add(Theme.valueOf(rs.getString("role")));
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
