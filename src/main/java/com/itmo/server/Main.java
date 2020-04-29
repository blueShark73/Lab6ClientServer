package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.app.Collection;
import com.itmo.app.FileWorker;
import com.itmo.exceptions.WithoutArgumentException;

import java.io.IOException;
import java.util.LinkedHashSet;

import org.apache.logging.log4j.*;

public class Main {
    public static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            if (args.length == 0) throw new WithoutArgumentException();
            int port = Integer.parseInt(args[0]);
            Application application = new Application(new LinkedHashSet<>());
            Collection collection = FileWorker.getCollection("INPUT_PATH", "input.xml");
            if (collection != null) application = new Application(collection.get());
            else log.info("Error with input file, collection is empty");
            System.out.println("Серверное приложение запущено...");
            Server server = new Server();
            server.connect(port);
            log.info("Connection is established, listen port: " + port);
            server.run(application);
        } catch (WithoutArgumentException e) {
            System.out.println("Для запуска введите порт в виде аргумента командной строки!!!");
        } catch (NumberFormatException e) {
            System.out.println("Порт - это целое число!!!");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением...");
        }
    }
}
