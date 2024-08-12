package org.example.orderservice.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class OutOfStockException extends RuntimeException {
    private final int statusCode;

    public OutOfStockException( String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
