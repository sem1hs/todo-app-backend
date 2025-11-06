package com.semihsahinoglu.todo_app.repository;

import com.semihsahinoglu.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.authorities WHERE u.username = :username")
    Optional<User> findByUsernameWithAuthorities(@Param("username") String username);

    @Query("""
        SELECT DISTINCT u FROM User u
        LEFT JOIN FETCH u.todos
        LEFT JOIN FETCH u.authorities
        WHERE u = :user
    """)
    Optional<User> findUserWithTodosAndAuthorities(@Param("user") User user);

}
