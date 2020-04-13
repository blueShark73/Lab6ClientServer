package com.itmo.exceptions;

/**
 * неправильный ввод команды с аргументом
 */
public class WithoutArgumentException extends RuntimeException {
    @Override
    public String getMessage(){
        return "Команда с аргументом должна быть введена с аргументом!!!";
    }
}
