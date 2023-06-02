package com.example.TODO.service;

import com.example.TODO.entity.ToDo;
import com.example.TODO.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImp{

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoServiceImp(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    public ToDo findById(Integer id) {
        return toDoRepository.findById(id).orElse(null);
    }

    public ToDo save(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public void deleteById(Integer id) {
        toDoRepository.deleteById(id);
    }

}
