package dao;

import models.Driver;
import models.Auto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

public class DriverDAOImpl implements DriverDAO{

    private Session session;

    @Override
    public Driver findById(int id) {
        openSession();
        return session.get(Driver.class, id);
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
        openSession();
        return session.get(Auto.class, id);
    }

    @Override
    public List<Driver> findAll() {
        List<Driver> drivers = (List<Driver>)HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Driver").list();
        return drivers;
    }

    @Override
    public List<Auto> findAllAuto() {
        List<Auto> autos = (List<Auto>)HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Auto").list();
        return autos;
    }

    @Override
    public List<Driver> findByName(String name) {
        Query query = HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Driver where name =:param");
        query.setParameter("param", name);
        return query.list();
    }

    public void openSession() {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }

    public void closeSessionAfterFind() {
        session.close();
    }
}
