package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * абстрактный класс команды, описывает общее для всех команд поведение(внезапно)
 * также каждая новая команда должна переопределять toString - возвращать представление команды без аргументов, нужно для истории и обработчика
 */
public abstract class Command implements Serializable {
    /**
     * метод исполнения команды
     * @param application - текущее работающее приложение
     */
    public abstract String execute(Application application);

    public abstract LinkedHashSet<StudyGroup> getCollection();

    public abstract HashSet<Long> getIdList();

    /**
     * @return информация о команде, которая потом выводится с командой help
     */
    abstract String getCommandInfo();

    /**
     * метод, нужный для того, чтобы из ссылки на абстрактную команды, мы знали требуется ли этой команде аргумент
     */
    public abstract boolean withArgument();
}
