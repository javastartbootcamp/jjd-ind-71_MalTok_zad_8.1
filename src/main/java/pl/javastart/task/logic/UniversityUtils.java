package pl.javastart.task.logic;

import pl.javastart.task.model.Grade;
import pl.javastart.task.model.Group;
import pl.javastart.task.model.Lecturer;
import pl.javastart.task.model.Student;

import java.util.Arrays;

public class UniversityUtils {
    private Lecturer[] lecturers = new Lecturer[100];
    private Group[] groups = new Group[100];
    private Student[] allStudents = new Student[100];
    private int countLecturers = 0;
    private int countGroups = 0;
    private int countStudents = 0;

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public Group[] getGroups() {
        return groups;
    }

    public Student[] getAllStudents() {
        return allStudents;
    }

    public void addLecturer(Lecturer lecturer) {
        if (countLecturers <= lecturers.length) {
            lecturers[countLecturers] = lecturer;
            countLecturers++;
        } else {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }
    }

    public void addGroup(Group group) {
        if (countGroups <= groups.length) {
            groups[countGroups] = group;
            countGroups++;
        } else {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }
    }

    private void addStudent(Student student) {
        if (countStudents <= allStudents.length) {
            allStudents[countStudents] = student;
            countStudents++;
        } else {
            allStudents = Arrays.copyOf(allStudents, allStudents.length * 2);
        }
    }

    public boolean checkLecturerExists(int lecturerId) {
        Lecturer lecturerById = findLecturerById(lecturerId);
        return lecturerById != null;
    }

    public Lecturer findLecturerById(int id) {
        Lecturer foundLecturer = null;
        for (Lecturer lecturer : lecturers) {
            if (lecturer != null) {
                if (lecturer.getId() == id) {
                    foundLecturer = lecturer;
                }
            } else {
                break;
            }
        }
        return foundLecturer;
    }

    public boolean checkGroupExists(String code) {
        Group groupByCode = findGroupByCode(code);
        return groupByCode != null;
    }

    public Group findGroupByCode(String code) {
        Group foundGroup = null;
        for (Group group : groups) {
            if (group != null) {
                if (group.getCode().equals(code)) {
                    foundGroup = group;
                }
            } else {
                break;
            }
        }
        return foundGroup;
    }

    public boolean checkStudentInGroup(int index, Group group) {
        boolean foundStudent = false;
        for (Student groupStudent : group.getStudents()) {
            if (groupStudent != null) {
                if (groupStudent.getIndex() == index) {
                    foundStudent = true;
                }
            } else {
                break;
            }
        }
        return foundStudent;
    }

    public Student findStudentByIndex(int index) {
        Student foundStudent = null;
        for (Group group : groups) {
            if (group != null) {
                for (Student student : group.getStudents()) {
                    if (student != null) {
                        if (student.getIndex() == index) {
                            foundStudent = student;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return foundStudent;
    }

    public boolean checkStudentGradeForGroup(String groupCode, Grade[] grades) {
        boolean found = false;
        for (Grade element : grades) {
            if (element != null) {
                if (element.getGroupCode().equals(groupCode)) {
                    found = true;
                }
            } else {
                break;
            }
        }
        return found;
    }

    public Student createAndAddStudent(int index, String firstName, String lastName) {
        Student student;
        boolean studentExists = checkStudentExists(index);
        if (studentExists) {
            System.out.println("Student o indeksie " + index + " już istnieje");
            student = findStudentByIndex(index);
        } else {
            student = new Student(index, firstName, lastName);
            addStudent(student);
        }
        return student;
    }

    private boolean checkStudentExists(int index) {
        Student studentByIndex = findStudentByIndex(index);
        return studentByIndex != null;
    }

}
