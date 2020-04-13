package com.itmo.app;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import org.apache.logging.log4j.*;

public class FileWorker {
    public static final Logger log = LogManager.getLogger();

    public static Collection getCollection(String env, String fileIfEnvInvalid){
        try {
            File fileInput = new File(fileIfEnvInvalid);
            try {
                fileInput = new File(System.getenv(env));
            } catch (NullPointerException e) {
                log.info("Environment variable not found, use the default file");
                System.out.println("Переменная окружения не найдена!!! Для чтения будет использован файл по умолчанию "+fileIfEnvInvalid);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileInput));
            log.info("Collection successfully loaded from file");
            return (Collection) unmarshaller.unmarshal(inputStreamReader);
        } catch (JAXBException e) {
            System.out.println("Входной файл не корректен или вообще пуст!!!");
            return null;
        } catch (FileNotFoundException e) {
            System.out.println("Входной файл не существует или для его чтения не хватает прав доступа!!!");
            return null;
        }
    }

    public static boolean saveCollection(Collection collection, String env, String fileIfEnvInvalid){
        try {
            File fileOutput = new File(fileIfEnvInvalid);
            try {
                fileOutput = new File(System.getenv(env));
            } catch (NullPointerException e) {
                System.out.println("Переменная окружения не найдена!!! Для сохранения будет использован файл по умолчанию input.xml");
            }
            if (!fileOutput.exists()) throw new FileNotFoundException();
            JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutput);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(collection, fileOutputStream);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не существует или переменная окружения указана неправильно!!!");
        } catch (JAXBException e) {
            System.out.println("Проблемы с содержанием файла!!!");
        }
        return false;
    }
}
