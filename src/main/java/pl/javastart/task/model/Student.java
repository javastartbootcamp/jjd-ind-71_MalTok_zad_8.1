package pl.javastart.task.model;

public class Student extends Person {
    private final int index;

    private final int maxGrades = 50;
    private final Grade[] grades;
    private int gradeEmptyIndex = 0;

    public Student(int index, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
        this.grades = new Grade[maxGrades];
    }

    public Grade[] getGrades() {
        return grades;
    }

    public int getGradeEmptyIndex() {
        return gradeEmptyIndex;
    }

    public void setGradeEmptyIndex(int gradeEmptyIndex) {
        this.gradeEmptyIndex = gradeEmptyIndex;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String info() {
        return index + " " + super.info();
    }
}
