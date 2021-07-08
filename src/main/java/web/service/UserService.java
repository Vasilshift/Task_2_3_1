package web.service;

import web.model.User;
import java.util.List;

public interface UserService {

    List<User> allUsers();

    User get(int id);

    void add(User user);

    void delete(int id);

    void update(User user);

}
