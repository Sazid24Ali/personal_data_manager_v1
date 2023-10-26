package com.pds_mark1.personal_data_manager_v1.exceptions_handler;

import org.springframework.web.multipart.MaxUploadSizeExceededException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeExceededException(
            MaxUploadSizeExceededException e) {
        MaxFileSizeExceededException customException_msg = new MaxFileSizeExceededException("Maximum file size exceeded. from handleException");
        return new ResponseEntity<>(customException_msg, HttpStatus.BAD_REQUEST);
    }
}
