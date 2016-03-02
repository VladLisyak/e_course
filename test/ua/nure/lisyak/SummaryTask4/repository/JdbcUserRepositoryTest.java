package ua.nure.lisyak.SummaryTask4.repository;

import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.holder.ThreadLocalConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.manager.HikariCPManager;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.repository.JdbcRepository.JdbcUserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JdbcUserRepositoryTest {
    private static final ConnectionHolder holder = new ThreadLocalConnectionHolder();
    static {
        holder.set(new HikariCPManager().getConnection());
    }
    private static final UserRepository  rep = new JdbcUserRepository(holder);

    @Test
    public void saveTest(){
        User user = new User();
        user.setName("name");
        user.setEmail("name@mail.ru");
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        User user2 = rep.save(user);

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getLogin(), user2.getLogin());

        rep.delete(user2.getId());
    }

    @Test(expected = DataAccessException.class)
    public void saveFailTest(){
        User user = new User();
        user.setEmail("name@mail.ru");
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        User user2 = rep.save(user);

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getLogin(), user2.getLogin());

        rep.delete(user2.getId());
    }

    @Test
    public void updateTest(){
        User user = rep.get(1);
        user.setName("name");

        User user2 = rep.update(user);

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getLogin(), user2.getLogin());

    }

    @Test(expected = DataAccessException.class)
    public void updateFailTest(){
        User user = rep.get(1);;
        user.setEmail(null);
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        User user2 = rep.update(user);

    }

    @Test
    public void deleteTest(){
        User user = new User();
        user.setName("name");
        user.setEmail("name@mail.ru");
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        user = rep.save(user);
        int before = rep.getAll().size();
        rep.delete(user.getId());
        assertEquals(before-1, rep.getAll().size());
    }

    @Test
    public void deleteFailTest(){

        assertEquals(false, rep.delete(-1));
    }

    @Test
    public void getTest(){
        User user = new User();
        user.setName("asdasf");
        user.setEmail("name@mail.ru");
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        user = rep.save(user);

        User user2 = rep.get(user.getId());

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getLogin(), user2.getLogin());

        rep.delete(user2.getId());
    }

    @Test
    public void getFailTest(){

        User user2 = rep.get(0);

        assertNull(user2);
    }

    @Test
    public void getByEmailTest(){
        User user = new User();
        user.setName("asdasf");
        user.setEmail("name@mail.ru");
        user.setLogin("name");
        user.setEnabled(Enabled.ACTIVE);
        user.setPassword("password");
        user.addRole(Role.STUDENT);

        user = rep.save(user);

        User user2 = rep.getByEmail(user.getEmail());

        assertEquals(user.getName(), user2.getName());
        assertEquals(user.getEmail(), user2.getEmail());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getLogin(), user2.getLogin());

        rep.delete(user2.getId());
    }


    @Test
    public void getByEmailFailTest(){

        User user2 = rep.getByEmail("nonExistingEmail@host.com");


        assertNull(user2);
    }

    @Test
    public void getAllByRoleTest(){

        int count = 0;
        for (User user:rep.getAll()) {
            if (user.getRoles().contains(Role.STUDENT) && user.getEnabled()!=Enabled.BANNED){
                count++;
            }
        }


        assertEquals(rep.getAllByRole(Role.STUDENT, 0).size(), count);
    }
}
