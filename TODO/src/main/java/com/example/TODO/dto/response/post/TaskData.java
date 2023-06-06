package com.example.TODO.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskData {
    private String createdAt;
    private Integer id;
    private Boolean status;
    private String text;
    private String updatedAt;
}
