package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;


/**
 * выводит последние 9 команд
 */
public class HistoryCommand extends Command {
    private Application application;

    @Override
    public String execute(Application application) {
        this.application = application;
        return application.getCommandHistory().getHistory();
    }

    @Override
    public LinkedHashSet<StudyGroup> getCollection() {
        return application.getCollection();
    }

    @Override
    public HashSet<Long> getIdList() {
        return application.getIdList();
    }

    @Override
    String getCommandInfo() {
        return "history : выведет последние 9 команд (без их аргументов)";
    }

    @Override
    public String toString() {
        return "history";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
