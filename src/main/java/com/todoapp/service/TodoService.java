package com.todoapp.service;

import com.todoapp.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addDto (TodoDto todoDto);
    TodoDto getTodo(Long todoId);
    List<TodoDto> getAllTodos(int pageNo, int pageSize, String sortBy, String sortDir);

    TodoDto updateTodoById(long id, TodoDto todoDto);
    void deleteTodoById(long id);

    TodoDto completeTodo(long todoId);

    TodoDto inCompleteTodo(long todoId);
}
