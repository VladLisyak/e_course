package ua.nure.lisyak.SummaryTask4.repository.JdbcRepository;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Repository;
import ua.nure.lisyak.SummaryTask4.db.QueryStorage;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.repository.CourseRepository;
import ua.nure.lisyak.SummaryTask4.repository.JournalEntryRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcJournalEntryRepository extends JdbcAbstractRepository implements JournalEntryRepository {
    private static final String SAVE_JOURNAL_ENTRY = "journal.save";
    private static final String UPDATE_JOURNAL_ENTRY = "journal.update";
    private static final String DELETE_JOURNAL_ENTRY = "journal.delete";
    private static final String GET_JOURNAL_ENTRY = "journal.get";
    private static final String GET_JOURNAL_ENTRY_BY_TUTOR = "journal.by.tutor";
    private static final String GET_JOURNAL_ENTRY_BY_COURSE = "journal.by.course";
    private static final String GET_JOURNAL_ENTRY_BY_STUDENT = "journal.get.by.student";
    private static final String DELETE_BY_STUDENT = "journal.delete.by.student";
    private static final String GET_BY_TUTOR_ID = "journal.get.by.tutor";

    @Autowired
    private CourseRepository courseRep;
    /**
     * Creates a new repository.
     *
     * @param connectionHolder connection holder
     */
    public JdbcJournalEntryRepository(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public JournalEntry save(JournalEntry entry) {
        String sql = QueryStorage.get(SAVE_JOURNAL_ENTRY);
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entry.getStudentId());
            ps.setInt(2, entry.getCourseId());
            ps.setInt(3, entry.getMark());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);

            return get(id);
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public JournalEntry update(JournalEntry entry) {
        String sql = QueryStorage.get(UPDATE_JOURNAL_ENTRY);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, entry.getStudentId());
            ps.setInt(2, entry.getCourseId());
            ps.setInt(3, entry.getMark());
            ps.setInt(4, entry.getId());

            ps.executeUpdate();

            return get(entry.getId());
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public boolean delete(int id) {
        return super.delete(id, DELETE_JOURNAL_ENTRY);
    }

    @Override
    public JournalEntry get(int id) {
        String sql = QueryStorage.get(GET_JOURNAL_ENTRY);
        return (JournalEntry) get(id, sql);
    }

    @Override
    public List<JournalEntry> getAllByTutorId(int id) {
        String sql = QueryStorage.get(GET_JOURNAL_ENTRY_BY_TUTOR);
        return getAllBy( id, sql);
    }

    @Override
    public JournalEntry getByTutorId(int tutorId, int entryId) {
        String sql = QueryStorage.get(GET_BY_TUTOR_ID);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, tutorId);
            ps.setInt(2, entryId);

            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return null;
            }

            return extractFromResultSet(rs);
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<JournalEntry> getAllByStudentId(int id) {
        String sql = QueryStorage.get(GET_JOURNAL_ENTRY_BY_STUDENT);
        return getAllBy( id, sql);
    }

    @Override
    public boolean deleteByStudent(int studentId, int courseId){
        String sql = QueryStorage.get(DELETE_BY_STUDENT);
        return queryBooleanForTwoParams(studentId, courseId, sql);
    }

    @Override
    public List<JournalEntry> getAllByCourseId(int id) {
        String sql = QueryStorage.get(GET_JOURNAL_ENTRY_BY_COURSE);
        return getAllBy(id, sql);
    }

    private List<JournalEntry> getAllBy(int id, String query){
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return extractAllFromResultSet(rs);
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, query, e);
            throw new DataAccessException(getMessage(query), e);
        }
    }

    private List<JournalEntry> extractAllFromResultSet(ResultSet rs) throws SQLException {
        List<JournalEntry> entries = new ArrayList<>();
        while (rs.next()){
            JournalEntry entry = extractFromResultSet(rs);
            entries.add(entry);
        }

        return entries;
    }

    @Override
    protected JournalEntry extractFromResultSet(ResultSet rs) throws SQLException {
        JournalEntry entry = new JournalEntry();
        entry.setId(rs.getInt("id"));
        entry.setStudentId(rs.getInt("user_id"));
        entry.setStudentName(rs.getString("name"));
        entry.setCourseId(rs.getInt("course_id"));
        entry.setStartDate(rs.getDate("start_date"));
        entry.setEndDate(rs.getDate("end_date"));
        entry.setCourseTitle(rs.getString("title"));
        entry.setMark(rs.getInt("mark"));
        entry.setStudentEmail(rs.getString("email"));
        entry.setStatus(Status.valueOf(rs.getString("status")));

        entry.setThemes(courseRep.getThemes(entry.getId()));

        return entry;
    }

}
