package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * команда выводит доступные команды
 */
public class HelpCommand extends Command {
    private Application application;

    @Override
    public String execute(Application application) {
        this.application = application;
        return "Доступные команды: " + "\n" +
                new HelpCommand().getCommandInfo() + "\n" +
                new InfoCommand().getCommandInfo() + "\n" +
                new ShowCommand().getCommandInfo() + "\n" +
                new AddCommand().getCommandInfo() + "\n" +
                new UpdateCommand().getCommandInfo() + "\n" +
                new RemoveCommand().getCommandInfo() + "\n" +
                new ClearCommand().getCommandInfo() + "\n" +
                new ExecuteScriptCommand().getCommandInfo() + "\n" +
                new ExitCommand().getCommandInfo() + "\n" +
                new AddIfMaxCommand().getCommandInfo() + "\n" +
                new AddIfMinCommand().getCommandInfo() + "\n" +
                new HistoryCommand().getCommandInfo() + "\n" +
                new SumOfStudentsCountCommand().getCommandInfo() + "\n" +
                new FilterStartsWithNameCommand().getCommandInfo() + "\n" +
                new FilterGreaterThanStudentsCountCommand().getCommandInfo();
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
        return "help : выводит справку по доступным командам";
    }

    @Override
    public String toString() {
        return "help";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
