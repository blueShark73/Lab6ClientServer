package com.itmo.server;

import com.itmo.app.Application;
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
    public static final Logger log = LogManager.getLogger();

    public Server(int sizeOfBuffer) {
        buffer = new byte[sizeOfBuffer];
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
                ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
                ObjectInputStream obs = new ObjectInputStream(byteStream);
                Command command = (Command) obs.readObject();
                System.out.println("Сервер получил команду " + command);
                String result = processCommand(application, command);
                log.info("Server receive command "+command.toString());
                System.out.println("Команда " + command + " выполнена, посылаю ответ клиенту...");
                log.info("Command "+command.toString()+" is completed, send an answer to the client");
                Response response = new Response(result);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(response);
                byte[] b = outputStream.toByteArray();
                byteBuffer = ByteBuffer.wrap(b);
                channel.send(byteBuffer, address);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Сервер ожидает команду, а клиент отправляет нечто неизвестное...");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением...");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //исполнение команды
    public String processCommand(Application application, Command command) {
        if(command instanceof ExitCommand) {
            new SaveCommand().execute(application);
            log.info("Server receive command "+new SaveCommand().toString());
        }
        String result = command.execute(application);
        application.getCommandHistory().add(command);
        application.setCollection(command.getCollection());
        application.setIdList(command.getIdList());
        return result;
    }
}