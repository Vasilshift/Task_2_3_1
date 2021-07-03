package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoImpl(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDaoImpl(){}

    @SuppressWarnings("Unchecked")
    @Override
    public List<User> allUsers() {
// return new ArrayList<>(users.values());
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("From User").list();
    }

    @Override
    public User show(int id) {
        String HQL = "from User where User.id = :id";
// ArrayList<User> users2 = new ArrayList<>(users.values());
// return users2.stream()
// .filter(user -> user.getId() == id)
// .findAny()
// .orElse(null);


        return sessionFactory.getCurrentSession()
                .createQuery(HQL, User.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void delete(int id) {
        String HQL = "delete from User where User.id = :id";
        sessionFactory.getCurrentSession()
                .createQuery(HQL, User.class)
                .setParameter("id", id)
                .uniqueResult();
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