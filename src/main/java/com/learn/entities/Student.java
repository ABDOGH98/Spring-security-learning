package com.learn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class Student {
    private long id;
    private String username;
}
