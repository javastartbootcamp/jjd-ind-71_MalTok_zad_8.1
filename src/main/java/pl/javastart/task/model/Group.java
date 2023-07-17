package pl.javastart.task.model;

import java.util.Arrays;

public class Group {
    private String code;
    private String name;
    private Lecturer lecturer;
    private Student[] students; //studenci zapisani w grupie
    private int studentEmptyIndex = 0;
    private int maxStudents = 30; //zakładana optymalna wielkość grupy

    public Group(String code, String name, Lecturer lecturer) {
        this.code = code;
        this.name = name;
        this.lecturer = lecturer;
        this.students = new Student[maxStudents];
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Student[] getStudents() {
        return students;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getStudentEmptyIndex() {
        return studentEmptyIndex;
    }

    public void setStudentEmptyIndex(int studentEmptyIndex) {
        this.studentEmptyIndex = studentEmptyIndex;
    }

    public void addStudent(Student student) {
        if (studentEmptyIndex == students.length) {
            students = Arrays.copyOf(students, students.length * 2);
        }
        students[studentEmptyIndex] = student;
        studentEmptyIndex++;

    }

    public boolean containsStudent(int index) {
        boolean foundStudent = false;
        for (int i = 0; i < studentEmptyIndex; i++) {
            Student groupStudent = students[i];
            if (groupStudent.getIndex() == index) {
                foundStudent = true;
                break;
            }
        }
        return foundStudent;
    }

    public Student findStudentByIndex(int index) {
        Student foundStudent = null;
        for (int i = 0; i < studentEmptyIndex; i++) {
            if (students[i].getIndex() == index) {
                foundStudent = students[i];
            }
        }
        return foundStudent;
    }

    public String info() {
        StringBuilder groupInfo = new StringBuilder("Kod: " + code + "\n" + "Nazwa: " + name + "\n" + "Prowadzący: "
                + lecturer.info() + "\n");

        for (int i = 0; i < studentEmptyIndex; i++) {
            Student student = students[i];
            groupInfo.append(student.info()).append("\n");
        }
        return groupInfo.toString();
    }
}
