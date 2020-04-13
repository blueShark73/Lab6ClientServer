package com.itmo.app;


import com.itmo.exceptions.InputFormatException;

/**
 * класс для валидации данных, вводимых пользвателем с консоли или же из файла
 */
public class FieldsValidator {
    /**
     * проверка на парсинг строки в long
     *
     * @param field            - строка
     * @param messageIfInvalid - сообщение об ошибке
     */
    public static boolean checkStringParseToLong(String field, String messageIfInvalid) {
        try {
            Long.parseLong(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfInvalid);
            return false;
        }
    }

    /**
     * проверка на парсинг строки в double
     *
     * @param field            - строка
     * @param messageIfInvalid - сообщение об ошибке
     */
    public static boolean checkStringParseToDouble(String field, String messageIfInvalid) {
        try {
            Double.parseDouble(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfInvalid);
            return false;
        }
    }

    /**
     * метод проверяет входит ли число в интервал
     *
     * @param number           - число
     * @param min              - нижняя граница
     * @param max              - верхняя граница
     * @param messageIfInvalid - сообщение об ошибке
     * @param canBeNull - может ли поле быть null
     */
    public static boolean checkNumber(Long number, long min, long max, String messageIfInvalid, boolean canBeNull) {
        try {
            if (number == null && canBeNull) return true;
            if (number == null) return false;
            if (number < min || number > max) throw new InputFormatException();
            return true;
        } catch (InputFormatException e) {
            System.out.println(messageIfInvalid);
            return false;
        }
    }
}
