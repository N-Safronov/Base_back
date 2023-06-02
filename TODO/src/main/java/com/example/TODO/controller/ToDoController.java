package com.example.TODO.controller;

import com.example.TODO.entity.ToDo;
import com.example.TODO.service.ToDoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoServiceImp toDoServiceImp;

    @Autowired
    public ToDoController(ToDoServiceImp toDoServiceImp) {
        this.toDoServiceImp = toDoServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllTodos() {
        return new ResponseEntity<>(toDoServiceImp.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(toDoServiceImp.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        return new ResponseEntity<>(toDoServiceImp.save(toDo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Integer id) {
        toDoServiceImp.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
