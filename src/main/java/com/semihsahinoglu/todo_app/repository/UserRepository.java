package com.semihsahinoglu.todo_app.repository;

import com.semihsahinoglu.todo_app.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "User.withAuthorities")
    Optional<User> findDistinctByUsername(String username);

    @EntityGraph(attributePaths = {"authorities", "todos"})
    Optional<User> findByUsername(String username);

}
