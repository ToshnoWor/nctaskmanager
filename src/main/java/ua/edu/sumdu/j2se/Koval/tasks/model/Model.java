package ua.edu.sumdu.j2se.Koval.tasks.model;

import org.apache.log4j.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static ua.edu.sumdu.j2se.Koval.tasks.view.View.*;

/**
 * Class Model
 * Responsible for manipulating the model.
 * @author Aleksei Koval
 */
public class Model {
    /**
     * Contains the current list of tasks in the program.
     */
    static private LinkedTaskList list;
    /**
     * Program logger.
     */
    static Logger logger = Logger.getLogger(Model.class);

    /**
     * Method listIsEmpty.
     * @return Is the task list empty.
     */
    static public boolean listIsEmpty(){
        return list.size()<1;
    }

    /**
     * Method showTasks.
     * Displays all tasks on the user's screen.
     */
    static public void showTasks(){
        if (!listIsEmpty())
            System.out.println(list.toString());
        else
            System.out.println("Empty!");
    }

    /**
     * Method configureLogging.
     * Configures the login.
     */
    public static void configureLogging() {
        // creates pattern layout
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
        layout.setConversionPattern(conversionPattern);

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();

        // creates file appender
        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile("logs.txt");
        fileAppender.setLayout(layout);
        fileAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }

    /**
     * Method readOnDb.
     * Reads a task sheet from a file.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void readOnDb() throws IOException, ClassNotFoundException, InterruptedException {

        if (list.size()==0) {
            FileInputStream fileIn = new FileInputStream("db.txt");
            ObjectInputStream inFile = new ObjectInputStream(fileIn);
            list = (LinkedTaskList) inFile.readObject();
        } else {
            System.out.println("List not empty, replace(true) or add new tasks(false)");
            if (scanBoolean()){
                FileInputStream fileIn = new FileInputStream("db.txt");
                ObjectInputStream inFile = new ObjectInputStream(fileIn);
                list = (LinkedTaskList) inFile.readObject();
            } else {
                LinkedTaskList newList;
                FileInputStream fileIn = new FileInputStream("db.txt");
                ObjectInputStream inFile = new ObjectInputStream(fileIn);
                newList = (LinkedTaskList) inFile.readObject();
                for (int i = 0; i < newList.size(); i++) {
                    if (!haveCopyTask(newList.getTask(i))) {
                        list.add(newList.getTask(i));
                    }
                }
            }
        }
    }

    /**
     * Method loadInDb.
     * Writes a list of tasks from a file.
     * @throws IOException
     */
    public static void loadInDb() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("db.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(list);
        logger.info("Tasks was successfully saved to database.");
    }

    /*public static String tIb(String text){
        return "\033[0;1m" + text + "\033[0m";
    }*/

    /**
     * Method haveCopyTask.
     * Searches for a match of tasks in the list and returns whether there is a copy.
     * @param task The task we are looking for in the list.
     * @return If the task is on the list.
     */
    public static boolean haveCopyTask(Task task){
        for (int i = 0; i < list.size(); i++) {
            if (list.getTask(i).equals(task))
                return true;
        }
        return false;
    }

    /**
     * Method caseFilterTask.
     * Filters the list of tasks inside and out. Draws them to the screen.
     * @throws InterruptedException
     */
    public static void caseFilterTask() throws InterruptedException {
        Iterable<Task> res = Tasks.incoming(list, getTime("start"), getTime("end"));
        res.forEach(task -> System.out.println(task.toString()));
    }

    /**
     * Method createEmptyList.
     * Creates an empty to-do list.
     */
    public static void createEmptyList(){
        list = new LinkedTaskList();
    }

    /**
     * Method changeTitle.
     * Changes the title of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public static void changeTitle(int id) throws InterruptedException {
        list.getTask(id).setTitle(scanString());
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
    }

    /**
     * Method changeTime.
     * Changes the time of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public static void changeTime(int id) throws InterruptedException {
        list.getTask(id).setTime(getTime("time"));
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
    }

    /**
     * Method changeStartEndInterval.
     * Changes the start or end ot interval of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public static void changeStartEndInterval(int id) throws InterruptedException {
        if (list.getTask(id).isRepeated()){
            switcherChangeSEI(id);
        } else {
            System.out.println("Task is not repeated.Continuous?(true or false)");
            if (scanBoolean())
                switcherChangeSEI(id);
        }
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
    }

    /**
     * Method switcherChangeSEI.
     * Selection of action execution.
     * @param id id task.
     * @throws InterruptedException
     */
    private static void switcherChangeSEI(int id) throws InterruptedException {
        System.out.println("Change start - 1, change end - 2, change interval - 3, change full - 4.");
        switch (scanInt()){
            case 1:
                list.getTask(id).setTime(getTime("'start'"), list.getTask(id).getEndTime(), list.getTask(id).getRepeatInterval());
                break;
            case 2:
                list.getTask(id).setTime(list.getTask(id).getStartTime(), getTime("'end'"), list.getTask(id).getRepeatInterval());
                break;
            case 3:
                list.getTask(id).setTime(list.getTask(id).getStartTime(), list.getTask(id).getEndTime(), getInterval());
                break;
            case 4:
                list.getTask(id).setTime(getTime("'start'"), getTime("'end'"), getInterval());
                break;
            default:
                System.out.println("Error select");
        }
    }

    /**
     * Method changeActive.
     * Changes the active of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public static void changeActive(int id) throws InterruptedException {
        list.getTask(id).setActive(scanBoolean());
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
    }

    /**
     * Method scanId.
     * Scans the task ID.
     * @return task ID.
     * @throws InterruptedException
     */
    public static int scanId() throws InterruptedException {
        System.out.println("Input id task");
        return scanInt();
    }

    /**
     * Method scanAction.
     * Scans the task action.
     * @return task action.
     * @throws InterruptedException
     */
    public static int scanAction() throws InterruptedException {
        System.out.println("Change title - 1\nChange time - 2\nChange start, end and interval - 3\nChange active - 4");
        return scanInt();
    }

    /**
     * Method removeTask.
     * Removes the required task from the list.
     * @throws InterruptedException
     */
    public static void removeTask() throws InterruptedException {
        System.out.println("Enter id.");
        int id = scanInt();
        String title = "";
        if (list.size()!=0 && list.getTask(id)!=null) {
            title = list.getTask(id).getTitle();
            list.remove(list.getTask(id));
        }
        else {
            System.out.println("You are mistaken.");
        }
        logger.info("Task with title " + title + " was deleted.");
    }

    /**
     * Method addTask.
     * Adds a task to the list.
     * @throws InterruptedException
     */
    public static void addTask() throws InterruptedException {

        Task task = readTask();
        task.setActive(true);
        if (haveCopyTask(task))
            System.out.println("List have copy task");
        else
            list.add(task);
        logger.info("Task with title " + task.getTitle() + " was added.");
    }

    /**
     * Method readTask.
     * Reads data about a task and creates an instance of it.
     * @return A finished copy of the task.
     * @throws InterruptedException
     */
    public static Task readTask() throws InterruptedException {
        Task task;
        System.out.println("Enter 'title'.");
        String title = scanString();
        System.out.println("Enter repeated 'true' or 'false'.");
        boolean repeated = scanBoolean();
        if (repeated){
            System.out.println("Enter 'interval'.");
            int interval = scanInt();
            LocalDateTime start = getTime("'start'");
            LocalDateTime end = getTime("'end'");
            task = new Task(title,start,end,interval);
        }
        else {
            LocalDateTime time = getTime("'time'");
            task = new Task(title,time);
        }
        return task;
    }

    /**
     * Method NextTimeTask.
     * Searches the list for the next task to execute.
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws CloneNotSupportedException
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    public static void NextTimeTask() throws NoSuchMethodException, InstantiationException, IllegalAccessException, CloneNotSupportedException, InvocationTargetException, InterruptedException {
        if (getNextTimeTask(list)!=null) {
            System.out.println("Enter time duration - true or full time to time Task - false.");
            boolean timeSwitch = scanBoolean();
            if (timeSwitch) {
                System.out.println("Enter time alarm\nEnter hours:");
                int hoursAlarm = scanInt();
                System.out.println("Enter minutes");
                int minutesAlarm = scanInt();
                LocalTime alarmTime = LocalTime.of(hoursAlarm, minutesAlarm, 1);
                LocalTime now = LocalTime.of(0, 0);
                while (now.isBefore(alarmTime)) {
                    getNextTimeTask(list);
                    TimeUnit.SECONDS.sleep(1);
                    now = now.plusSeconds(1);
                }
            } else {
                LocalTime timeToTask = Objects.requireNonNull(getNextTimeTask(list)).toLocalTime();
                LocalTime now = LocalTime.of(0, 0);
                while (now.isBefore(timeToTask)) {
                    getNextTimeTask(list);
                    TimeUnit.SECONDS.sleep(1);
                    now = now.plusSeconds(1);
                }
            }
        }
    }

    /**
     * Method NextTimeTask.
     * Looking for tasks to be completed in the future.
     * @param list tasks after sorting.
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws CloneNotSupportedException
     * @throws IllegalAccessException
     */
    private static LocalDateTime getNextTimeTask(LinkedTaskList list) throws InvocationTargetException, NoSuchMethodException, InstantiationException, CloneNotSupportedException, IllegalAccessException {
        LocalDateTime max = LocalDateTime.of(2000,1,1,0,0);
        for (int i = 0; i < list.size(); i++) {
            if (list.getTask(i).isRepeated()) {
                if (list.getTask(i).getEndTime().isAfter(max)) {
                    max = list.getTask(i).getEndTime();
                }
            }
            else{
                if (list.getTask(i).getTime().isAfter(max)) {
                    max = list.getTask(i).getEndTime();
                }
            }
        }
        LinkedTaskList filtered = (LinkedTaskList) list.incoming(LocalDateTime.now(),max);
        if (filtered.size()>0){
            ArrayTaskList arrayTaskList = new ArrayTaskList();
            LocalDateTime min = LocalDateTime.of(9999,1,1,0,0);
            int minId = 0;
            for (int i = 0; i < filtered.size(); i++) {
                arrayTaskList.add(filtered.getTask(i));
            }
            for (int i = 0; i < arrayTaskList.size(); i++) {
                assert min != null;
                if (Objects.requireNonNull(arrayTaskList.getTask(i).nextTimeAfter(LocalDateTime.now())).isBefore(min)){
                    min = arrayTaskList.getTask(i).nextTimeAfter(LocalDateTime.now());
                    minId = i;
                }
            }
            Duration rez = Duration.between(LocalDateTime.now(),min);
            long absSeconds = Math.abs(rez.getSeconds());
            System.out.println("Task:" + arrayTaskList.getTask(minId).getTitle() + "\tTime until: " + "Hours: " + (absSeconds / 3600) +
                    " minutes: " + ((absSeconds % 3600) / 60) +
                    " seconds: " + (absSeconds % 60));
            return min;
        }
        System.out.println("Next task not found!");
        return null;
    }

    /**
     * Method exit.
     * Closes the application and sends the last log.
     */
    public static void exit(){
        logger.info("App execution was ended by user.");
    }

    /**
     * Writes a message to the log file.
     * @param message message log.
     */
    public static void getMessageException(String message) {
        logger.info(message);
    }
}
