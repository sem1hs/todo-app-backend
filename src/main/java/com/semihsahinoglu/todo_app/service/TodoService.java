package com.semihsahinoglu.todo_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.exception.TodoNotFoundException;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TodoService {

    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;
    private final ObjectMapper objectMapper;

    public TodoService(TodoMapper todoMapper, TodoRepository todoRepository, ObjectMapper objectMapper) {
        this.todoMapper = todoMapper;
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
    }

    public TodoResponse createTodo(TodoRequest todoRequest, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Todo todo = todoMapper.toEntity(todoRequest, user);
        Todo saved = todoRepository.save(todo);
        return todoMapper.toDto(saved);
    }

    public List<TodoResponse> getAllTodos(CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        List<Todo> todos = todoRepository.findTodosByUser_Id(userId);
        List<TodoResponse> todoResponses = todos.stream().map(todoMapper::toDto).toList();
        return todoResponses;
    }

    public TodoResponse getTodoById(Long todoId, CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Todo todo = todoRepository.findTodosByUser_IdAndId(userId, todoId).orElseThrow(() -> new TodoNotFoundException("Todo bulunamadı !"));
        return todoMapper.toDto(todo);
    }

    public TodoResponse updateTodo(Long todoId, Map<String, Object> updates, CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Todo todo = todoRepository.findTodosByUser_IdAndId(userId, todoId).orElseThrow(() -> new TodoNotFoundException("Todo bulunamadı !"));
        try {
            objectMapper.updateValue(todo, updates);
        } catch (Exception e) {
            throw new IllegalArgumentException("Todo güncellenemedi", e);
        }
        Todo updatedTodo = todoRepository.save(todo);
        return todoMapper.toDto(updatedTodo);
    }

    public void deleteTodo(Long todoId, CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Todo todo = todoRepository.findTodosByUser_IdAndId(userId, todoId).orElseThrow(() -> new TodoNotFoundException("Todo bulunamadı !"));

        todoRepository.delete(todo);
    }

}
