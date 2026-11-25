import java.util.ArrayList;
import java.time.LocalDate;

public class Workout {
    private String name;
    private ArrayList<LocalDate> workoutDays;

    public Workout(String name) {
        this.name = name;
        this.workoutDays = null;
    }

    public String getName() {
        return name;
    }

    public void addWorkoutDay(String date) {
        workoutDays.add(LocalDate.parse(date));
    }
}
