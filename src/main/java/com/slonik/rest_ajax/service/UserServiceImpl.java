package com.slonik.rest_ajax.service;

import com.slonik.rest_ajax.entity.Role;
import com.slonik.rest_ajax.entity.User;
import com.slonik.rest_ajax.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (user.getRoles().isEmpty()) {
            roles.add(new Role(2L));
        }else {
            for (Role role :
                    user.getRoles()) {
                if (role.getId() != null){
                    roles.add(role);
                }
            }
        }
        user.setRoles(roles);
        userDao.save(user);
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        return userDao.findByUsername(login);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void edit(User user) {

        User editUser = findById(user.getId());
        Set<Role> editRoles = new HashSet<>();
        editUser.setName(user.getName());
        editUser.setUsername(user.getUsername());
        editUser.setPassword(user.getPassword());
        if (user.getRoles().isEmpty()) {
            editRoles.add(new Role(2L));
        }else {
            for (Role role :
                    user.getRoles()) {
                if (role.getId() != null){
                    editRoles.add(role);
                }
            }
        }
        editUser.setRoles(editRoles);
        userDao.editUser(editUser);
    }
}
