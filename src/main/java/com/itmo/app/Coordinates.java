package com.itmo.app;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * класс координат учебной группы
 */
@XmlRootElement
public class Coordinates implements Serializable {
    private Long x; //Поле не может быть null
    private long y;

    Coordinates(Long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    @XmlElement
    public void setX(Long x) {
        this.x = x;
    }

    @XmlElement
    public void setY(long y) {
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

