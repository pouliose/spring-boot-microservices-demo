package com.runner.users.services;

import com.runner.users.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    User find(Integer id);

    void create(User run);

    void update(User run, Integer id);

    void delete(Integer id);
}
