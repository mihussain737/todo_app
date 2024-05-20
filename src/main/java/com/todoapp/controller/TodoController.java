package com.todoapp.controller;

import com.todoapp.dto.TodoDto;
import com.todoapp.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "todo app", description = "add todo, get todo, delete todo, update")
public class TodoController {

    @Autowired
    private TodoService todoService;

    //http://localhost:8080/api/todos

    @Operation(
            summary = "add todo",
            description = "this method is to save todo"
    )
    @ApiResponse(
            responseCode = "201",
            description = "todo saved"
    )

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.addDto(todoDto);
        return new ResponseEntity<>(todoDto1, HttpStatus.CREATED);
    }
    @Operation(
            summary = "get todo",
            description = "this method is to save todo"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long todoId){
        System.out.println("Inside getTodoById method with ID: " + todoId);
        TodoDto todo = todoService.getTodo(todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    //http://localhost:8080/api/todos/all?pageNo=0&pageSize=3&sortBy=id&sortDir=desc
    @GetMapping("/all")
    public ResponseEntity<List<TodoDto>>getAllTodo(
            @RequestParam(value = "pageNO",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "completed",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

    ){
        List<TodoDto> allTodos = todoService.getAllTodos(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(allTodos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@Valid @PathVariable("id") long id, @RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.updateTodoById(id, todoDto);
        return ResponseEntity.ok().body(todoDto1);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable("id") long todoId){
        todoService.deleteTodoById(todoId);
        return ResponseEntity
                .ok().body("Todo is deleted with id: "+todoId);
    }
    //http://localhost:8080/api/todos/{id}/complete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") long todoId) {
        TodoDto todoDto = todoService.completeTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }
    //http://localhost:8080/api/todos/{id}/Incomplete
    @PatchMapping("/{id}/Incomplete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") long todoId){
        TodoDto todoDto=todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }
}
