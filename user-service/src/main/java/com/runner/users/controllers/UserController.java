package com.runner.users.controllers;

import com.runner.users.domain.User;
import com.runner.users.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    Page<User> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                       @RequestParam(defaultValue = "10") int pageSize,
                       @RequestParam(defaultValue = "id") String sortBy,
                       @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    User find(@PathVariable Integer id){
        Optional<User> run = userRepository.findById(id);
        if(run.isEmpty()){
            throw new com.runner.users.exceptions.UserNotFoundException();
        }
        return run.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody User run){
        userRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody User run, @PathVariable Integer id){
        userRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        userRepository.deleteById(userRepository.findById(id).orElseThrow(com.runner.users.exceptions.UserNotFoundException::new).getId());
    }
}
