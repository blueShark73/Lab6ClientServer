package com.itmo.app;

import com.itmo.exceptions.InputFormatException;
import com.itmo.exceptions.SameIdException;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * класс приложения, в котором соединены функции отправителя команды и получателя результата
 */
public class Application {
    private LinkedHashSet<StudyGroup> collection;
    private HashSet<Long> idList;
    private CommandHistory commandHistory;
    private ZonedDateTime initializationDate;
    private static final int DEFAULT_HISTORY_SIZE = 9;

    /**
     * создаём множество идентификаторов, историю команд и устнавливаем время инициализации коллекции
     *
     * @param collection - коллекция элементов, которую мы получили из входного файла
     */
    public Application(LinkedHashSet<StudyGroup> collection) {
        this.collection = new LinkedHashSet<>();
        idList = new HashSet<>();
        if (collection != null) {
            for (StudyGroup studyGroup : collection) {
                try {
                    //проверка валидности данных из файла
                    if (!FieldsValidator.checkNumber((long) studyGroup.getName().length(), 2, 19, "У элемента некорректное имя, id: " + studyGroup.getId(), false)
                            || !FieldsValidator.checkNumber(studyGroup.getStudentsCount(), 0, 50, "У элемента некорректное кол-во студентов, id: " + studyGroup.getId(), true)
                            || !FieldsValidator.checkNumber((long) studyGroup.getGroupAdmin().getName().length(), 2, 19, "У элемента некорректное имя админа, id: " + studyGroup.getId(), false)
                            || !FieldsValidator.checkNumber(studyGroup.getGroupAdmin().getHeight(), 0, 300, "У элемента некорректный рост админа, id: " + studyGroup.getId(), true)
                            || !FieldsValidator.checkNumber((long) studyGroup.getGroupAdmin().getPassportID().length(), 7, 24, "У элемента некорректный пасспортный id админа, id: " + studyGroup.getId(), false)
                            || !FieldsValidator.checkNumber(studyGroup.getGroupAdmin().getWeight(), 0, 300, "У элемента некорректный вес админа, id: " + studyGroup.getId(), false)
                            || !FieldsValidator.checkNumber(studyGroup.getId(), 0, Long.MAX_VALUE, "У элемента некорректный id, имя элемента: " + studyGroup.getName(), false)
                            || studyGroup.getFormOfEducation() == null
                            || !FieldsValidator.checkNumber(studyGroup.getCoordinates().getX(), Long.MIN_VALUE, Long.MAX_VALUE, "У элемента некорректная координата, id: " + studyGroup.getId(), false))
                        throw new InputFormatException();
                    if (!idList.add(studyGroup.getId()))
                        throw new SameIdException("В коллекции присутствуют элементы с одинаковыми id, будет загружен только один!!!");
                    this.collection.add(studyGroup);
                } catch (InputFormatException e) {
                    System.out.println("Ошибка во входном файле, элемент с некорректными полями не будет добавлен в коллекцию!!!");
                } catch (SameIdException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        setInitializationDate();
        commandHistory = new CommandHistory(DEFAULT_HISTORY_SIZE);
    }

    //сортируем коллекцию в алфавитном порядке, чтобы потом передавать ее отсортированную клиенту
    public LinkedHashSet<StudyGroup> getSortedCollection() {
        return collection.stream().sorted(Comparator.comparing(StudyGroup::getName)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setCollection(LinkedHashSet<StudyGroup> collection) {
        this.collection = collection;
    }

    public void setInitializationDate() {
        initializationDate = ZonedDateTime.now();
    }

    public LinkedHashSet<StudyGroup> getCollection() {
        return this.collection;
    }

    public HashSet<Long> getIdList() {
        return idList;
    }

    public void setIdList(HashSet<Long> idList) {
        this.idList = idList;
    }

    /**
     * используется для команды show
     */
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    /**
     * используется для команды sum_of_students_count
     */
    public long getSumOfStudentsCount() {
        return collection.stream().mapToLong(StudyGroup::getStudentsCount).sum();
    }

    /**
     * используется для команды add_if_max
     */
    public StudyGroup getMaxStudyGroup() {
        return collection.stream().max(StudyGroup::compareTo).orElse(null);
    }

    /**
     * используется для команды add_if_min
     */
    public StudyGroup getMinStudyGroup() {
        return collection.stream().min(StudyGroup::compareTo).orElse(null);
    }

    /**
     * используется для команды history
     */
    public CommandHistory getCommandHistory() {
        return commandHistory;
    }
}
