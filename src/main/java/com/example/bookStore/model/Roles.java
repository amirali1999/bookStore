package com.example.bookStore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name",nullable = false,unique = true)
    private String name;
    @Column(name = "description")
    private String description;

    public Roles(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Roles(String name) {
        this.name = name;
    }
}
