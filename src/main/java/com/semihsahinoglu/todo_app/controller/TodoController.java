package com.semihsahinoglu.todo_app.controller;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.entity.CustomUserDetails;
import com.semihsahinoglu.todo_app.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest, @AuthenticationPrincipal CustomUserDetails authenticatedPrincipal) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest, authenticatedPrincipal);
        return ResponseEntity.ok().body(todoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getUserTodos(@AuthenticationPrincipal CustomUserDetails authenticatedPrincipal) {
        List<TodoResponse> todoResponses = todoService.getUserTodos(authenticatedPrincipal);
        return ResponseEntity.ok().body(todoResponses);
    }
}
