package com.itmo.app;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedHashSet;

/**
 * класс, использующийся для нормальной маршаллизации и демаршаллизации
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection {
    @XmlElement(name = "studyGroup")
    private LinkedHashSet<StudyGroup> collection;

    public LinkedHashSet<StudyGroup> get() {
        return collection;
    }

    public void set(LinkedHashSet<StudyGroup> collection) {
        this.collection = collection;
    }
}
