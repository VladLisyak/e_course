package ua.nure.lisyak.SummaryTask4.service.JdbcService;

import ua.nure.lisyak.SummaryTask4.annotation.Autowired;
import ua.nure.lisyak.SummaryTask4.annotation.Service;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.repository.JournalEntryRepository;
import ua.nure.lisyak.SummaryTask4.service.JournalEntryService;

import java.util.List;

@Service
public class JdbcJournalEntryService implements JournalEntryService {
    @Autowired
    private JournalEntryRepository repository;

    @Override
    public JournalEntry save(JournalEntry entry) {
        return repository.save(entry);
    }

    @Override
    public JournalEntry update(JournalEntry entry) {
        return repository.update(entry);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public JournalEntry get(int id) {
        return repository.get(id);
    }

    @Override
    public List<JournalEntry> getAllByStudentId(int id) {
        return repository.getAllByStudentId(id);
    }

    @Override
    public boolean deleteByStudent(int studentId, int courseId) {
        return repository.deleteByStudent(studentId, courseId);
    }

    @Override
    public List<JournalEntry> getAllByCourseId(int id) {
        return repository.getAllByCourseId(id);
    }

    @Override
    public List<JournalEntry> getAllByTutorId(int id) {
        return repository.getAllByTutorId(id);
    }

    @Override
    public JournalEntry getByTutorId(int tutorId, int entryId) {
        return repository.getByTutorId(tutorId, entryId);
    }

    @Override
    public List<JournalEntry> getAllByTutorIdWithStatus(int id, String param) {
        return repository.getAllByTutorIdWithStatus(id, param);
    }
}
