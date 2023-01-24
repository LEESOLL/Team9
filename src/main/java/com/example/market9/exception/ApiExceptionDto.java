package com.example.market9.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiExceptionDto {
    private String errorMessage;
    private HttpStatus httpStatus;
}
