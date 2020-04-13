package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * команда выведет элементы, значение поля name которых начинается с заданной подстроки
 */
public class FilterStartsWithNameCommand extends Command implements CommandWithInit{
    private Application application;
    private String argument;

    public FilterStartsWithNameCommand(){}

    public void init(String argument, Scanner scanner){
        this.argument = argument;
    }

    /**
     * поиск и вывод на консоль
     */
    @Override
    public String execute(Application application) {
        this.application = application;
        LinkedHashSet<StudyGroup> collection = getCollection();
        StringBuilder result = new StringBuilder();
        if(collection.isEmpty()) return "Коллекция пуста...";
        application.getSortedCollection().stream().filter(studyGroup -> new StringBuffer(studyGroup.getName()).indexOf(argument)==0)
                .forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
        return result.deleteCharAt(result.length()-1).toString();
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
        return "filter_starts_with_name name : выведет элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public String toString() {
        return "filter_starts_with_name";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
