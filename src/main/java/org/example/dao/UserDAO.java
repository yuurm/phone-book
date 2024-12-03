package org.example.dao;

import org.example.model.User;

import java.util.List;

public interface UserDAO {
    void createUserTable();
    void addUser(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    void updateUser(User user);
    void deleteUser(int id);

    int getUserCount();

    List<UserLog> getUserLogs();
}

