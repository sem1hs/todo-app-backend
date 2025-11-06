package com.semihsahinoglu.todo_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    private TodoService todoService;
    private TodoRepository todoRepository;
    private TodoMapper todoMapper;
    private CustomUserDetails userDetails;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        todoRepository = Mockito.mock(TodoRepository.class);
        todoMapper = Mockito.mock(TodoMapper.class);
        userDetails = Mockito.mock(CustomUserDetails.class);
        objectMapper = Mockito.mock(ObjectMapper.class);

        todoService = new TodoService(todoMapper, todoRepository, objectMapper);
    }

    @Test
    void shouldCreateTodoSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setUsername("semih");

        TodoRequest todoRequest = new TodoRequest("Title", "Desc", false);

        Todo todo = Todo.builder()
                .title("Title")
                .description("Desc")
                .completed(false)
                .user(user)
                .build();
        todo.setId(null);

        Todo savedTodo = Todo.builder()
                .title("Title")
                .description("Desc")
                .completed(false)
                .user(user)
                .build();
        savedTodo.setId(10L);

        TodoResponse todoResponse = TodoResponse.builder()
                .id(10L)
                .title("Title")
                .description("Desc")
                .completed(false)
                .username(user.getUsername())
                .build();

        when(userDetails.getUser()).thenReturn(user);
        when(todoMapper.toEntity(todoRequest, user)).thenReturn(todo);
        when(todoRepository.save(todo)).thenReturn(savedTodo);
        when(todoMapper.toDto(savedTodo)).thenReturn(todoResponse);

        TodoResponse result = todoService.createTodo(todoRequest, userDetails);

        assertNotNull(result);
        assertEquals(10L, result.id());
        assertEquals("Title", result.title());
        assertEquals("Desc", result.description());
        assertEquals("semih", result.username());
        assertFalse(result.completed());

        verify(todoMapper).toEntity(todoRequest, user);
        verify(todoRepository).save(todo);
        verify(todoMapper).toDto(savedTodo);
    }
}
