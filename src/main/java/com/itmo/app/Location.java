package com.itmo.app;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * класс локации, указывает, гле находится админ
 */
@XmlRootElement
public class Location implements Serializable {
    private double x;
    private Long y; //Поле не может быть null
    private String name; //Строка не может быть пустой, Поле может быть null

    public Location(String name, double x, Long y) {
        setName(name);
        setY(y);
        setX(x);
    }

    public Location() {
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    @XmlElement
    public void setX(double x) {
        this.x = x;
    }

    @XmlElement
    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
