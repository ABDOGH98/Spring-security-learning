package com.learn.controllers;


import com.learn.entities.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/students")
public class StudentManagementController {

    private final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"abdo"),
            new Student(2,"ziza"),
            new Student(3,"kika"),
            new Student(4,"koko")
    );
    @GetMapping
    @PreAuthorize(("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')"))
    public List<Student> getAllStudents(){
        return STUDENTS ;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }
    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer studentId){
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s  %s",student,student));
    }
}
