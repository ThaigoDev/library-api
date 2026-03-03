package com.thai.tec.librayapi.domain.dtos;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ResponseErrorDTO(int statusCode, String message, List<ErrorDTO>erros) {

     public static ResponseErrorDTO defaultResponse(String message) {
        return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }
     public static ResponseErrorDTO conflictError(String message) {
        return new ResponseErrorDTO(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
