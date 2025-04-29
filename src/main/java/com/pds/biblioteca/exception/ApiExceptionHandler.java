package com.pds.biblioteca.exception;

import com.pds.biblioteca.entity.ErrorMessage;
import fireconnect.exceptions.FirestoreExecuteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FirestoreExecuteException.class)
    public ResponseEntity<ErrorMessage> handleFirestoreExecuteException(FirestoreExecuteException e) {
        return ResponseEntity.internalServerError().body(ErrorMessage.builder()
                .error(e.getErrorType().name())
                .message(e.getMessage())
                .build());
    }

}
