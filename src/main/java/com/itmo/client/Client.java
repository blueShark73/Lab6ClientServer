package com.itmo.client;

import com.itmo.app.Handler;
import com.itmo.commands.Command;
import com.itmo.exceptions.StackIsLimitedException;
import com.itmo.server.Response;

import java.io.*;
import java.net.*;


public class Client {
    private SocketAddress socketAddress;
    private DatagramSocket socket;
    private Handler handler;
    private int scriptCount = 0;
    private static final int STACK_SIZE = 10;
    private static final int DEFAULT_BUFFER_SIZE = 65536;


    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void connect(String host, int port) throws IOException {
        try {
            InetAddress address = InetAddress.getByName(host);
            if (address == null) throw new NullPointerException();
            System.out.println(address);
            socketAddress = new InetSocketAddress(address, port);
            socket = new DatagramSocket();
            socket.connect(address, port);
        } catch (NullPointerException e) {
            System.out.println("Введенного адреса не существует!!!");
        }
    }

    public void sendCommandAndReceiveAnswer(Command command) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(byteStream);
            obs.writeObject(command);
            byte[] b = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(b, b.length, socketAddress);
            socket.send(packet);
            System.out.println("Запрос отправлен на сервер...");
            b = new byte[DEFAULT_BUFFER_SIZE];
            packet = new DatagramPacket(b, b.length);
            socket.receive(packet);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Response response = (Response) objectInputStream.readObject();
            String result = response.getAnswer();
            result = result.substring(0, result.indexOf("END"));
            System.out.println("Получен ответ от сервера: ");
            System.out.println(result);
        } catch (PortUnreachableException e) {
            System.out.println("Сервер в данный момент недоступен...");
        } catch (ClassNotFoundException e){
            System.out.println("Клиент ждал ответ в виде Response, а получил что-то непонятное...");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }

    public int getScriptCount() {
        return scriptCount;
    }

    public void incrementScriptCounter() {
        if (scriptCount >= STACK_SIZE) throw new StackIsLimitedException();
        scriptCount++;
    }

    public void decrementScriptCounter() {
        scriptCount--;
    }
}
