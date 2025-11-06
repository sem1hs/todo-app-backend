package com.semihsahinoglu.todo_app.service;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.entity.CustomUserDetails;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoMapper todoMapper;
    private final TodoRepository todoRepository;

    public TodoService(TodoMapper todoMapper, TodoRepository todoRepository) {
        this.todoMapper = todoMapper;
        this.todoRepository = todoRepository;
    }

    public TodoResponse createTodo(TodoRequest todoRequest, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Todo todo = todoMapper.toEntity(todoRequest, user);
        Todo saved = todoRepository.save(todo);
        return todoMapper.toDto(saved);
    }

    public List<TodoResponse> getUserTodos(CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        List<Todo> todos = todoRepository.findTodosByUser_Id(userId);
        List<TodoResponse> todoResponses = todos.stream().map(todoMapper::toDto).toList();
        return todoResponses;
    }
}
