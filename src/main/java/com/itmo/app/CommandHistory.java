package com.itmo.app;


import com.itmo.commands.Command;

import java.io.Serializable;
import java.util.ArrayDeque;


/**
 * класс для ведения истории команд
 */
public class CommandHistory implements Serializable {
    private ArrayDeque<Command> history;
    private int size;

    CommandHistory(int size) {
        this.size = size;
        history = new ArrayDeque<>(size);
    }

    /**
     * добавляет команду в историю, если она заполнена, то удалим следующую по очереди команду
     * @param command - команда, которую добавляем
     */
    public void add(Command command) {
        if (history.size() == size) history.pop();
        history.addLast(command);
    }

    public Command getLastCommand(){
        return history.peekLast();
    }

    /**
     * выводит историю
     */
    public String getHistory() {
        StringBuilder result = new StringBuilder();
        result.append("Последние ").append(size).append(" команд: ").append("\n");
        history.forEach(command -> result.append(command.toString()).append("\n"));
        return result.deleteCharAt(result.length()-1).toString();
    }
}
