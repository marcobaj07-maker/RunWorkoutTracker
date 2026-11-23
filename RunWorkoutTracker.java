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
        ArrayList<String> workoutData = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("workouts.csv"))) {
            String line = br.readLine();
            ArrayList<String> lines = new ArrayList<>();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            for (String l : lines) {
                String[] info = l.split(",");
                String workout = info[0];
                workoutData.add(workout);
                int weekCount = Integer.parseInt(info[1]);
                int twoWeekCount = Integer.parseInt(info[2]);
                int monthCount = Integer.parseInt(info[3]);
                workoutData.add(String.valueOf(weekCount));
                workoutData.add(String.valueOf(twoWeekCount));
                workoutData.add(String.valueOf(monthCount));
                System.out.println("workouts.csv says " + workout + " was done " + 
                    weekCount + " times in the last week, " + 
                    twoWeekCount + " times in the last two weeks, and " + 
                    monthCount + " times in the last month.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("workouts.csv"), true)) {
            String response = IO.readln("What workout do you want to do today?");
            for (int i = 0; i < workoutData.size(); i++) {
                if (response.equals(workoutData.get(i))) {
                    int weekCount = Integer.parseInt(workoutData.get(i + 1));
                    int twoWeekCount = Integer.parseInt(workoutData.get(i + 2));
                    int monthCount = Integer.parseInt(workoutData.get(i + 3));
                    weekCount++;
                    twoWeekCount++;
                    monthCount++;
                    System.out.println(response + " chosen, have fun!");
                    workoutData.set(i + 1, String.valueOf(weekCount));
                    workoutData.set(i + 2, String.valueOf(twoWeekCount));
                    workoutData.set(i + 3, String.valueOf(monthCount));
                    break;
                }
            }
            for (int i = 0; i < workoutData.size(); i+=4) {
                String line = workoutData.get(i) + "," +
                    workoutData.get(i + 1) + "," +
                    workoutData.get(i + 2) + "," +
                    workoutData.get(i + 3);
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}