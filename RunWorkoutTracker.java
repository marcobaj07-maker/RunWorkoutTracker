import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

/** 
 * RunWorkoutTracker
 * 
 * This program is intended to read workouts from a csv file, print to the terminal
 * whichever one was done the least amount of times in the last week/2 weeks/month,
 * take the user input on which one is being done that day, update the count for that workout,
 * and write the updated list back to the csv file.
 * 
 * @author Marco Bajuyo
 * 
 * @version 8 Nov 2026
 */
public class RunWorkoutTracker {
    public static void main(String[] args) {
        ArrayList<String> workouts = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("workouts.csv"))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            for (String l : lines) {
                String[] info = l.split(",");
                Workout w = new Workout(info[0]);
                for (int i = 1; i < info.length; i++) {
                    w.addWorkoutDay(info[i]);
                }
                w.recalculate();
                workouts.add(w.getName());
                System.out.println("You've done " + w.getName() + " " + w.getWeekCount() + " times in the last week, " +
                    " " + w.getTwoWeekCount() + " times in the last two weeks, and" +
                    " " + w.getMonthCount() + " times in the last month");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("workouts.csv"), true)) {
            boolean found = false;
            String response = IO.readln("What workout do you want to do today?\n");
            while (!found) {
                for (int i = 0; i < workouts.size(); i++) {
                    if (response.equals(workouts.get(i))) {
                        System.out.println(response + " chosen, have fun!");
                        lines.set(i, lines.get(i) + "," + LocalDate.now());
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                System.out.println("Invalid Choice, try again.");
                response = IO.readln("What workout do you want to do today?\n");
            }
            for (String l : lines) {
                pw.println(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}