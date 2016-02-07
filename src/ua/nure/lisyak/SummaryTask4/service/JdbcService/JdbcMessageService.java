package ua.nure.lisyak.SummaryTask4.service.JdbcService;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Service;
import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.repository.MessageRepository;
import ua.nure.lisyak.SummaryTask4.service.MessageService;

import java.util.List;

@Service
public class JdbcMessageService implements MessageService {

    @Autowired
    private MessageRepository repository;

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }

    @Override
    public Message update(Message message) {
        return repository.update(message);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public Message get(int id) {
        return repository.get(id);
    }

    @Override
    public int getUnreadCount(int id) {
        return repository.getUnreadCount(id);
    }

    @Override
    public Message getUnread(int toId, int fromId) {
        return repository.getUnread(toId, fromId);
    }

    @Override
    public List<Message> getDialog(int fId, int sId) {
        return repository.getDialog(fId, sId);
    }
}
