package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    public UserServiceImpl() {
    }

    @Transactional(readOnly=true)
    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Transactional(readOnly=true)
    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not exists.");
        }
        return user;
    }
}
