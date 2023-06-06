package com.example.TODO.controller;

import com.example.TODO.dto.response.ApiResponse;
import com.example.TODO.dto.response.get.ApiResponseGet;
import com.example.TODO.dto.response.TaskData;
import com.example.TODO.dto.response.get.TodoResponseData;
import com.example.TODO.dto.response.post.ApiResponsePost;
import com.example.TODO.entity.ToDo;
import com.example.TODO.service.ToDoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoServiceImp toDoServiceImp;

    @Autowired
    public ToDoController(ToDoServiceImp toDoServiceImp) {
        this.toDoServiceImp = toDoServiceImp;
    }

  /*  @GetMapping
    public ResponseEntity<Page<ToDo>> getAllTodos() {
        return new ResponseEntity<>(toDoServiceImp.findAllPage(PageRequest.of(0, 10)), HttpStatus.OK);
    }
*/
    @GetMapping
    public ResponseEntity<ApiResponseGet> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage){

        Page<ToDo> savedToDo = toDoServiceImp.findAllPage(PageRequest.of(page, perPage));

        List<TaskData> content = new ArrayList<>();

        List<ToDo> toDoList = savedToDo.getContent();
        for (ToDo toDo : toDoList) {
            TaskData taskData = convertToDoToTaskData(toDo);  // Replace 'convertToDoToTaskData' with your actual conversion logic
            content.add(taskData);
        }

        List<ToDo> todos = toDoServiceImp.findAll();
        Integer notReady = (int) todos.stream().filter(todo -> !todo.getDone()).count();
        Integer ready = (int) todos.stream().filter(ToDo::getDone).count();

        TodoResponseData data = new TodoResponseData(content, notReady, todos.size(), ready);
        ApiResponseGet response = new ApiResponseGet(data, HttpStatus.OK.value(), true);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(toDoServiceImp.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponsePost> createToDo(@RequestBody ToDo toDo) {

        ToDo savedToDo = toDoServiceImp.save(toDo);

        TaskData data = new TaskData(
                savedToDo.getCreatedAt(),
                savedToDo.getId(),
                savedToDo.getDone(),
                savedToDo.getDescription(),
                savedToDo.getUpdatedAt()
        );

        ApiResponsePost response = new ApiResponsePost(data, HttpStatus.CREATED.value(), true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable("id") Integer id) {
        toDoServiceImp.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(true, 1), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteTodoAllReady() {
        toDoServiceImp.deleteAllReady(true);
        return new ResponseEntity<>(new ApiResponse(true, 1), HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse> patchStatusTrue(@RequestBody Map<String, Boolean> body) {
        Boolean status = body.get("status");
        toDoServiceImp.changeStatus(status);
        return new ResponseEntity<>(new ApiResponse(true, 1), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> patchStatusId(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, Boolean> body
    ) {
        Boolean status = body.get("status");
        toDoServiceImp.changeStatusById(id, status);
        return new ResponseEntity<>(new ApiResponse(true, 1), HttpStatus.OK);
    }

    @PatchMapping("/text/{id}")
    public ResponseEntity<ApiResponse> patchText(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> body
    ) {
        String text = body.get("text");
        toDoServiceImp.changeTextById(id, text);
        return new ResponseEntity<>(new ApiResponse(true, 1), HttpStatus.OK);
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
