package com.itmo.app;

import javax.xml.bind.annotation.XmlEnum;
import java.io.Serializable;
import java.util.Arrays;

/**
 * форма обучения группы
 */
@XmlEnum
public enum FormOfEducation implements Serializable {
    DISTANCE_EDUCATION("Заочная"),
    FULL_TIME_EDUCATION("Очная"),
    EVENING_CLASSES("Вечерняя школа");

    private String russian;

    FormOfEducation(String russian) {
        this.russian = russian;
    }

    /**
     * аналог valueOf только еще и сообщением об ошибке
     *
     * @param value         - строка, которую ищем
     * @param messageIfNull - сообщение, если не нашли
     */
    public static FormOfEducation getValue(String value, String messageIfNull) {
        try {
            return Arrays.stream(FormOfEducation.values()).filter(f -> f.ordinal()+1 == Integer.parseInt(value))
                    .findAny().orElse(null);
        } catch (NumberFormatException ignored){}
        System.out.println(messageIfNull);
        return null;
    }
}
