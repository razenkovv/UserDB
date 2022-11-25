package com.mephi.dao;

import com.mephi.model.User;
import com.mephi.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao{
    public UserDaoJDBCImpl() {}
    @Override
    public void createUsersTable() {
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            try (Statement st = connection.createStatement()) {
                String create = "CREATE TABLE IF NOT EXISTS _user (";
                String strId = "id BIGSERIAL,";
                String strName = "name VARCHAR(100),";
                String strLastName = "lastName VARCHAR(100),";
                String strAge = "age SMALLINT,";
                String strPkey = "PRIMARY KEY (id));";
                st.executeUpdate(create + strId + strName + strLastName + strAge + strPkey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            try (Statement st = connection.createStatement()) {
                String drop = "DROP TABLE IF EXISTS _user;";
                st.executeUpdate(drop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            String insert = "INSERT INTO _user (name, lastName, age) VALUES(?, ?, ?);";
            try (PreparedStatement prSt = connection.prepareStatement(insert)) {
                prSt.setString(1, name);
                prSt.setString(2, lastName);
                prSt.setByte(3, age);
                prSt.executeUpdate();
            }
            System.out.println("User " + name + " added to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            try (Statement st = connection.createStatement()) {
                String delete = "DELETE FROM _user WHERE id = ";
                st.executeUpdate(delete + id + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            try (Statement st = connection.createStatement()) {
                String select = "SELECT * FROM _user";
                ResultSet rs = st.executeQuery(select);
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String lastName = rs.getString("lastName");
                    Byte age = rs.getByte("age");
                    users.add(new User(id, name, lastName, age));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = JDBCUtil.getDataSource().getConnection()) {
            try (Statement st = connection.createStatement()) {
                String truncate = "TRUNCATE _user RESTART IDENTITY;";
                st.executeUpdate(truncate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
