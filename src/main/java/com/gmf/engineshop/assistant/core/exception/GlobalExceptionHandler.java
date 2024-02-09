package com.gmf.engineshop.assistant.core.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.gmf.engineshop.assistant.core.model.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler({ NotFoundException.class })
   public ResponseEntity<HttpResponseDTO<String>> NotFoundExceptionHandler(NotFoundException exception) {
      return ResponseHandler.getResponseException("NotFoundException",
            exception.getMessage(),
            HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler({ AlreadyExistException.class })
   public ResponseEntity<HttpResponseDTO<String>> AlreadyExistExceptionHandler(
         AlreadyExistException exception) {
      return ResponseHandler.getResponseException("AlreadyExistException",
            exception.getMessage(),
            HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({ InvalidDataException.class })
   public ResponseEntity<HttpResponseDTO<String>> InvalidDataExceptionHandler(
         InvalidDataException exception) {
      return ResponseHandler.getResponseException("InvalidDataException",
            exception.getMessage(),
            HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({ RuntimeException.class })
   public ResponseEntity<HttpResponseDTO<String>> RuntimeExceptionHandler(RuntimeException exception) {
      return ResponseHandler.getResponseException(
            "RuntimeException :: " + exception.getClass().getSimpleName(),
            exception.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({ NoResourceFoundException.class })
   public ResponseEntity<HttpResponseDTO<String>> NoResourceFoundExceptionHandler(NoResourceFoundException exception) {
      return ResponseHandler.getResponseException(
            "NoResourceFoundException :: " + exception.getClass().getSimpleName(),
            exception.getMessage(),
            HttpStatus.FORBIDDEN);
   }

   @ExceptionHandler({ IndexOutOfBoundsException.class })
   public ResponseEntity<HttpResponseDTO<List<String>>> IndexOutOfBoundsExceptionHanlder(
         IndexOutOfBoundsException exception) {
      List<String> emptyList = new ArrayList<>();
      return ResponseHandler.getResponseException(
            emptyList,
            exception.getMessage(),
            HttpStatus.ACCEPTED);
   }
}
