package com.example.demoo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter

public class Student {
    private String id;
    private String name;
    private String description;
    private List<Course> courses;


    @Override
    public String toString() {
        return String.format(
                "Student [id=%s, name=%s, description=%s, courses=%s]", id,
                name, description, courses);
    }
}

