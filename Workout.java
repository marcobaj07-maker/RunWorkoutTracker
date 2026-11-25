import java.util.ArrayList;
import java.time.LocalDate;

public class Workout {
    private String name;
    private ArrayList<LocalDate> workoutDays;
    private ArrayList<LocalDate> weekWorkouts;
    private ArrayList<LocalDate> twoWeekWorkouts;
    private ArrayList<LocalDate> monthWorkouts;

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

    // Runs through the list of workouts and sorts them into week, two week, and month lists
    // based on when they were done. If any workout is older than a month, it gets removed.
    public void recalculate() {
        LocalDate today = LocalDate.now();
        for (LocalDate l : workoutDays) {
            int daysAgo = (int) (today.toEpochDay() - l.toEpochDay());
            if (daysAgo < 7) {
                weekWorkouts.add(l);
                twoWeekWorkouts.add(l);
                monthWorkouts.add(l);
            } else if (daysAgo < 14) {
                twoWeekWorkouts.add(l);
                monthWorkouts.add(l);
            } else if (daysAgo < 31) {
                monthWorkouts.add(l);
            } else {
                workoutDays.remove(l);
            }
        }
    }
}
