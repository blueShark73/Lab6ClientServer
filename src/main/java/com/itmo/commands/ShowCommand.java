package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * команда выводит все элементы коллекции на консоль
 */
public class ShowCommand extends Command {
    private Application application;

    public ShowCommand(){}

    @Override
    public String execute(Application application) {
        this.application = application;
        StringBuilder result = new StringBuilder();
        if (!getCollection().isEmpty()) {
            application.getSortedCollection().forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
            return result.deleteCharAt(result.length()-1).toString();
        } return "Коллекция пуста!!!";
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
        return "show : выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public String toString() {
        return "show";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
