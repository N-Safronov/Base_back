package com.example.TODO.dto.response.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseGet {
    private TodoResponseData data;
    private Integer statusCode;
    private Boolean success;
}
