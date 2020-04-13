package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.FieldsValidator;
import com.itmo.app.StudyGroup;
import com.itmo.exceptions.InputFormatException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * команда для вывода тех учебных групп, значение поля studentsCount которых больше заданного
 */
public class FilterGreaterThanStudentsCountCommand extends Command implements CommandWithInit {
    private Application application;
    private Long studentsCount;

    public FilterGreaterThanStudentsCountCommand() {
    }

    public void init(String argument, Scanner scanner) {
        if (!FieldsValidator.checkStringParseToLong(argument, "Кол-во студентов - это целое число!!!"))
            throw new InputFormatException();
        studentsCount = Long.parseLong(argument);
    }

    /**
     * сравнение и вывод нужных групп
     */
    @Override
    public String execute(Application application) {
        this.application = application;
        LinkedHashSet<StudyGroup> collection = getCollection();
        StringBuilder result = new StringBuilder();
        application.getSortedCollection().stream().filter(studyGroup -> studyGroup.getStudentsCount() > studentsCount)
                .forEach(studyGroup -> result.append(studyGroup.toString()).append("\n"));
        if (collection.isEmpty()) return "Коллекция пуста...";
        return result.deleteCharAt(result.length() - 1).toString();
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
        return "filter_greater_than_students_count studentsCount : выведет элементы, значение поля studentsCount которых больше заданного";
    }

    @Override
    public String toString() {
        return "filter_greater_than_students_count";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
