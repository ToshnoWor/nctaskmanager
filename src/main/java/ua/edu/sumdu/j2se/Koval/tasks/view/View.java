package ua.edu.sumdu.j2se.Koval.tasks.view;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Scanner;

import static ua.edu.sumdu.j2se.Koval.tasks.model.Model.*;

/**
 * Class View
 * @author Aleksei Koval
 */
public class View {
    /**
     * Method scanInt.
     * Scans only the int value from the console.
     * @return int value.
     * @throws InterruptedException
     */
    public static int scanInt() throws InterruptedException {
        Scanner scanI = new Scanner(System.in);
        while (!scanI.hasNextInt()){
            System.out.println("Enter 'number': ");

            String checkValue = scanI.nextLine();
            checkQuit(checkValue);
        }
        int number = scanI.nextInt();
        scanI.nextLine();
        return number;
    }

    /**
     * Method scanBoolean.
     * Scans only the boolean value from the console.
     * @return boolean value.
     * @throws InterruptedException
     */
    public static boolean scanBoolean() throws InterruptedException {
        Scanner scanB = new Scanner(System.in);
        while (!scanB.hasNextBoolean()){
            System.out.println("Enter 'true' or 'false': ");
            String checkValue = scanB.nextLine();
            checkQuit(checkValue);
        }
        boolean number = scanB.nextBoolean();
        scanB.nextLine();
        return number;
    }

    /**
     * Method scanString.
     * Scans only the String value from the console.
     * @return String value.
     * @throws InterruptedException
     */
    public static String scanString() throws InterruptedException {
        Scanner scanS = new Scanner(System.in);
        String title = scanS.nextLine();
        checkQuit(title);
        return title;
    }

    /**
     * Method getTime.
     * Scans since date and phased to LocalDateTime.
     * @param name variable name.
     * @return LocalDateTime value.
     * @throws InterruptedException
     */
    public static LocalDateTime getTime(String name) throws InterruptedException {
        System.out.println("Enter " + name);
        System.out.println("Enter 'year'");
        int Year = scanInt();
        System.out.println("Enter 'month'");
        int Month = scanInt();
        System.out.println("Enter 'day'");
        int DayOfMonth = scanInt();
        System.out.println("Enter 'hour'");
        int Hour = scanInt();
        System.out.println("Enter 'min'");
        int Min = scanInt();
        return LocalDateTime.of(Year,Month,DayOfMonth,Hour,Min);
    }

    /**
     * Method getInterval.
     * Scan interval.
     * @return int value - interval.
     * @throws InterruptedException
     */
    public static int getInterval() throws InterruptedException {
        System.out.println("Enter interval.");
        return scanInt();
    }

    /**
     * Method caseShowTasks.
     * Calls the rendering method of tasks in the model class.
     */
    public static void caseShowTasks(){
        showTasks();
    }

    /**
     * Method caseNextTimeTask.
     * Calls a method to render the next task in the list to execute.
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws CloneNotSupportedException
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    public static void caseNextTimeTask() throws NoSuchMethodException, InstantiationException, IllegalAccessException, CloneNotSupportedException, InvocationTargetException, InterruptedException {
        if (listIsEmpty())
            return;
        NextTimeTask();
    }

    /**
     * Initiate an exit from the program.
     * @param value word quit to leave.
     * @throws InterruptedException
     */
    public static void checkQuit(String value) throws InterruptedException {
        if (value.equals("quit")) {
            throw new InterruptedException("User returned to init execution.");
        }
    }
}
