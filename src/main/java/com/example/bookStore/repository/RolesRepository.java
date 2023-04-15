package com.example.bookStore.repository;

import com.example.bookStore.model.Roles;
import com.example.bookStore.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
