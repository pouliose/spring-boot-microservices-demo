package com.runner.users.services;

import com.runner.users.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User find(Integer id);

    void create(User run);

    void update(User run, Integer id);

    void delete(Integer id);
}
