package pl.javastart.task.app;

import pl.javastart.task.logic.UniversityUtils;
import pl.javastart.task.model.Grade;
import pl.javastart.task.model.Group;
import pl.javastart.task.model.Lecturer;
import pl.javastart.task.model.Student;

public class UniversityApp {

    UniversityUtils universityUtils = new UniversityUtils();

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
        boolean lecturerExists = universityUtils.checkLecturerExists(id);
        if (lecturerExists) {
            System.out.println("Prowadzący z id " + id + " już istnieje");
        } else {
            Lecturer lecturer = new Lecturer(id, degree, firstName, lastName);
            universityUtils.addLecturer(lecturer);
        }
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
        boolean lecturerExists = universityUtils.checkLecturerExists(lecturerId);
        if (lecturerExists) {
            boolean groupExists = universityUtils.checkGroupExists(code);
            if (groupExists) {
                System.out.println("Grupa " + code + " już istnieje");
            } else {
                Lecturer lecturerById = universityUtils.findLecturerById(lecturerId);
                Group group = new Group(code, name, lecturerById);
                universityUtils.addGroup(group);
            }
        } else {
            System.out.println("Prowadzący o id " + lecturerId + " nie istnieje");
        }
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
        Student student = universityUtils.createAndAddStudent(index, firstName, lastName);
        boolean groupExists = universityUtils.checkGroupExists(groupCode);
        if (groupExists) {
            Group foundGroup = universityUtils.findGroupByCode(groupCode);
            boolean studentSigned = universityUtils.checkStudentInGroup(index, foundGroup);
            if (studentSigned) {
                System.out.println("Student o indeksie " + index + " jest już w grupie " + groupCode);
            } else {
                int emptyIndex = foundGroup.getStudentEmptyIndex();
                foundGroup.getStudents()[emptyIndex] = student;
                foundGroup.setStudentEmptyIndex(emptyIndex + 1);
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
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
        boolean groupExists = universityUtils.checkGroupExists(groupCode);
        if (groupExists) {
            Group groupByCode = universityUtils.findGroupByCode(groupCode);
            groupByCode.printInfo();
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
        boolean groupExists = universityUtils.checkGroupExists(groupCode);
        if (groupExists) {
            Group foundGroup = universityUtils.findGroupByCode(groupCode);
            boolean studentSigned = universityUtils.checkStudentInGroup(studentIndex, foundGroup);
            if (studentSigned) {
                Student foundStudent = universityUtils.findStudentByIndex(studentIndex);
                Grade[] grades = foundStudent.getGrades();
                boolean gradeFound = universityUtils.checkStudentGradeForGroup(groupCode, grades);
                if (!gradeFound) {
                    int gradeEmptyIndex = foundStudent.getGradeEmptyIndex();
                    grades[gradeEmptyIndex] = new Grade(grade, groupCode);
                    foundStudent.setGradeEmptyIndex(gradeEmptyIndex + 1);
                } else {
                    System.out.println("Student o indeksie " + studentIndex
                            + " ma już wystawioną ocenę dla grupy " + groupCode);
                }
            } else {
                System.out.println("Student o indeksie " + studentIndex + " nie jest zapisany do grupy " + groupCode);
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
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
        for (Group group : universityUtils.getGroups()) {
            if (group != null) {
                for (Student student : group.getStudents()) {
                    if (student != null) {
                        if (student.getIndex() == index) {
                            for (Grade grade : student.getGrades()) {
                                if (grade != null) {
                                    System.out.println(group.getName() + ": " + grade.info());
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
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
        boolean groupExists = universityUtils.checkGroupExists(groupCode);
        if (groupExists) {
            Group foundGroup = universityUtils.findGroupByCode(groupCode);
            for (Student student : foundGroup.getStudents()) {
                if (student != null) {
                    for (Grade grade : student.getGrades()) {
                        if (grade != null) {
                            System.out.println(student.getFirstName() + " " + student.getLastName() + ": " + grade.info());
                        }
                    }
                }
            }
        } else {
            System.out.println("Grupa " + groupCode + " nie istnieje");
        }
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
        for (Student uniqueStudent : universityUtils.getAllStudents()) {
            if (uniqueStudent != null) {
                System.out.println(uniqueStudent.info());
            }
        }
    }
}
