package com.example.TODO.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TodoResponseData {
    private List<TaskData> content;
    private Integer notReady;
    private Integer numberOfElements;
    private Integer ready;
}
