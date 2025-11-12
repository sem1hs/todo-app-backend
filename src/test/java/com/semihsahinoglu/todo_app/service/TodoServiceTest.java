package com.semihsahinoglu.todo_app.service;

import com.semihsahinoglu.todo_app.dto.TodoRequest;
import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoService todoService;

    private CustomUserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("semih");
        userDetails = new CustomUserDetails(user);
    }

    @Test
    void createTodo_shouldSaveAndReturnDto() {
        // given
        TodoRequest request = new TodoRequest("Test Todo", "Test description", false);

        Todo todoEntity = new Todo();
        todoEntity.setTitle("Test Todo");
        todoEntity.setDescription("Test description");
        todoEntity.setCompleted(false);
        todoEntity.setUser(user);

        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setTitle("Test Todo");
        savedTodo.setDescription("Test description");
        savedTodo.setCompleted(false);
        savedTodo.setUser(user);
        savedTodo.setCreatedDate(LocalDateTime.now());

        TodoResponse expectedResponse = TodoResponse.builder()
                .id(1L)
                .title("Test Todo")
                .description("Test description")
                .completed(false)
                .createdBy("semih")
                .createdDate(savedTodo.getCreatedDate())
                .build();

        when(todoMapper.toEntity(request, user)).thenReturn(todoEntity);
        when(todoRepository.save(todoEntity)).thenReturn(savedTodo);
        when(todoMapper.toDto(savedTodo)).thenReturn(expectedResponse);

        // when
        TodoResponse result = todoService.createTodo(request, userDetails);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Test Todo");
        assertThat(result.description()).isEqualTo("Test description");
        assertThat(result.completed()).isFalse();
        assertThat(result.createdBy()).isEqualTo("semih");

        // verify correct method calls
        verify(todoMapper).toEntity(request, user);
        verify(todoRepository).save(todoEntity);
        verify(todoMapper).toDto(savedTodo);
    }
}
