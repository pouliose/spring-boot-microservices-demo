package com.runner.runs.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.runner.runs.domain.Run;
import com.runner.runs.services.RunService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("api/v1/runs")
@RestController
@AllArgsConstructor
public class RunController {

    private static final Logger LOG = LoggerFactory.getLogger(RunController.class);

    private final RunService runService;

    @GetMapping("")
    List<Run> findAll() {
        return runService.findAll();
    }

    @GetMapping("/{id}")
    Run find(@PathVariable Integer id) {
        return runService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) throws JsonProcessingException {
        runService.create(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runService.update(run, id);
    }

    @PatchMapping("/{id}")
    public void partialUpdate(@RequestBody Map<String, Object> updates, @PathVariable Integer id) {
        Run existingRun = runService.find(id);
        Run updatedRun = new Run();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Run.class, key);
            field.setAccessible(true);
            if (field.getType().equals(LocalDateTime.class) && value instanceof String) {
                LocalDateTime dateTime = LocalDateTime.parse((String) value, formatter);
                ReflectionUtils.setField(field, updatedRun, dateTime);
            } else {
                ReflectionUtils.setField(field, updatedRun, value);
            }
        });
        copyNonNullProperties(updatedRun, existingRun);
        runService.update(existingRun, id);
    }

    private void copyNonNullProperties(Object src, Object target) {
        LOG.debug("src = {}, target = {}", src, target);
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        LOG.debug("src = {}, target = {}", src, target);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        LOG.debug("emptyNames = {}", emptyNames);
        String[] arrayToReturn = emptyNames.toArray(result);
        LOG.debug("arrayToReturn = {}", arrayToReturn);
        return arrayToReturn;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public List<Run> getRunsByUserId(@PathVariable Integer userId) {
        return runService.getRunsByUserId(userId);
    }
}
