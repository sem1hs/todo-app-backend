package com.semihsahinoglu.todo_app.controller;

import com.semihsahinoglu.todo_app.dto.ApiResponse;
import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.dto.TodoUpdateRequest;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> createTodo(@RequestBody TodoRequest todoRequest, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.createTodo(todoRequest, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }

    @GetMapping
    public ResponseEntity<List<? extends ApiResponse>> getAllTodos(CustomUserDetails userDetails) {
        List<TodoResponse> todoResponses = todoService.getAllTodos(userDetails);
        return ResponseEntity.ok().body(todoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTodoById(@PathVariable(name = "id") Long todoId, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.getTodoById(todoId, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTodo(@PathVariable(name = "id") Long todoId, @RequestBody TodoUpdateRequest updates, CustomUserDetails userDetails) {
        TodoResponse todoResponse = todoService.updateTodo(todoId, updates, userDetails);
        return ResponseEntity.ok().body(todoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable(name = "id") Long todoId, CustomUserDetails userDetails) {
        todoService.deleteTodo(todoId, userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("Silme işlemi başarılı. Silinen id : %s", todoId));
    }
}
