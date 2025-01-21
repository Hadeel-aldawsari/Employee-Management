package com.example.employeemanagement.ControllerAdvice;
import com.example.employeemanagement.ApiResponse.ApiException;
import com.example.employeemanagement.ApiResponse.ApiResponse;
import com.example.employeemanagement.exception.DatabaseException;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.InvalidInputException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ConcurrentModificationException;



@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    //here I added the 3 custom handler exception as required

    // EmployeeNotFoundException handler
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(msg));
    }

    // InvalidInputException handler
    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<ApiResponse> handleInvalidInputException(InvalidInputException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // DatabaseException handler
    @ExceptionHandler(value = DatabaseException.class)
    public ResponseEntity<ApiResponse> handleDatabaseException(DatabaseException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(msg));
    }



    //Here other exception added

    // MethodArgumentNotValidException handler (for validation errors)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // DataIntegrityViolationException handler (for SQL constraint violations)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // IncorrectResultSizeDataAccessException handler
    @ExceptionHandler(value = IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<ApiResponse> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // HttpRequestMethodNotSupportedException handler
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ApiResponse(msg));
    }

    // NoResourceFoundException handler (for missing resources)
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(msg));
    }

    // HttpMessageNotReadableException handler (for JSON parsing errors)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // MissingServletRequestParameterException handler (for missing query parameters)
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // NullPointerException handler
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Null Pointer Exception: " + msg));
    }

    // Database access exceptions (InvalidDataAccessApiUsageException)
    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(msg));
    }

    // Generic Exception handler for unhandled exceptions
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An unexpected error occurred: " + msg));
    }
}
