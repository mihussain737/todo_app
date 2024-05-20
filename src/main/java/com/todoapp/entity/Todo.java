package com.todoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Todo {

    @Id
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
