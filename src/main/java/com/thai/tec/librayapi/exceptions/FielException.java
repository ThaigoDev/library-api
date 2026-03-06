package com.thai.tec.librayapi.exceptions;

import lombok.Getter;

public class FielException extends RuntimeException {
    @Getter
    private String field;
    public FielException(String message, String field) {
        super(message);
        this.field = field;
    }
}
