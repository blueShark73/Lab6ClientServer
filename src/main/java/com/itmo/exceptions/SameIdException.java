package com.itmo.exceptions;

/**
 * в файле с входными данными найдены элементы с одинаковыми id
 */
public class SameIdException extends RuntimeException {
    public SameIdException(String message) {
        super(message);
    }
}
