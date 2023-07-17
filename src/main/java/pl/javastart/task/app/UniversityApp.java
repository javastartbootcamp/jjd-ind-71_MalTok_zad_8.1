package pl.javastart.task.app;

import pl.javastart.task.model.Grade;
import pl.javastart.task.model.Group;
import pl.javastart.task.model.Lecturer;
import pl.javastart.task.model.Student;

import java.util.Arrays;

public class UniversityApp {

    private Lecturer[] lecturers = new Lecturer[100];
    private Group[] groups = new Group[100];
    private Student[] allStudents = new Student[100]; //tablica unikalnych studentów
    private Grade[] grades = new Grade[100];
    private int countGrades = 0;
    private int countLecturers = 0;
    private int countGroups = 0;
    private int countAllStudents = 0;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */
    public void createLecturer(int id, String degree, String firstName, String lastName) {
        boolean lecturerExists = checkLecturerExists(id);
        if (lecturerExists) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            Lecturer lecturer = new Lecturer(id, degree, firstName, lastName);
            addLecturer(lecturer);
        }
    }

    private void addLecturer(Lecturer lecturer) {
        if (countLecturers == lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }
        lecturers[countLecturers] = lecturer;
        countLecturers++;
    }

    private boolean checkLecturerExists(int lecturerId) {
        Lecturer lecturerById = findLecturerById(lecturerId);
        return lecturerById != null;
    }

    private Lecturer findLecturerById(int id) {
        Lecturer foundLecturer = null;
        for (int i = 0; i < countLecturers; i++) {
            Lecturer lecturer = lecturers[i];
            if (lecturer.getId() == id) {
                foundLecturer = lecturer;
            }
        }
        return foundLecturer;
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */
    public void createGroup(String code, String name, int lecturerId) {
        Lecturer lecturerById = findLecturerById(lecturerId);
        if (lecturerById != null) {
            boolean groupExists = checkGroupExists(code);
            if (groupExists) {
                System.out.println("Grupa " + code + " już istnieje");
            } else {
                Group group = new Group(code, name, lecturerById);
                addGroup(group);
            }
        } else {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
        }
    }

    private void addGroup(Group group) {
        if (countGroups == groups.length) {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }
        groups[countGroups] = group;
        countGroups++;
    }

    private boolean checkGroupExists(String code) {
        Group groupByCode = findGroupByCode(code);
        return groupByCode != null;
    }

    private Group findGroupByCode(String code) {
        Group foundGroup = null;
        for (int i = 0; i < countGroups; i++) {
            Group group = groups[i];
            if (group.getCode().equals(code)) {
                foundGroup = group;
            }
        }
        return foundGroup;
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */
    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {
        Group foundGroup = findGroupByCode(groupCode);
        if (foundGroup != null) {
            if (foundGroup.containsStudent(index)) {
                System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            } else {
                Student student = new Student(index, firstName, lastName);
                addUniqueStudent(student); //dodaje studenta do tablicy unikalnych studentów
                foundGroup.addStudent(student); //dodaje do konkretnej grupy
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
    }

    private void addUniqueStudent(Student student) {
        boolean found = findUniqueStudent(student);
        if (!found) {
            if (countAllStudents == allStudents.length) {
                allStudents = Arrays.copyOf(allStudents, allStudents.length * 2);

            }
            allStudents[countAllStudents] = student;
            countAllStudents++;
        }
    }

    private boolean findUniqueStudent(Student student) {
        boolean found = false;
        for (int i = 0; i < countAllStudents; i++) {
            if (allStudents[i].getIndex() == student.getIndex()) {
                System.out.println("Student o indeksie " + student.getIndex() + " już istnieje");
                found = true;
            }
        }
        return found;
    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {
        Group groupByCode = findGroupByCode(groupCode);
        if (groupByCode != null) {
            System.out.println(groupByCode.info());
        } else {
            System.out.println("Grupa " + groupCode + " nie znaleziona");
        }
    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */

    public void addGrade(int studentIndex, String groupCode, double grade) {
        Group foundGroup = findGroupByCode(groupCode);
        if (foundGroup == null) {
            System.out.println("Grupa " + groupCode + " nie istnieje");
            return;
        }
        Student student = foundGroup.findStudentByIndex(studentIndex);
        if (student == null) {
            System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            return;
        }

        if (gradeExists(foundGroup, student)) {
            System.out.println("Student o indeksie " + studentIndex
                    + " ma już wystawioną ocenę dla grupy " + groupCode);
        }
        addGradeToArray(grade, foundGroup, student);
    }

    private void addGradeToArray(double grade, Group foundGroup, Student student) {
        if (countGrades == grades.length) {
            grades = Arrays.copyOf(grades, grades.length * 2);
        }
        grades[countGrades] = new Grade(grade, foundGroup, student);
        countGrades++;
    }

    private boolean gradeExists(Group group, Student student) {
        boolean found = false;
        for (int i = 0; i < countGrades; i++) {
            if (grades[i].getGroup().getCode().equals(group.getCode())) {
                if (grades[i].getStudent().getIndex() == student.getIndex()) {
                    found = true;
                }
            }
        }
        return found;
    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        for (int i = 0; i < countGrades; i++) {
            Grade grade = grades[i];
            if (grade.getStudent().getIndex() == index) {
                System.out.println(grade.getGroup().getName() + ": " + grade.getGrade());
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        for (int i = 0; i < countGrades; i++) {
            Grade grade = grades[i];
            if (grade.getGroup().getCode().equals(groupCode)) {
                System.out.println(grade.info());
            }
        }
        System.out.println("Grupa " + groupCode + " nie istnieje");
    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        for (int i = 0; i < countAllStudents; i++) {
            Student uniqueStudent = allStudents[i]; //drukuje z tablicy unikalnych studentów
            System.out.println(uniqueStudent.info());
        }
    }
}
