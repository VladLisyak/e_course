package ua.nure.lisyak.SummaryTask4.repository.JdbcRepository;

import ua.nure.lisyak.SummaryTask4.annotation.Repository;
import ua.nure.lisyak.SummaryTask4.db.QueryStorage;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcUserRepository extends JdbcAbstractRepository implements UserRepository{
    private static final String SAVE_USER = "user.save";
    private static final String DELETE_USER = "user.delete";
    private static final String GET_USER = "user.get.by.id";
    private static final String GET_ROLES = "user.get.roles.by.id";
    private static final String GET_USER_BY_EMAIL = "user.get.by.email";
    private static final String GET_ALL = "user.all";
    private static final String UPDATE_USER = "user.update";
    private static final String GET_BY_ROLE = "user.getAll.by.role";
    private static final String DELETE_ROLE = "user.role.delete";
    private static final String DELETE_OLD = "user.role.delete.old";
    private static final String ADD_ROLE = "user.role.add";
    private static final String GET_BY_LOGIN = "user.get.by.login";
    private static final String GET_ALL_BY_STATUS = "user.by.status";

    /**
     * Creates a new repository.
     *
     * @param connectionHolder connection holder
     */
    public JdbcUserRepository(ConnectionHolder connectionHolder) {
        super(connectionHolder);
    }

    @Override
    public User save(User user) {
        String sql = QueryStorage.get(SAVE_USER);
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getEnabled().toString());
            ps.setString(6, user.getImage());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            user.setId(id);

            updateRoles(user);

            return get(id);
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public User update(User user) {
        String sql = QueryStorage.get(UPDATE_USER);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getEnabled().toString());
            ps.setString(6, user.getImage());
            ps.setInt(7, user.getId());
            ps.executeUpdate();

            updateRoles(user);

            return get(user.getId());
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = QueryStorage.get(DELETE_USER);
        return super.delete(id, sql);
    }

    @Override
    public User get(int id) {
        String sql = QueryStorage.get(GET_USER);
        User user = (User) get(id, sql);


        return user;

    }

    @Override
    public User getByEmail(String email) {
        String sql = QueryStorage.get(GET_USER_BY_EMAIL);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            User user = null;
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
              user = extractFromResultSet(rs);
            }

            return user;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<User> getAllByRole(Role role) {
        String sql = QueryStorage.get(GET_BY_ROLE);
        return getAllByEnum(role.toString(), sql);
    }

    @Override
    public List<User> getAll() {
        String sql = QueryStorage.get(GET_ALL);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            List<User> allUsers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            User user;

            while (rs.next()){
                user = extractFromResultSet(rs);
                allUsers.add(user);
            }

            return allUsers;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public User getByLogin(String login) {
        String sql = QueryStorage.get(GET_BY_LOGIN);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            User user = null;

            if(rs.next()) {
                user = extractFromResultSet(rs);
            }

            return user;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    @Override
    public List<User> getAllByStatus(String param) {
        String query = QueryStorage.get(GET_ALL_BY_STATUS);
        return getAllByEnum(param, query);
    }

    @Override
     protected User extractFromResultSet(ResultSet resultSet) throws SQLException{
        User user;
        user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setImage(resultSet.getString("image"));
        user.setPassword(resultSet.getString("password"));
        user.setEnabled(Enabled.valueOf(resultSet.getString("enabled").toUpperCase()));
        user.setImage(user.getImage());
        user.setRoles(getRoles(user.getId()));

        return user;
    }

    private Set<Role> getRoles(int id) {
        String sql = QueryStorage.get(GET_ROLES);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            Set<Role> roles = new HashSet<>();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                roles.add(Role.valueOf(rs.getString("role")));
            }

            return roles;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    private boolean updateRoles(User user) {
        if(user.getRoles()==null || user.getRoles().size()==0){
            user.addRole(Role.STUDENT);
        }

        deleteOldRoles(user.getId());


        for (Role role : user.getRoles()) {
            LOGGER.debug("Adding role {}", role);
            addRole(user.getId(), role);
        }

        return true;
    }

    private boolean deleteRole(int userId, Role role){
        String sql = QueryStorage.get(DELETE_ROLE);
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, role.toString());

            return ps.executeUpdate()!=0;
        } catch (SQLException e) {
            LOGGER.warn(ERROR_MESSAGE, sql, e);
            throw new DataAccessException(getMessage(sql), e);
        }
    }

    private boolean deleteOldRoles(int userId){
        return super.delete(userId, QueryStorage.get(DELETE_OLD));
    }

    private boolean addRole(int themeId, Role role) {
        String sql = QueryStorage.get(ADD_ROLE);
        return addEnum(themeId, role.toString(), sql);
    }
}
