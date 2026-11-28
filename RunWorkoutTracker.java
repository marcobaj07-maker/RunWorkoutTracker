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
        ArrayList<Workout> workouts = new ArrayList<>();
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
                workouts.add(w);
                System.out.println("You've done " + w.getName() + " " + w.getWeekCount() + " times in the last week, " +
                    " " + w.getTwoWeekCount() + " times in the last two weeks, and" +
                    " " + w.getMonthCount() + " times in the last month");
            }
            // Indexes through the list of workouts to find the lowest weekly count.
            ArrayList<Workout> sameWeekCount = new ArrayList<>();
            int leastTimesDoneWeek = Integer.MAX_VALUE;
            for (Workout w : workouts) {
                if (w.getWeekCount() < leastTimesDoneWeek) {
                    leastTimesDoneWeek = w.getWeekCount();
                }
            }
            // Adds to a new list every workout matching that lowest weekly count.
            for (Workout w : workouts) {
                if (w.getWeekCount() == leastTimesDoneWeek) {
                    sameWeekCount.add(w);
                }
            }
            // Suggests that workout if it is the only one in the list.
            // If several have been done the same least amount of times in the past week, check the past two weeks
            // in the same way that the past week was checked.
            if (sameWeekCount.size() == 1) {
                System.out.println(sameWeekCount.get(0).getName() + " has been done the least amount of times recently.");
            } else {
                ArrayList<Workout> sameTwoWeekCount = new ArrayList<>();
                int leastTimesDoneTwoWeek = Integer.MAX_VALUE;
                for (Workout w : workouts) {
                    if (w.getTwoWeekCount() < leastTimesDoneTwoWeek) {
                        leastTimesDoneTwoWeek = w.getTwoWeekCount();
                    }
                }
                for (Workout w : workouts) {
                    if (w.getTwoWeekCount() == leastTimesDoneTwoWeek) {
                        sameTwoWeekCount.add(w);
                    }
                }
                // Suggests that workout if it is the only one in the list of workouts least done in the past two weeks.
                // If several have been done the same least amount of times in the past two weeks, 
                // check the past month in the same way that the past two weeks were checked.
                if (sameTwoWeekCount.size() == 1) {
                    System.out.println(sameTwoWeekCount.get(0).getName() + " has been done the least amount of times recently.");
                } else {
                    ArrayList<Workout> sameMonthCount = new ArrayList<>();
                    int leastTimesDoneMonth = Integer.MAX_VALUE;
                    for (Workout w : workouts) {
                        if (w.getMonthCount() < leastTimesDoneMonth) {
                            leastTimesDoneMonth = w.getMonthCount();
                        }
                    }
                    for (Workout w : workouts) {
                        if (w.getMonthCount() == leastTimesDoneMonth) {
                            sameMonthCount.add(w);
                        }
                    }
                    // Suggest every workout least done in the past month, and leave it to the user to choose between them.
                    for (Workout wkt : sameMonthCount) {
                        System.out.println(wkt.getName() + " has been done the least amount of times recently.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter("workouts.csv"), true)) {
            boolean found = false;
            String response = IO.readln("What workout do you want to do today?\n");
            while (!found) {
                for (int i = 0; i < workouts.size(); i++) {
                    if (response.equals(workouts.get(i).getName())) {
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