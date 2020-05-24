package dao;

import models.User;
import java.util.List;

public interface UserDAO {

    User findById(int id);

    User findByLogin(String login);

    User findByPasswordAndLogin(String password, String login);

    void save(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();
}
