package ohtu;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;

    public void setWeek(int week) {
        this.week = week;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public int[] getExercises() {
        return exercises;
    }

    public int getHours() {
        return hours;
    }

    public int getWeek() {
        return week;
    }
    
    

    String tulosta(int[] exercises) {
        StringBuilder sb = new StringBuilder();
        sb.append("viikko " + week + ":\n"  
                + " tehtyjä tehtäviä yhteensä: " + exercises.length 
                + " (maksimi "+ exercises[week - 1] + ")"
                + ", aikaa kului " + hours +" tuntia, tehdyt tehtävät: ");
        
        for (int i = 0; i < exercises.length; i++) {
            sb.append(exercises[i]).append(" ");
            
        }
        return sb.toString();
    }
    
}