package dao;

import models.Driver;
import models.Auto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class DriverDAOImpl implements DriverDAO{

    @Override
    public Driver findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Driver driver = session.get(Driver.class, id);
        tx1.commit();
        session.close();
        return driver;
    }

    @Override
    public void save(Driver driver) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(driver);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Driver driver) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(driver);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Driver driver) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(driver);
        tx1.commit();
        session.close();
    }

    @Override
    public Auto findAutoById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Auto.class, id);
    }

    @Override
    public List<Driver> findAll() {
        List<Driver> drivers = (List<Driver>)HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Driver").list();
        return drivers;
    }

    @Override
    public List<Driver> findByName(String name) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User where login =:param");
        query.setParameter("param", name);
        return query.list();
    }
}
