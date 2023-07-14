package pl.javastart.task.model;

public class Grade {
    private double grade;
    private String groupCode;

    public Grade(double grade, String groupCode) {
        this.grade = grade;
        this.groupCode = groupCode;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public double info() {
        return grade;
    }
}
