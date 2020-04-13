package com.itmo.exceptions;

/**
 * пользовательский ввод некорректен
 */
public class InputFormatException extends RuntimeException{
    @Override
    public String getMessage(){
        return "Ошибка ввода!!!";
    }
}
