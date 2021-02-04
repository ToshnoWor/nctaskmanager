package ua.edu.sumdu.j2se.Koval.tasks.view;

import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;

import java.time.LocalDateTime;
import java.util.Scanner;


/**
 * Class View
 * @author Aleksei Koval
 */
public class View {

    private int action;

    public View(){}

    /**
     * Method scanInt.
     * Scans only the int value from the console.
     * @return int value.
     * @throws InterruptedException
     */
    public int scanInt() throws InterruptedException {
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
    public boolean scanBoolean() throws InterruptedException {
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
    public String scanString() throws InterruptedException {
        Scanner scanS = new Scanner(System.in);
        String title = scanS.nextLine();
        checkQuit(title);
        return title;
    }

    /**
     * Scan action
     * @return some int
     * @throws InterruptedException
     */
    public int scanAction() throws InterruptedException {
        action = scanInt();
        return getAction();
    }

    /**
     * Return action in class
     * @return some int
     */
    public int getAction(){
        return action;
    }

    /**
     * Scan time
     * @param name - name time
     * @throws InterruptedException
     */
    public LocalDateTime scanTime(String name) throws InterruptedException {
        if (name.equals("'start'")) {
            System.out.println("Take the current time?(True)");
            if (scanBoolean())
                return LocalDateTime.now();
        }
        if (name.equals("'end'")){
            System.out.println("Take the time of the next day.(True)");
            if (scanBoolean())
                return LocalDateTime.now().plusDays(1);
        }
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
        LocalDateTime time;
        return time = LocalDateTime.of(Year,Month,DayOfMonth,Hour,Min);
    }

    /**
     * Method getTime.
     * Scans since date and phased to LocalDateTime.
     * @param name variable name.
     * @return LocalDateTime value.
     * @throws InterruptedException
     */
    public LocalDateTime getTime(String name) throws InterruptedException {
        return scanTime(name);
    }

    /**
     * Method getInterval.
     * Scan interval.
     * @return int value - interval.
     * @throws InterruptedException
     */
    public int getInterval() throws InterruptedException {
        System.out.println("Enter interval.");
        return scanInt();
    }

    /**
     * Method caseShowTasks.
     * Calls the rendering method of tasks in the model class.
     */
    public void caseShowTasks(){
        Controller.showTasks();
    }

    /**
     * Method checkQuit.
     * Initiate an exit from the program.
     * @param value word quit to leave.
     * @throws InterruptedException
     */
    public void checkQuit(String value) throws InterruptedException {
        if (value.equals("quit")) {
            throw new InterruptedException("User returned to init execution.");
        }
    }

    /**
     * Method Notification.
     * Tells the user when the next task will be.
     * @param message - name task
     */
    public void Notification(String message){
        System.out.println("Now time to " + message);
    }
}
