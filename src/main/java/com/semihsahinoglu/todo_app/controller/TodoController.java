package com.semihsahinoglu.todo_app.controller;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(CustomUserDetails userDetails) {
        List<TodoResponse> todoResponses = todoService.getAllTodos(userDetails);
        return ResponseEntity.ok().body(todoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable(name = "id") Long todoId, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.getTodoById(todoId, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable(name = "id") Long todoId, @RequestBody Map<String, Object> updates, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.updateTodo(todoId, updates, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }
}
