package com.dezavoo.bootcamp.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dezavoo.bootcamp.userservice.dto.ErrorResponse;

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ErrorCode.USER_NOT_FOUND.name(), ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflict(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ErrorCode.USER_ALREADY_EXISTS.name(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        ErrorCode.VALIDATION_ERROR.name(),
                        "Request validation failed"
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ErrorCode.REQUEST_BODY_MISSING.name(),
                        "Request body is missing or malformed"
                ));
    }    

    /* ================= DynamoDB Specific ================= */

    @ExceptionHandler(software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException.class)
    public ResponseEntity<ErrorResponse> handleConditionalCheck(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ErrorCode.DYNAMODB_CONDITIONAL_CHECK_FAILED.name(),
                        "Conditional check failed"
                ));
    }

    @ExceptionHandler(software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException.class)
    public ResponseEntity<ErrorResponse> handleThroughput(Exception ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new ErrorResponse(
                        ErrorCode.DYNAMODB_THROTTLING_ERROR.name(),
                        "DynamoDB throttling limit exceeded"
                ));
    }

    @ExceptionHandler(software.amazon.awssdk.services.dynamodb.model.DynamoDbException.class)
    public ResponseEntity<ErrorResponse> handleDynamoGeneric(DynamoDbException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ErrorCode.DYNAMODB_INTERNAL_ERROR.name(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(software.amazon.awssdk.core.exception.SdkException.class)
    public ResponseEntity<ErrorResponse> handleAwsSdkException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorCode.AWS_SDK_ERROR.name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ErrorCode.INTERNAL_SERVER_ERROR.name(),
                        "Unexpected server error"
                ));
    }
}
