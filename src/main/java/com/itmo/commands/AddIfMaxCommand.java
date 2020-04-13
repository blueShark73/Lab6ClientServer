package com.itmo.commands;


import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * команда добавляет элемент, если он больше максимального элемента коллекции
 */
public class AddIfMaxCommand extends AddCommand {
    private HashSet<Long> idList;
    private LinkedHashSet<StudyGroup> collection;

    public AddIfMaxCommand(){ }

    @Override
    public String execute(Application application) {
        idList = application.getIdList();
        collection = application.getCollection();
        studyGroup.setId(StudyGroup.generateId(idList));
        if (studyGroup.compareTo(application.getMaxStudyGroup()) > 0) {
            collection.add(studyGroup);
            idList.add(studyGroup.getId());
            return "Элемент с именем "+studyGroup.getName()+" добавлен";
        }
        return "Элемент не добавлен";
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
        return "add_if_max element : добавит новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public String toString() {
        return "add_if_max";
    }
}
