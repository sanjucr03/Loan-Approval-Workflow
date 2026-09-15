package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){

	        Map<String,Object> response = new HashMap<>();

	        response.put("timestamp", LocalDateTime.now());
	        response.put("status",404);
	        response.put("message",ex.getMessage());

	        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

	    }

	    @ExceptionHandler(DuplicateApplicationException.class)
	    public ResponseEntity<?> handleDuplicate(DuplicateApplicationException ex){

	        Map<String,Object> response = new HashMap<>();

	        response.put("timestamp", LocalDateTime.now());
	        response.put("status",409);
	        response.put("message",ex.getMessage());

	        return new ResponseEntity<>(response,HttpStatus.CONFLICT);

	    }

	    @ExceptionHandler(InvalidWorkflowException.class)
	    public ResponseEntity<?> handleWorkflow(InvalidWorkflowException ex){

	        Map<String,Object> response = new HashMap<>();

	        response.put("timestamp", LocalDateTime.now());
	        response.put("status",400);
	        response.put("message",ex.getMessage());

	        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex){

	        Map<String,String> errors = new HashMap<>();

	        ex.getBindingResult().getFieldErrors().forEach(error ->
	                errors.put(error.getField(),error.getDefaultMessage()));

	        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

	    }
}
