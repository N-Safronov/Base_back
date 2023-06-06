package com.example.TODO.dto.response.post;

import com.example.TODO.dto.response.TaskData;
import com.example.TODO.dto.response.get.TodoResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponsePost {
    private TaskData data;
    private Integer statusCode;
    private Boolean success;
}
