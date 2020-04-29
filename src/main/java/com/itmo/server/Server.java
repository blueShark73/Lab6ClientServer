package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.app.SerializationManager;
import com.itmo.commands.Command;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import com.itmo.commands.ExitCommand;
import com.itmo.commands.SaveCommand;
import org.apache.logging.log4j.*;


public class Server {
    private DatagramChannel channel;
    private SocketAddress address;
    private byte[] buffer;
    private SerializationManager<Command> commandSerializationManager = new SerializationManager<>();
    private SerializationManager<Response> responseSerializationManager = new SerializationManager<>();
    public static final Logger log = LogManager.getLogger();
    private static final int DEFAULT_BUFFER_SIZE = 65536;

    public Server() {
        buffer = new byte[DEFAULT_BUFFER_SIZE];
    }

    //модуль приёма соединений
    public void connect(int port) throws IOException {
        address = new InetSocketAddress(port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(address);
    }

    //чтение полученных данных и отправка ответа
    public void run(Application application) {
        try {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                do {
                    address = channel.receive(byteBuffer);
                } while (address == null);
                Command command = commandSerializationManager.readObject(buffer);
                System.out.println("Сервер получил команду " + command);
                String result = processCommand(application, command);
                log.info("Server receive command " + command.toString());
                System.out.println("Команда " + command + " выполнена, посылаю ответ клиенту...");
                log.info("Command " + command.toString() + " is completed, send an answer to the client");
                Response response = new Response(result);
                byte[] answer = responseSerializationManager.writeObject(response);
                byteBuffer = ByteBuffer.wrap(answer);
                channel.send(byteBuffer, address);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Сервер ожидает команду, а клиент отправляет нечто неизвестное...");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением...");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("Сервер ожидал команду, а получил что-то не то...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //исполнение команды
    public String processCommand(Application application, Command command) {
        if (command instanceof ExitCommand) {
            new SaveCommand().execute(application);
            log.info("Server receive command " + new SaveCommand().toString());
        }
        String result = command.execute(application);
        application.getCommandHistory().add(command);
        application.setCollection(command.getCollection());
        application.setIdList(command.getIdList());
        return result;
    }
}