package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * команда закрытия приложения
 */
public class ExitCommand extends Command {
    private Application application;

    /**
     * установим флаг выхода, остальное условности
     */
    @Override
    public String execute(Application application) {
        this.application = application;
        return "Выход из приложения, коллекция сохранена в файле...";
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
        return "exit : завершит программу (без сохранения в файл)";
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
