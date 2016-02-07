package ua.nure.lisyak.SummaryTask4.repository;

import ua.nure.lisyak.SummaryTask4.model.JournalEntry;

import java.util.List;

public interface JournalEntryRepository {
    JournalEntry save(JournalEntry entry);

    JournalEntry update(JournalEntry entry);

    // false if not found
    boolean delete(int id);

    // null if not found
    JournalEntry get(int id);

    List<JournalEntry> getAllByStudentId(int id);

    boolean deleteByStudent(int studentId, int courseId);

    List<JournalEntry> getAllByCourseId(int id);

    List<JournalEntry> getAllByTutorId(int id);
}
