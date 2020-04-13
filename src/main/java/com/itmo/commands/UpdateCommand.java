package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.FieldsValidator;
import com.itmo.app.StudyGroup;
import com.itmo.exceptions.IdNotFoundException;
import com.itmo.exceptions.InputFormatException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * команда обновит значения всех полей элемента с данным id
 */
public class UpdateCommand extends Command implements CommandWithInit{
    private LinkedHashSet<StudyGroup> collection;
    private HashSet<Long> idList;
    private StudyGroup studyGroup;

    public UpdateCommand() {
    }

    public void init(String argument, Scanner scanner) {
        try {
            if (!FieldsValidator.checkStringParseToLong(argument, "id - это целое число!!!"))
                throw new InputFormatException();
            long id = Long.parseLong(argument);
            studyGroup = new StudyGroup();
            studyGroup.setId(id);
            if (scanner != null) studyGroup.setScanner(scanner);
            studyGroup.setAllFields();
            studyGroup.setScanner(null);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода, id - число!!!");
        }
    }

    /**
     * обновляет поля
     */
    @Override
    public String execute(Application application) {
        collection = application.getCollection();
        idList = application.getIdList();
        try {
            if (!collection.removeIf(studyGroup -> studyGroup.getId() == this.studyGroup.getId()))
                throw new IdNotFoundException("Элемент нельзя обновить, т.к. элемента с таким id нет в коллекции!!!");
            collection.add(studyGroup);
            return "Элемент с id "+studyGroup.getId()+" обновлён";
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
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
        return "update id : обновит значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String toString() {
        return "update";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
