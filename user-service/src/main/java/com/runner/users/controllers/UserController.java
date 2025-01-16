package com.runner.users.controllers;

import com.runner.users.domain.User;
import com.runner.users.services.UserService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("api/v1/users")
@RestController
@Slf4j
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("")
    Page<User> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                       @RequestParam(defaultValue = "10") int pageSize,
                       @RequestParam(defaultValue = "id") String sortBy,
                       @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userService.findAll(pageable);
    }

    @Observed(
            name = "user.name",
            contextualName = "user-service-->end",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    @GetMapping("/{id}")
    User find(@PathVariable Integer id) {
        LOG.debug("Searching for user with {}", id);
        return userService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody User user) {
        userService.create(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody User user, @PathVariable Integer id) {
        userService.update(user, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
