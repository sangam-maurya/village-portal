package com.example.main.Excepction;

import com.example.main.payload.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDto>handelException(ResourceNotFound resourceNotFound , WebRequest web){
        ErrorDto errorDto = new ErrorDto(resourceNotFound.getMessage(), web.getDescription(true), new Date());
        return ResponseEntity.ok(errorDto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handelException(Exception e , WebRequest web){
        ErrorDto errorDto = new ErrorDto(e.getMessage(),web.getDescription(true),new Date() );
        return ResponseEntity.ok(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
