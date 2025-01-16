package com.runner.users.services.implementations;

import com.runner.users.domain.User;
import com.runner.users.exceptions.UserNotFoundException;
import com.runner.users.repositories.UserRepository;
import com.runner.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        Iterator<User> iterator = userRepository.findAll().iterator();
        List<User> users = new ArrayList<>();
        iterator.forEachRemaining(users::add);
        return users;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User find(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get();
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user, Integer id) {
        if (!user.getId().equals(id)) {
            throw new IllegalArgumentException("ID in the request body does not match the ID in the path parameter");
        }
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(userRepository.findById(id).orElseThrow(UserNotFoundException::new).getId());
    }
}
