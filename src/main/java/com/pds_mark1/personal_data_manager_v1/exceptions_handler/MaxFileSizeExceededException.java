package com.pds_mark1.personal_data_manager_v1.exceptions_handler;

public class MaxFileSizeExceededException extends RuntimeException{
    public String message;
    
    public MaxFileSizeExceededException(String message) {
        super(message);
    }    
    
}
