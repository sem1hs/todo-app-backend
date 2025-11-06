package com.semihsahinoglu.todo_app.mapper;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoResponse toDto(Todo todo) {
        if (todo == null) return null;

        TodoResponse todoResponse = TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .username(todo.getUser().getUsername())
                .build();

        return todoResponse;
    }

    public Todo toEntity(TodoRequest todoRequest, User user) {
        if (todoRequest == null || user == null) return null;

        Todo todo = Todo.builder()
                .title(todoRequest.title())
                .description(todoRequest.description())
                .completed(todoRequest.completed())
                .user(user)
                .build();

        return todo;
    }
}
