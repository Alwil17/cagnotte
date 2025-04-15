package com.grey.cagnotte.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CagnotteCustomException extends RuntimeException{
    private String errorCode;

    public CagnotteCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}