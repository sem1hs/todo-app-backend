package com.semihsahinoglu.todo_app.service;


import com.semihsahinoglu.todo_app.dto.TodoResponse;
import com.semihsahinoglu.todo_app.entity.Todo;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.mapper.TodoMapper;
import com.semihsahinoglu.todo_app.repository.TodoRepository;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoServiceTest {

    private TodoService todoService;

    private TodoMapper todoMapper;
    private TodoRepository todoRepository;

    // 1 Setup tamamla
    @BeforeEach
    void setup() {
        todoMapper = Mockito.mock(TodoMapper.class);
        todoRepository = Mockito.mock(TodoRepository.class);

        todoService = new TodoService(todoMapper, todoRepository);
    }

    @Test
    void shouldReturnMappedList_whenUserHasMultipleTodos() {
        Long userId = 1L;

        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        User user = Mockito.mock(User.class);
        Mockito.when(userDetails.getUser()).thenReturn(user);
        Mockito.when(user.getId()).thenReturn(userId);

        Todo todo1 = new Todo();
        Todo todo2 = new Todo();
        List<Todo> todos = List.of(todo1, todo2);
        Mockito.when(todoRepository.findTodosByUser_Id(userId)).thenReturn(todos);

        TodoResponse dto1 = Mockito.mock(TodoResponse.class);
        TodoResponse dto2 = Mockito.mock(TodoResponse.class);
        Mockito.when(todoMapper.toDto(todo1)).thenReturn(dto1);
        Mockito.when(todoMapper.toDto(todo2)).thenReturn(dto2);

        List<TodoResponse> result = todoService.getAllTodos(userDetails);

        // then
        assertThat(result).containsExactly(dto1, dto2);

        // verify
        Mockito.verify(todoRepository).findTodosByUser_Id(userId);
        Mockito.verify(todoMapper).toDto(todo1);
        Mockito.verify(todoMapper).toDto(todo2);
    }
}

