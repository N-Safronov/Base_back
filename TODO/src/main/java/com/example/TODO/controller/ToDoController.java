package com.example.TODO.controller;

import com.example.TODO.dto.response.ApiResponse;
import com.example.TODO.dto.response.post.ApiResponsePost;
import com.example.TODO.dto.response.post.TaskData;
import com.example.TODO.dto.response.post.TodoResponseData;
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
    public ResponseEntity<Page<ToDo>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage){
        return new ResponseEntity<>(toDoServiceImp.findAllPage(PageRequest.of(page, perPage)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(toDoServiceImp.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponsePost> createToDo(@RequestBody ToDo toDo) {

        ToDo savedToDo = toDoServiceImp.save(toDo);

        List<TaskData> content = new ArrayList<>();
        content.add(
                new TaskData(
                        savedToDo.getCreatedAt(),
                        savedToDo.getId(),
                        savedToDo.getDone(),
                        savedToDo.getDescription(),
                        savedToDo.getUpdatedAt()
                )
        );

        List<ToDo> todos = toDoServiceImp.findAll();
        Integer notReady = (int) todos.stream().filter(todo -> !todo.getDone()).count();
        Integer ready = (int) todos.stream().filter(ToDo::getDone).count();

        TodoResponseData data = new TodoResponseData(content, notReady, todos.size(), ready);
        ApiResponsePost response = new ApiResponsePost(data, HttpStatus.CREATED.value(), true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Integer id) {
        toDoServiceImp.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteTodoAllReady() {
        toDoServiceImp.deleteAllReady(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
}
