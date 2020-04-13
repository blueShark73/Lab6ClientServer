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
 * команда удаляет элемент из коллекции по его id
 */
public class RemoveCommand extends Command implements CommandWithInit{
    private LinkedHashSet<StudyGroup> collection;
    private HashSet<Long> idList;
    private Long id;

    public RemoveCommand() { }

    public void init(String argument, Scanner scanner) {
        if (!FieldsValidator.checkStringParseToLong(argument, "id - это целое число!!!"))
            throw new InputFormatException();
        id = Long.parseLong(argument);
    }

    /**
     * удаление элемента
     *
     */
    @Override
    public String execute(Application application) {
        collection = application.getCollection();
        idList = application.getIdList();
        try {
            if (!idList.remove(id))
                throw new IdNotFoundException("Элемент не удален, т.к. элемента с таким id нет в коллекции!!!");
            collection.removeIf(studyGroup -> studyGroup.getId() == id);
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
        return "Элемент с id "+id+" удалён из коллекции";
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
        return "remove_by_id id : удалит элемент из коллекции по его id";
    }

    @Override
    public String toString() {
        return "remove_by_id";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
