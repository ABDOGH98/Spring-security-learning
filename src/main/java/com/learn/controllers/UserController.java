package com.learn.controllers;

import com.learn.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final List<Student> USERS = Arrays.asList(
            new Student(1,"abdo"),
            new Student(2,"ziza"),
            new Student(3,"kika"),
            new Student(4,"koko")
    );

    @GetMapping(path = "{id}")
    private Student hetUser(@PathVariable("id") long id){
        return USERS.stream()
                .filter(user->user.getId()==id)
                .findFirst()
                .orElseThrow(()->new IllegalStateException("User "+id));
    }
}
