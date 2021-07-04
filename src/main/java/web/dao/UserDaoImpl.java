package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public UserDaoImpl(){}

    @SuppressWarnings("Unchecked")
    @Override
    public List<User> allUsers() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From User",User.class).list();
    }

    @Override
    public User show(int id) {
        return sessionFactory.getCurrentSession()
                .get(User.class, id);
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("delete from User where id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(User user, int id) {
        User userToUpdate = show(id);

        userToUpdate.setName(user.getName());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setAge(user.getAge());
        userToUpdate.setWork(user.getWork());
        userToUpdate.setMarried(user.isMarried());
    }
}