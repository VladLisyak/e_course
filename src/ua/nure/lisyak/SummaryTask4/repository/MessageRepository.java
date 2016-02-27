package ua.nure.lisyak.SummaryTask4.repository;

import ua.nure.lisyak.SummaryTask4.model.Message;

import java.util.List;

public interface MessageRepository {

    Message save(Message message);

    Message update(Message message);

    // false if not found
    boolean delete(int id);

    // null if not found
    Message get(int id);

    /*List<Message> getByFromId(int id);

    List<Message> getByToId(int id);*/

    Integer getUnreadCount(int toId, int fromId);

    Message getUnread(int toId, int fromId);

    List<Message> getDialog(int fId, int sId);

    List<Message> getUnread(Integer userId);
}
