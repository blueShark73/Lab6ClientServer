package com.itmo.commands;


import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * команда очищает коллекцию
 */
public class ClearCommand extends Command {
    @Override
    public String execute(Application application) {
        application.setInitializationDate();
        return "Коллекция очищена";
    }

    public HashSet<Long> getIdList() {
        return new HashSet<>();
    }

    public LinkedHashSet<StudyGroup> getCollection() {
        return new LinkedHashSet<>();
    }

    @Override
    String getCommandInfo() {
        return "clear : очистит коллекцию";
    }

    @Override
    public String toString() {
        return "clear";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
