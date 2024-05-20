package com.todoapp.service.impl;

import com.todoapp.dto.TodoDto;
import com.todoapp.entity.Todo;
import com.todoapp.exception.ResourceAlreadyFound;
import com.todoapp.exception.ResourceNotFoundException;
import com.todoapp.repository.TodoRepository;
import com.todoapp.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TodoDto addDto(TodoDto todoDto) {
        Optional<Todo> byId = todoRepository.findById(todoDto.getId());
        if(byId.isPresent()){
            throw new ResourceAlreadyFound("Todo is already found with id: "+todoDto.getId());
        }

        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                ()->new ResourceNotFoundException("Todo Not Found with id"+todoId)
        );
       return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        List<Todo> content = todoRepository.findAll(pageable).getContent();
        return content.stream().map((todo -> modelMapper.map(todo, TodoDto.class))).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodoById(long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()->
                new ResourceNotFoundException("Todo not Found with id: "+id)
        );
        todo.setId(id);
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo save = todoRepository.save(todo);
        return modelMapper.map(save,TodoDto.class);
    }

    @Override
    public void deleteTodoById(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo is not found with id: " + id)
        );
        if (todo!=null) {
            todoRepository.deleteById(id);
        }
    }

    @Override
    public TodoDto completeTodo(long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new ResourceNotFoundException("Todo is not found with id :" + todoId)
        );
        if (todo!=null){
            todo.setCompleted(Boolean.TRUE);
        }
        Todo save = todoRepository.save(todo);
        return modelMapper.map(save,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new ResourceNotFoundException("Todo is not found with id: " + todoId)
        );
        if(todo!=null){
            todo.setCompleted(Boolean.FALSE);
        }
        Todo save = todoRepository.save(todo);
        return modelMapper.map(save, TodoDto.class);
    }
}
