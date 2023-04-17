package com.example.askida.backend.authentication.dataAccess.abstracts;

import com.example.askida.backend.authentication.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);
}
