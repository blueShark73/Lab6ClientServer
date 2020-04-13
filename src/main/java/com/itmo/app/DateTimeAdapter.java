package com.itmo.app;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * класс, использующийся для маршаллизации и демаршаллизации дат
 */
public class DateTimeAdapter extends XmlAdapter<String, Date> {
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public Date unmarshal(String xml) throws ParseException {
        return dateFormat.parse(xml);
    }

    @Override
    public String marshal(Date object) {
        return dateFormat.format(object);
    }
}

