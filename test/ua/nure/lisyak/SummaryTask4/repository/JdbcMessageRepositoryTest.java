package ua.nure.lisyak.SummaryTask4.repository;

import org.junit.Before;
import org.junit.Test;
import ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.holder.ThreadLocalConnectionHolder;
import ua.nure.lisyak.SummaryTask4.db.manager.HikariCPManager;
import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.repository.JdbcRepository.JdbcMessageRepository;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

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


        message = rep.save(message);

    }


    @Test
    public void saveMessageTest(){
        assertNotNull(message);
        rep.delete(message.getId());
    }

    @Test
    public void updateMessageTest(){
        message.setId(message.getId());
        String oldMessage = message.getMessage();
        message.setMessage("askkkk");
        Message m2 = rep.update(message);
        assertNotEquals(oldMessage, m2.getMessage());
        rep.delete(message.getId());
    }

    @Test
    public void updateMessageFailTest(){
        int id = message.getId();
        message.setId(0);

        assertNull(rep.update(message));
        rep.delete(id);
    }

    @Test
    public void getMessageTest(){
        assertNotNull(rep.get(message.getId()));
        rep.delete(message.getId());
    }

    @Test()
    public void getMessageFailTest(){
       assertNull(rep.get(0));
    }

    @Test
    public void deleteMessageTest(){
        assertNotNull(rep.get(message.getId()));
        assertTrue(rep.delete(message.getId()));
    }

    @Test()
    public void deleteMessageFailTest(){
       assertFalse(rep.delete(0));
    }

    @Test
    public void getUnreadCountTest(){
        int count = rep.getUnreadCount(message.getToId(), message.getFromId());
        Message m = rep.save(this.message);
        int count2 = rep.getUnreadCount(this.message.getToId(), this.message.getFromId());

        assertEquals(count+1, count2);
        rep.delete(m.getId());
    }


    @Test
    public void getDialogTest(){
        int count = rep.getDialog(message.getToId(), message.getFromId()).size();
        Message m = rep.save(this.message);
        int count2 = rep.getDialog(this.message.getToId(), this.message.getFromId()).size();

        assertEquals(count+1, count2);
        rep.delete(m.getId());
    }



}
