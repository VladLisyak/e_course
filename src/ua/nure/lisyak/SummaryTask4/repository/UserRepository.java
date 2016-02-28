package ua.nure.lisyak.SummaryTask4.repository;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User update(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

  /*  List<User> getAllByRole(Role role);*/

    List<User> getAllByRole(Role role, int userId);

    List<User> getAll();

    User getByLogin(String login);

    List<User> getAllByStatus(String param);
}
