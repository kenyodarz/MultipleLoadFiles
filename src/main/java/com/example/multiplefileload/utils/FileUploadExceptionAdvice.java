package com.example.multiplefileload.utils;

import com.example.multiplefileload.messages.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Response> handleMaxSizeException(MaxUploadSizeExceededException e){
        return ResponseEntity.badRequest().body(new Response("Verifica el Tamaño de los archivos"));
    }


    public ResponseEntity<Response> handleException(Exception e){
        return ResponseEntity.badRequest().body(new Response("Ocurrio algo en el Archivo"));
    }

}
