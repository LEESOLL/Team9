package com.example.market9.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDto {
    private final int statusCode;
    private final String message;

}
