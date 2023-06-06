package com.example.TODO.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskData {
    private LocalDateTime createdAt;
    private Integer id;
    private Boolean status;
    private String text;
    private LocalDateTime updatedAt;
}
