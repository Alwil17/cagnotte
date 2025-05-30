package com.grey.cagnotte.exception;

import com.grey.cagnotte.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CagnotteCustomException.class)
    public ResponseEntity<Object> handleUsernameAlreadyTaken(CagnotteCustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("You don't have permissions to access this resource.","FORBIDDEN_ACCESS_DENIED");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}

