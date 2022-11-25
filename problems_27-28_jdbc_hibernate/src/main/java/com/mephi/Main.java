package com.mephi;

import com.mephi.model.User;
import com.mephi.service.UserService;
import com.mephi.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("John", "Brown", (byte)30);
        service.saveUser("Jack", "Smith", (byte)35);
        service.saveUser("Alice", "Black", (byte)20);
        service.saveUser("Rachel", "Dean", (byte)23);
        List<User> users = service.getAllUsers();
        System.out.println(users);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
