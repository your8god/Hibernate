package dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDAOImpl implements UserDAO{

    @Override
    public User findByLogin(String login) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User where login =:param");
        query.setParameter("param", login);
        List<User> users = (List<User>)query.list();
        if (users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public User findByPasswordAndLogin(String password, String login) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User where login =:log AND password =:pass");
        query.setParameter("log", login);
        query.setParameter("pass", password);
        List<User> users = (List<User>)query.list();
        if (users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
}
