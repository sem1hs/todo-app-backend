package com.semihsahinoglu.todo_app.service;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.dto.TodoUpdateRequest;
import com.semihsahinoglu.todo_app.exception.TodoNotFoundException;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    public TodoResponse updateTodo(Long todoId, TodoUpdateRequest updates, CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Todo todo = todoRepository.findTodosByUser_IdAndId(userId, todoId).orElseThrow(() -> new TodoNotFoundException("Todo bulunamadı !"));

        updates.title().ifPresent(todo::setTitle);
        updates.description().ifPresent(todo::setDescription);
        updates.completed().ifPresent(todo::setCompleted);

        Todo updatedTodo = todoRepository.save(todo);
        return todoMapper.toDto(updatedTodo);
    }

    public void deleteTodo(Long todoId, CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Todo todo = todoRepository.findTodosByUser_IdAndId(userId, todoId).orElseThrow(() -> new TodoNotFoundException("Todo bulunamadı !"));

        todoRepository.delete(todo);
    }

}
