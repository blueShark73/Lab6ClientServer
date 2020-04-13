package com.itmo.app;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * класс учебной группы, содержит в себе проверки пользовательского ввода и описание группы
 */
@XmlRootElement
public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле может быть null
    private Person groupAdmin; //Поле не может быть null
    private Scanner in = new Scanner(System.in);
    private Date creationDateXml; //Поле нужное для нормальной десереализации

    /**
     * для команды show
     */
    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin.toString() +
                '}';
    }

    /**
     * устанавливает все поля
     */
    public void setAllFields() {
        do {
            System.out.println("Введите название учебной группы(оно должно состоять из 2-19 знаков):");
            name = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) name.length(), 2, 19, "Неккоректное имя элемента!!! Попробуйте снова.", false));
        setFields();
    }

    /**
     * устанавливает все поля кроме имени
     */
    public void setFields() {
        checkCoordinates();
        checkStudentsCount();
        checkFormOfEducation();
        checkSemesterEnum();
        checkGroupAdmin();
        setCreationDate();
    }

    public void setCreationDate() {
        creationDate = ZonedDateTime.now();
        creationDateXml = Date.from(creationDate.toInstant());
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlElement
    public void setStudentsCount(Long studentsCount) {
        this.studentsCount = studentsCount;
    }

    @XmlElement
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    @XmlElement
    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    @XmlElement
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Date getCreationDateXml() {
        return creationDateXml;
    }

    /**
     * для маршаллизации и демаршаллизации используем Date, который при чтении из файла парсится в ZonedDateTime
     */
    @XmlElement
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    public void setCreationDateXml(Date creationDateXml) {
        creationDate = ZonedDateTime.ofInstant(creationDateXml.toInstant(), ZoneId.systemDefault());
        this.creationDateXml = creationDateXml;
    }

    /**
     * устанавливает сканер для считывания пользовательского ввода не только с консоли
     */
    public void setScanner(Scanner scanner) {
        in = scanner;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    /**
     * проверка корректности введённых пользователем координат
     */
    public void checkCoordinates() {
        String nextLine;
        do {
            System.out.println("Введите координату Х учебной группы: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!! Попробуйте снова."));
        Long x = Long.parseLong(nextLine);
        do {
            System.out.println("Введите координату Y учебной группы: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!! Попробуйте снова."));
        long y = Long.parseLong(nextLine);
        this.coordinates = new Coordinates(x, y);
    }

    /**
     * проверка корректности введённого пользователем кол-ва студентов
     */
    public void checkStudentsCount() {
        String nextLine;
        do {
            System.out.println("Введите кол-во студентов в группе: ");
            nextLine = in.nextLine();
        } while ((!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, кол-во студентов - это целое число!!!")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 50, "Некорректное кол-во студентов, должно быть от 0 до 50!!! Попробуйте снова.", true))
                && !nextLine.equals(""));
        if (!nextLine.equals("")) studentsCount = Long.parseLong(nextLine);
        else {
            System.out.println("Значение поля воспринято как null");
            studentsCount = null;
        }
    }

    /**
     * проверка корректности введённой пользователем формы обучения
     */
    public void checkFormOfEducation() {
        String nextLine;
        do {
            System.out.println("Введите форму обучения(1 - Очная, 2- Заочная, 3 - Вечерняя школа): ");
            nextLine = in.nextLine();
        } while (FormOfEducation.getValue(nextLine, "Такой формы обучения у нас нет!!! Попробуйте снова.") == null);
        formOfEducation = FormOfEducation.getValue(nextLine, "");
    }

    /**
     * проверка корректности введённого пользователем номера семестра
     */
    public void checkSemesterEnum() {
        String nextLine;
        do {
            System.out.println("Введите номер семестра(3/4/5/6/8): ");
            nextLine = in.nextLine();
        } while (Semester.getValue(nextLine, "Вводите корректный номер семестра из предложенных вариантов!!!") == null && !nextLine.equals(""));
        if (nextLine.equals("")) {
            semesterEnum = null;
            System.out.println("Значение поля воспринято как null");
        } else semesterEnum = Semester.getValue(nextLine, "");
    }

    /**
     * проверка корректности введённого пользователем админа группы
     */
    public void checkGroupAdmin() {
        groupAdmin = new Person();
        System.out.println("Пришло время выбрать админа!");
        String nextLine;
        do {
            System.out.println("Введите имя админа(оно должно состоять из 2-19 знаков): ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 2, 19, "Неккоректное имя админа!!! Попробуйте снова.", false));
        groupAdmin.setName(nextLine);
        do {
            System.out.println("Введите рост(>0 обязательно): ");
            nextLine = in.nextLine();
        } while ((!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, рост - это число!!! Попробуйте снова.")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 300, "Некорректный рост, студенты у нас обычно от 0 до 300 см!!! Попробуйте снова.", true))
                && !nextLine.equals(""));
        if (!nextLine.equals("")) groupAdmin.setHeight(Long.parseLong(nextLine));
        else System.out.println("Значение поля воспринято как null");
        do {
            System.out.println("Введите вес(>0): ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, вес - это число!!! Попробуйте снова.")
                || !FieldsValidator.checkNumber(Long.parseLong(nextLine), 0, 300, "Некорректный вес, студенты у нас обычно от 0 до 300 кг!!! Попробуйте снова.", false));
        groupAdmin.setWeight(Long.parseLong(nextLine));
        do {
            System.out.println("Введите номер и серию паспорта(7-24 знака): ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkNumber((long) nextLine.length(), 7, 24, "Ошибка ввода!!! Попробуйте снова.", false));
        groupAdmin.setPassportID(nextLine);
        do {
            System.out.println("Введите местоположение админа. Сначала координату Х: ");
            nextLine = in.nextLine();
        } while (!FieldsValidator.checkStringParseToDouble(nextLine, "Ошибка ввода, координата - это число!!!"));
        double xForLocation = Double.parseDouble(nextLine);
        do {
            System.out.println("Теперь координату Y: ");
            nextLine = in.nextLine();
        }
        while (!FieldsValidator.checkStringParseToLong(nextLine, "Ошибка ввода, координата - это целое число!!!"));
        Long yForLocation = Long.parseLong(nextLine);
        System.out.println("Введите название этого места(пустая строка, если такого не имеется): ");
        String nameForLocation = in.nextLine();
        Location location = new Location(nameForLocation, xForLocation, yForLocation);
        groupAdmin.setLocation(location);
    }

    /**
     * генерация случайного и уникального идентификатора группы
     *
     * @param idList - лист идентификаторов, относительно которого id должен быть уникален
     */
    public static long generateId(HashSet<Long> idList) {
        Random random = new Random();
        boolean goodId = false;
        long id = Long.MAX_VALUE;
        while (!goodId) {
            id = random.nextLong();
            if (id <= 0) continue;
            goodId = idList.add(id);
        }
        return id;
    }

    /**
     * сравнение по имени группы
     *
     * @param studyGroup - элемент с которым идет сравнение
     */
    @Override
    public int compareTo(StudyGroup studyGroup) {
        if(studyGroup==null) return 1;
        if(studyGroup.getName()==null) return 1;
        if(getName()==null) return -1;
        return getName().compareTo(studyGroup.getName());
    }
}
