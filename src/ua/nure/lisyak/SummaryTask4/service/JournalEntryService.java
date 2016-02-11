package ua.nure.lisyak.SummaryTask4.service;

import ua.nure.lisyak.SummaryTask4.annotation.Transactional;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;

import java.util.List;

public interface JournalEntryService {

    @Transactional
    JournalEntry save(JournalEntry entry);

    @Transactional
    JournalEntry update(JournalEntry entry);

    // false if not found
    @Transactional
    boolean delete(int id);

    @Transactional
    boolean deleteByStudent(int studentId, int courseId);

    // null if not found
    JournalEntry get(int id);

    List<JournalEntry> getAllByStudentId(int id);

    List<JournalEntry> getAllByCourseId(int id);

    List<JournalEntry> getAllByTutorId(int id);

    JournalEntry getByTutorId(int tutorId, int entryId);
}
