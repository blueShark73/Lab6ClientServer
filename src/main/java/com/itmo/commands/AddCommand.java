package com.itmo.commands;


import com.itmo.app.Application;
import com.itmo.app.FieldsValidator;
import com.itmo.app.StudyGroup;
import com.itmo.exceptions.InputFormatException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * команда для добавления элемента в коллекцию по имени
 */
public class AddCommand extends Command implements CommandWithInit{
    private HashSet<Long> idList;
    private LinkedHashSet<StudyGroup> collection;
    protected StudyGroup studyGroup;

    public AddCommand(){}

    public void init(String argument, Scanner scanner){
        studyGroup = executeInitialization(argument, scanner);
    }

    /**
     * исполнение
     *
     * @param application - текущее приложение
     */
    @Override
    public String execute(Application application) {
        collection = application.getCollection();
        idList = application.getIdList();
        studyGroup.setId(StudyGroup.generateId(idList));
        idList.add(studyGroup.getId());
        collection.add(studyGroup);
        return "Элемент с именем "+studyGroup.getName()+" добавлен в коллекцию";
    }

    /**
     * инициализация, которая одинакова для трех команд
     */
    public StudyGroup executeInitialization(String argument, Scanner scanner) {
        StudyGroup studyGroup = new StudyGroup();
        if (scanner != null) studyGroup.setScanner(scanner);
        if (!FieldsValidator.checkNumber((long)argument.length(), 2, 19, "Некорректное имя элемента, оно должно быть из 2-19 знаков!!!", false))
            throw new InputFormatException();
        studyGroup.setName(argument);
        studyGroup.setFields();
        studyGroup.setScanner(null);
        return studyGroup;
    }

    @Override
    public LinkedHashSet<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public HashSet<Long> getIdList() {
        return idList;
    }

    @Override
    String getCommandInfo() {
        return "add element : добавит новый элемент в коллекцию";
    }

    @Override
    public String toString() {
        return "add";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
