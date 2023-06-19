package com.booksystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    public ErrorResponse(String message) {
        this.message = message;
    }
    private String message;
    private int errorCode;
}
