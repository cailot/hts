package au.org.dashboard.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import au.org.dashboard.auth.entity.ApiResponse;
import au.org.dashboard.auth.util.ResponseUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.error(ex.getMessage(), ResponseUtil.getEmptyUser(97)));
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.error(ex.getMessage(), ResponseUtil.getEmptyUser(98)));
    }
}