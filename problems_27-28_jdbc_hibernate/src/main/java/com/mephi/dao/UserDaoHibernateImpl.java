package com.mephi.dao;

import com.mephi.model.User;
import com.mephi.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaDelete;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String create = "CREATE TABLE IF NOT EXISTS _user (";
            String strId = "id BIGSERIAL,";
            String strName = "name VARCHAR(100),";
            String strLastName = "lastName VARCHAR(100),";
            String strAge = "age SMALLINT,";
            String strPkey = "PRIMARY KEY (id));";

            transaction = session.beginTransaction();
            session.createNativeQuery(create + strId + strName + strLastName + strAge + strPkey, User.class).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String drop = "DROP TABLE IF EXISTS _user;";

            transaction = session.beginTransaction();
            session.createNativeQuery(drop, User.class).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
           transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).orderBy(cb.asc(root.get("age")));

            transaction = session.beginTransaction();
            users = session.createQuery(cq).getResultList();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            JpaCriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);

            transaction = session.beginTransaction();
            session.createMutationQuery(cd).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
