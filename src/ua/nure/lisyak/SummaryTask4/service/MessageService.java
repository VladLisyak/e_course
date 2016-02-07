package ua.nure.lisyak.SummaryTask4.service;

import ua.nure.lisyak.SummaryTask4.annotation.Transactional;
import ua.nure.lisyak.SummaryTask4.model.Message;

import java.util.List;

public interface MessageService {

    @Transactional
    Message save(Message message);

    @Transactional
    Message update(Message message);

    // false if not found
    @Transactional
    boolean delete(int id);

    // null if not found
    Message get(int id);

    /*List<Message> getByFromId(int id);

    List<Message> getByToId(int id);*/

    int getUnreadCount(int id);

    Message getUnread(int toId, int fromId);

    List<Message> getDialog(int fId, int sId);
}
