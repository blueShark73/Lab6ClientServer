package com.itmo.commands;


import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * команда добавляет элемент, если он меньше минимального элемента коллекции
 */
public class AddIfMinCommand extends AddCommand {
    private HashSet<Long> idList;
    private LinkedHashSet<StudyGroup> collection;

    public AddIfMinCommand(){ }

    @Override
    public String execute(Application application) {
        collection = application.getCollection();
        idList = application.getIdList();
        studyGroup.setId(StudyGroup.generateId(idList));
        if (application.getMinStudyGroup()==null || studyGroup.compareTo(application.getMinStudyGroup()) < 0) {
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
        return "add_if_min element : добавит новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String toString() {
        return "add_if_min";
    }
}

