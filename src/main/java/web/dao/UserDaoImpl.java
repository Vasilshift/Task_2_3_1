package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImpl() {
    }

    @SuppressWarnings("Unchecked")
    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("From User", User.class).getResultList();
    }

    @Override
    public User show(int id) {
        TypedQuery<User> query = entityManager.createQuery("From User where id=:id", User.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(show(id));
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}