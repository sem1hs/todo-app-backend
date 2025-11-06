package com.semihsahinoglu.todo_app.repository;

import com.semihsahinoglu.todo_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findTodosByUser_Id(Long userId);
    Optional<Todo> findTodosByUser_IdAndId(Long userId, Long todoId);
}
