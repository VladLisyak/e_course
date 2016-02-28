package ua.nure.lisyak.SummaryTask4.repository.JdbcRepository;

import ua.nure.lisyak.SummaryTask4.annotation.Repository;
import ua.nure.lisyak.SummaryTask4.db.QueryStorage;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.repository.MessageRepository;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMessageRepository extends JdbcAbstractRepository implements MessageRepository{
    private static final String SAVE_MESSAGE = "message.save";
    private static final String GET_MESSAGE = "message.get";
    private static final String UPDATE_MESSAGE = "message.update";
    private static final String DELETE_MESSAGE = "message.delete";
    private static final String GET_UNREAD_COUNT = "message.unread.count";
    private static final String GET_DIALOG = "message.dialog";
    private static final String GET_UNREAD_FROM_TO = "message.unread.from.to";
    private static final String GET_UNREAD = "message.unread";

    /**
     * Creates a new repository.
     *
     * @param connectionHolder connection holder
     */
    public JdbcMessageRepository(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public Message get(int id) {
        String sql = QueryStorage.get(GET_MESSAGE);
        return (Message) get(id, sql);
    }

    @Override
    public Message save(Message message) {
        String sql = QueryStorage.get(SAVE_MESSAGE);
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, message.getFromId());
            ps.setInt(2, message.getToId());
            ps.setString(3, message.getMessage());
            ps.setBoolean(4, message.isRead());
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
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
    public Message update(Message message) {
        String sql = QueryStorage.get(UPDATE_MESSAGE);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, message.getFromId());
            ps.setInt(2, message.getToId());
            ps.setString(3, message.getMessage());
            ps.setBoolean(4, message.isRead());
            ps.setTimestamp(5, new Timestamp(message.getDate().getTime()));
            ps.setInt(6, message.getId());

            ps.executeUpdate();


            return get(message.getId());
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public boolean delete(int id) {
        return super.delete(id, DELETE_MESSAGE);
    }

   /* @Override
    public List<Message> getAllByFromId(int id) {
        return null;
    }

    @Override
    public List<Message> getByToId(int id) {
        return null;
    }*/

    @Override
    public Integer getUnreadCount(int toId, int fromId) {
        String sql = QueryStorage.get(GET_UNREAD_COUNT);
        try(PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, toId);
            ps.setInt(2, fromId);

            Integer unreadCount = null;

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            unreadCount = rs.getInt("message_count");}

            return unreadCount;
        }catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public Message getUnread(int toId, int fromId){
        String sql = QueryStorage.get(GET_UNREAD_FROM_TO);
        try(PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, fromId);
            ps.setInt(2, toId);
            ResultSet rs = ps.executeQuery();
            Message unreadMessage = null;

            if(rs.next()){
                unreadMessage = extractFromResultSet(rs);
            }

            return unreadMessage;
        }catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<Message> getDialog(int fId, int sId) {
        String sql = QueryStorage.get(GET_DIALOG);
        try(PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, fId);
            ps.setInt(2, sId);
            ps.setInt(3, sId);
            ps.setInt(4, fId);
            ResultSet rs = ps.executeQuery();

            return getAllFromResultSet(rs);
        }catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<Message> getUnread(Integer userId) {
        String sql = QueryStorage.get(GET_UNREAD);
        try(PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();


            return getAllFromResultSet(rs);
        }catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    private List<Message> getAllFromResultSet(ResultSet rs) throws SQLException {
        List<Message> messages = new ArrayList<>();
        while(rs.next()){
            Message m = extractFromResultSet(rs);
            messages.add(m);
        }
        return messages;
    }

    @Override
    protected Message extractFromResultSet(ResultSet rs) throws SQLException {
        Message m = new Message();
        m.setId(rs.getInt("id"));
        m.setReferrerName(rs.getString("name"));
        m.setFromId(rs.getInt("from_id"));
        m.setToId(rs.getInt("to_id"));
        m.setDate(rs.getTimestamp("date"));
        m.setMessage(rs.getString("message"));
        return m;
    }
}
