package com.itmo.exceptions;

/**
 * вызывается при попытке запустить больше 9 скриптов в стеке
 */
public class StackIsLimitedException extends RuntimeException {
    @Override
    public String getMessage(){
        return "Нельзя запускать так много скриптов, стек ограничен!!!";
    }
}
