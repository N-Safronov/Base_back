package com.example.TODO.dto.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponsePost {
    private TodoResponseData data;
    private Integer statusCode;
    private Boolean success;
}
