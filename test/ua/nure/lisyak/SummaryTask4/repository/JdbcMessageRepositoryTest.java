package ua.nure.lisyak.SummaryTask4.repository;

import org.junit.Before;
import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.holder.ThreadLocalConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.manager.HikariCPManager;
import ua.nure.lisyak.SummaryTask4.exception.DataAccessException;
import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.repository.JdbcRepository.JdbcMessageRepository;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class JdbcMessageRepositoryTest {
    private static final ConnectionHolder holder = new ThreadLocalConnectionHolder();

    static {
        holder.set(new HikariCPManager().getConnection());
    }
    private static final MessageRepository  rep = new JdbcMessageRepository(holder);
    private Message message = new Message();

    @Before
    public void setMessage(){
        message.setRead(false);
        message.setMessage("sdasdafsg");
        message.setReferrerName("name");
        message.setFromId(1);
        message.setToId(2);
        message.setDate(new Timestamp(new Date().getTime()));
    }


    @Test
    public void saveMessageTest(){
        Message m = rep.save(message);
        assertNotNull(m);
        rep.delete(m.getId());
    }

    @Test
    public void updateMessageTest(){
        Message m = rep.save(message);
        message.setId(m.getId());
        message.setMessage("askkkk");
        Message m2 = rep.update(message);
        assertNotEquals(m.getMessage(), m2.getMessage());
        rep.delete(m.getId());
    }

    @Test(expected = DataAccessException.class)
    public void updateMessageFailTest(){
        Message m = rep.save(message);
        int id = m.getId();
        m.setId(0);

        Message m2 = rep.update(m);
        rep.delete(id);
    }

    @Test
    public void getMessageTest(){
        Message m = rep.save(message);
        assertNotNull(rep.get(m.getId()));
        rep.delete(m.getId());
    }

    @Test()
    public void getMessageFailTest(){
       assertNull(rep.get(0));
    }





}
