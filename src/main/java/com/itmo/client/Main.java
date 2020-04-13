package com.itmo.client;


import com.itmo.app.Handler;
import com.itmo.commands.*;
import com.itmo.exceptions.WithoutArgumentException;

import java.io.IOException;
import java.util.*;

/**
 * маршаллизация входных данных, создание обработчика команд и установка команд для него, создание приложения и его запуск
 */
public class Main {
    private static Client activeClient = null;

    public static Client getActiveClient() {
        return activeClient;
    }

    public static void main(String[] args) {
        try {
            if (args.length < 2) throw new WithoutArgumentException();
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Handler handler = new Handler();
            handler.addCommand(new AddCommand().toString(), new AddCommand());
            handler.addCommand(new AddIfMaxCommand().toString(), new AddIfMaxCommand());
            handler.addCommand(new AddIfMinCommand().toString(), new AddIfMinCommand());
            handler.addCommand(new ClearCommand().toString(), new ClearCommand());
            handler.addCommand(new ExecuteScriptCommand().toString(), new ExecuteScriptCommand());
            handler.addCommand(new ExitCommand().toString(), new ExitCommand());
            handler.addCommand(new FilterGreaterThanStudentsCountCommand().toString(), new FilterGreaterThanStudentsCountCommand());
            handler.addCommand(new FilterStartsWithNameCommand().toString(), new FilterStartsWithNameCommand());
            handler.addCommand(new HelpCommand().toString(), new HelpCommand());
            handler.addCommand(new HistoryCommand().toString(), new HistoryCommand());
            handler.addCommand(new InfoCommand().toString(), new InfoCommand());
            handler.addCommand(new RemoveCommand().toString(), new RemoveCommand());
            handler.addCommand(new ShowCommand().toString(), new ShowCommand());
            handler.addCommand(new SumOfStudentsCountCommand().toString(), new SumOfStudentsCountCommand());
            handler.addCommand(new UpdateCommand().toString(), new UpdateCommand());
            Client client = new Client();
            activeClient = client;
            client.connect(host, port);
            handler.setClient(client);
            client.setHandler(handler);
            System.out.println("Клиент-серверное приложение для управления коллекцией объектоов");
            System.out.println("Автор: Дьяконов Михаил P3111");
            System.out.println("Для просмотра доступных команд введите help, для выхода - exit");
            handler.run(new Scanner(System.in));
        } catch (WithoutArgumentException e) {
            System.out.println("Запускайте клиента с двумя аргументами: <host> <port>");
        } catch (NumberFormatException e) {
            System.out.println("Порт - это целое число!!!");
        } catch (IllegalArgumentException e) {
            System.out.println("Введите корректный номер порта...");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением, проверьте корректны ли аргументы программы...");
            e.printStackTrace();
        }
    }
}
