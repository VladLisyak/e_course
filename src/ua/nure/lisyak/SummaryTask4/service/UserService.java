package ua.nure.lisyak.SummaryTask4.service;

import ua.nure.lisyak.SummaryTask4.annotation.Cacheable;
import ua.nure.lisyak.SummaryTask4.annotation.EvictCache;
import ua.nure.lisyak.SummaryTask4.annotation.Transactional;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;

import java.util.List;

public interface UserService {

    @Transactional
    @EvictCache
    User save(User user);

    @Transactional
    @EvictCache
    User update(User user);

    // false if not found
    @Transactional
    @EvictCache
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    @Cacheable
    User getByEmail(String email);

    @Cacheable
    List<User> getAllByRole(Role role, int id);

    @Cacheable
    List<User> getAll();

    User getByLogin(String login);

    @Cacheable
    List<User> getAllByStatus(String param);
}
