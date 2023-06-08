package com.example.TODO.service;

import com.example.TODO.dto.response.TaskData;
import com.example.TODO.dto.response.get.ApiResponseGet;
import com.example.TODO.dto.response.get.TodoResponseData;
import com.example.TODO.dto.response.post.ApiResponsePost;
import com.example.TODO.entity.ToDo;
import com.example.TODO.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ApiResponseGet findAllPage(Pageable pageable) {

        Page<ToDo> savedToDo = toDoRepository.findAll(pageable);

        List<TaskData> content = new ArrayList<>();

        List<ToDo> toDoList = savedToDo.getContent();
        for (ToDo toDo : toDoList) {
            TaskData taskData = convertToDoToTaskData(toDo);  // Replace 'convertToDoToTaskData' with your actual conversion logic
            content.add(taskData);
        }

        List<ToDo> todos = findAll();
        Integer notReady = (int) todos.stream().filter(todo -> !todo.getDone()).count();
        Integer ready = (int) todos.stream().filter(ToDo::getDone).count();

        TodoResponseData data = new TodoResponseData(content, notReady, todos.size(), ready);

        return new ApiResponseGet(data, HttpStatus.OK.value(), true);
    }

    public ToDo findById(Integer id) {
        return toDoRepository.findById(id).orElse(null);
    }

    public ApiResponsePost save(ToDo toDo) {

        ToDo savedToDo = toDoRepository.save(toDo);

        TaskData data = new TaskData(
                savedToDo.getCreatedAt(),
                savedToDo.getId(),
                savedToDo.getDone(),
                savedToDo.getDescription(),
                savedToDo.getUpdatedAt()
        );

        return new ApiResponsePost(data, HttpStatus.CREATED.value(), true);
    }

    public void deleteById(Integer id) {
        toDoRepository.deleteById(id);
    }

    public void deleteAllReady(Boolean status) {
        /*List<ToDo> todos = toDoRepository.findByDoneTrue();
        toDoRepository.deleteAll(todos);*/

        toDoRepository.deleteByDoneTrue(status);
    }

    public void changeStatus(Boolean status){
        toDoRepository.changeStatus(status);
    }

    public void changeStatusById(Integer id, Boolean status){
        toDoRepository.changeStatusById(id, status);
    }

    public void changeTextById(Integer id, String status){
        toDoRepository.changeTextById(id, status);
    }

    public TaskData convertToDoToTaskData(ToDo toDo) {
        return new TaskData(
                toDo.getCreatedAt(),
                toDo.getId(),
                toDo.getDone(),
                toDo.getDescription(),
                toDo.getUpdatedAt()
        );
    }
}
