package ohtu;

public class CourseInfo {

    private String name;
    private String term;
    private int[] exercises;

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int[] getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        return "Kurssi: " + name + ", " + term;
    }

}
