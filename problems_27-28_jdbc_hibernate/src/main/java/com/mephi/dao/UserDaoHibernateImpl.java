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
import org.hibernate.query.criteria.JpaCriteriaQuery;

import javax.persistence.criteria.CriteriaDelete;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{

    @Override
    public void createUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String create = "CREATE TABLE IF NOT EXISTS _user (";
            String strId = "id BIGSERIAL,";
            String strName = "name VARCHAR(100),";
            String strLastName = "lastName VARCHAR(100),";
            String strAge = "age SMALLINT,";
            String strPkey = "PRIMARY KEY (id));";

            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(create + strId + strName + strLastName + strAge + strPkey, User.class).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String drop = "DROP TABLE IF EXISTS _user;";

            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(drop, User.class).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = new User(name, lastName, age);
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            JpaCriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);

            Transaction transaction = session.beginTransaction();
            users = session.createQuery(cq).getResultList();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            JpaCriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);

            Transaction transaction = session.beginTransaction();
            session.createMutationQuery(cd).executeUpdate();
            transaction.commit();
        } catch (IllegalArgumentException | HibernateException e) {
            e.printStackTrace();
        }
    }
}
