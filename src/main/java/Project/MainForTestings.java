package Project;

import java.util.Arrays;

public class MainForTestings {
    public static void main(String[] args) {
        Student student1 = new Student(1, "Резянов Владислав Євгенович", "2007-05-15", "vlad.rezanov@ukma.edu.ua", 3806722, 230101, 1, 2, 2025, "Бюджет", "Активний");
        Student student2 = new Student(2, "Шевченко Андрій Миколайович", "2006-11-20", "a.shevchenko@gmail.com", 383344, 220405, 2, 3, 2024, "Контракт", "Активний");
        Student student3 = new Student(3, "Коваленко Марія Іванівна", "2004-03-10", "m.kovalenko@ukr.net", 355566, 200708, 4, 2, 2021, "Бюджет", "Академічна відпустка");

        StudentRegistry studentRegistry = new StudentRegistry();
        studentRegistry.addStudent(student1);
        studentRegistry.addStudent(student2);
        studentRegistry.addStudent(student3);

        System.out.println(studentRegistry);

        System.out.println(Arrays.toString(studentRegistry.findByGroup(2)));
        System.out.println(Arrays.toString(studentRegistry.findByPib("")));
        System.out.println(Arrays.toString(studentRegistry.findByCourse(2)));

    }
}
