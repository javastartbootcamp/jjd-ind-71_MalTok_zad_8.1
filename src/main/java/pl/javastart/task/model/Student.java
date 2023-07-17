package pl.javastart.task.model;

public class Student extends Person {
    private final int index;

    public Student(int index, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String info() {
        return index + " " + super.info();
    }
}
