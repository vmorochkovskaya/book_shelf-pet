package org.example.app.exceptions;

public class FileNotFoundException extends RuntimeException {
    private String message;

    public FileNotFoundException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}