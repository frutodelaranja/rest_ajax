package com.slonik.rest_ajax.repository;

import com.slonik.rest_ajax.entity.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    User findById(Long id);

    List<User> listUsers();

    void delete(Long id);

    void editUser(User user);

    User findByUsername(String username);
}
