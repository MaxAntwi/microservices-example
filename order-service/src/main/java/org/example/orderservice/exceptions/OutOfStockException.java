package org.example.orderservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OutOfStockException extends RuntimeException {
    private final String message;
    private final int statusCode;
}
