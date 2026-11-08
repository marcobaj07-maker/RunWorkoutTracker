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
        try (BufferedReader br = new BufferedReader(new FileReader("workouts.csv"))) {
            String[] info = br.readLine().split(",");
            String text = info[0];
            int count = Integer.parseInt(info[1]);
            System.out.println("workouts.csv says " + text + " " + count + " time(s)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}