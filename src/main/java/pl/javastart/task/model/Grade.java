package pl.javastart.task.model;

public class Grade {
    private double grade;
    private Group group;
    private Student student;

    public Grade(double grade, Group group, Student student) {
        this.grade = grade;
        this.group = group;
        this.student = student;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String info() {
        return student.info() + ": " + grade;
    }
}
