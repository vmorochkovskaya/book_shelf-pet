package org.example.app.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;

@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {
    private final Logger logger = Logger.getLogger(FileExceptionAdvice.class);

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException exc) {
        logger.warn("File Not Found: " + exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File Not Found: " + exc.getMessage());
    }

    @ExceptionHandler(value = { ServletException.class })
    public ResponseEntity servletException(ServletException exc) {
        String message = exc.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (message.equals("token_expired")) {
            System.out.println("token_expired");
            httpStatus = HttpStatus.UNAUTHORIZED;
            message = "the token is expired and not valid anymore";
        }
        return ResponseEntity.status(httpStatus).body("File Not Found: " + message);
    }
}