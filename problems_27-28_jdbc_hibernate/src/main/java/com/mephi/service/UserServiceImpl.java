package com.mephi.service;

import com.mephi.dao.UserDaoJDBCImpl;
import com.mephi.dao.UserDaoHibernateImpl;
import com.mephi.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    //private final UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
    private final UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
    @Override
    public void createUsersTable() {
        dao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
