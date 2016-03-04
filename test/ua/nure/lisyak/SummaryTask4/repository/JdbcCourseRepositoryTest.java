package ua.nure.lisyak.SummaryTask4.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.holder.ThreadLocalConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.manager.HikariCPManager;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.Course;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.model.enums.Theme;
import ua.nure.lisyak.SummaryTask4.repository.JdbcRepository.JdbcCourseRepository;
import ua.nure.lisyak.SummaryTask4.repository.JdbcRepository.JdbcUserRepository;

import java.util.Date;

import static org.junit.Assert.*;

public class JdbcCourseRepositoryTest {
    private static final ConnectionHolder holder = new ThreadLocalConnectionHolder();

    static {
        holder.set(new HikariCPManager().getConnection());
    }
    private static final CourseRepository  rep = new JdbcCourseRepository(holder);
    private static final UserRepository  userRep = new JdbcUserRepository(holder);
    private Course course = new Course();
    private User user = new User();
    private User savedTutor;
    private Course saved;

    static {
        ((JdbcCourseRepository)rep).setUserRep(userRep);
    }
    @Before
    public void setCourse(){
        course.setTitle("notExistingTitle");
        course.setDescription("notExistingDescription");
        course.setStatus(Status.FINISHED);
        course.addTheme(Theme.GEOGRAPHY);
        course.setEndDate(new Date());
        course.setStartDate(new Date());
        course.setSubscribersCount(5);
        course.setImage("noimage.jpg");

        user.setLogin("NotExistingLogin");
        user.setName("NotExistingName");
        user.setEmail("NotExistingEmail");
        user.setEnabled(Enabled.ACTIVE);
        user.setImage("noimage.jpg");
        user.setPassword("asdfsdgsfg");
        user.addRole(Role.TUTOR);

        savedTutor = userRep.save(user);
        course.setTutorId(savedTutor.getId());
        course.setTutor(savedTutor);

        saved = rep.save(course);
    }

    @After
    public void deleteEntities(){
        rep.delete(saved.getId());
        userRep.delete(savedTutor.getId());
    }

    @Test
    public void saveTest(){


        assertNotNull(saved);
        assertEquals(saved.getDescription(), course.getDescription());
        assertEquals(saved.getTitle(), course.getTitle());
        assertEquals(saved.getTutorId(), course.getTutorId());


    }

    @Test(expected = DataAccessException.class)
    public void saveFailTest(){
        User savedTutor = userRep.save(user);
        course.setTutorId(savedTutor.getId());
        course.setTutor(savedTutor);

        Course saved = rep.save(course);

        rep.save(course);
    }

   @Test
    public void updateTest(){

        String description = saved.getDescription();

        saved.setDescription("secondNotExisting");

        Course updated = rep.update(saved);

        assertNotEquals(updated.getDescription(), description);
        assertEquals(saved.getTitle(), course.getTitle());
        assertEquals(saved.getId(), updated.getId());

    }

    @Test(expected = DataAccessException.class)
    public void updateFailTest(){
        saved.setTitle(null);

        rep.update(saved);

    }

    @Test
    public void deleteTest(){

        assertTrue(rep.delete(saved.getId()));

    }

    @Test
    public void deleteFailTest(){
        assertFalse(rep.delete(0));
    }

    @Test
    public void getTest(){

        assertNotNull(rep.get(saved.getId()));

    }

    @Test
    public void getFailTest(){
        assertNull(rep.get(0));
    }

    @Test
    public void getByTiltleAndTutorTest(){
        assertNotNull(rep.getByTitleAndTutor(saved.getTitle(), saved.getTutor().getId()));
    }

    @Test
    public void getByTiltleAndTutorFailTest(){
        assertNull(rep.getByTitleAndTutor(saved.getTitle()+"aa", saved.getTutor().getId()));
    }

    @Test
    public void getStudentAverageMarkTest(){
        assertTrue(rep.getStudentAverageMark(savedTutor.getId())==0);
    }


    @Test
    public void getByStatusAndTutorIdTest(){
        assertTrue(rep.getByStatusAndTutorId(saved.getStatus().toString(), savedTutor.getId()).size()!=0);
    }

    @Test
    public void getByStatusAndTutorFalseIdTest(){
        assertTrue(rep.getByStatusAndTutorId(Status.BEFORE_START.toString(), savedTutor.getId()).size()==0);
    }

    @Test
    public void getByTitleTest(){
        assertNotNull(rep.getByTitle(saved.getTitle()));
    }

    @Test
    public void getByTitleFailTest(){
        assertNull(rep.getByTitle(null));
    }

    @Test
    public void getAllTest(){
        int count = rep.getAll().size();
        rep.delete(saved.getId());
        assertNotEquals(count, rep.getAll().size());
    }







}
