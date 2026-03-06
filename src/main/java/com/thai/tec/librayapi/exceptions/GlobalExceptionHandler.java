package com.thai.tec.librayapi.exceptions;

import com.thai.tec.librayapi.domain.dtos.ErrorDTO;
import com.thai.tec.librayapi.domain.dtos.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseErrorDTO handleArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorDTO> errosFieldList  = fieldErrors.stream().map(err->  new ErrorDTO(err.getField(), err.getDefaultMessage())).collect(Collectors.toList());
     return   new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errosFieldList);
    }

    @ExceptionHandler(DuplicatedRegisterException.class)
    public ResponseErrorDTO handleDuplicatedRegisterException( DuplicatedRegisterException e) {
         List<ErrorDTO> list = new ArrayList<>();
         return new ResponseErrorDTO(HttpStatus.CONTINUE.value(), e.getMessage(),list);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseErrorDTO handleRuntimeException  ( RuntimeException e ) {
        return new ResponseErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro in the server, please contact to support",List.of());
    }
    @ExceptionHandler(FielException.class)
    public ResponseErrorDTO  handleFieldException (FielException e ) {
        return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), "Validation Error", List.of( new ErrorDTO(e.getField(), e.getMessage())));
    }
}
