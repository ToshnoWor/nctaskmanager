package ua.edu.sumdu.j2se.Koval.tasks.model;

import org.apache.log4j.*;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


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
    public void configureLogging() {
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
    public void readOnDb(View view) throws IOException, ClassNotFoundException, InterruptedException {
        if (list.size()==0) {
            FileInputStream fileIn = new FileInputStream("db.txt");
            ObjectInputStream inFile = new ObjectInputStream(fileIn);
            list = (LinkedTaskList) inFile.readObject();
        } else {
            System.out.println("List not empty, replace(true) or add new tasks(false)");
            if (view.scanBoolean()){
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
    public void loadInDb() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("db.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(list);
        logger.info("Tasks was successfully saved to database.");
    }

    /**
     * Method haveCopyTask.
     * Searches for a match of tasks in the list and returns whether there is a copy.
     * @param task The task we are looking for in the list.
     * @return If the task is on the list.
     */
    public boolean haveCopyTask(Task task){
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
    public void caseFilterTask(View view) throws InterruptedException {
        Iterable<Task> res = Tasks.incoming(list, view.getTime("start"), view.getTime("end"));
        res.forEach(task -> System.out.println(task.toString()));
    }

    /**
     * Method createEmptyList.
     * Creates an empty to-do list.
     */
    public void createEmptyList(){
        list = new LinkedTaskList();
    }

    /**
     * Method changeTitle.
     * Changes the title of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public void changeTitle(int id, View view) throws InterruptedException, IOException {
        list.getTask(id).setTitle(view.scanString());
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
        loadInDb();
    }

    /**
     * Method changeTime.
     * Changes the time of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public void changeTime(int id, View view) throws InterruptedException, IOException {
        list.getTask(id).setTime(view.getTime("time"));
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
        loadInDb();
    }

    /**
     * Method changeStartEndInterval.
     * Changes the start or end ot interval of the task with a id.
     * @param id id task.
     * @throws InterruptedException
     */
    public void changeStartEndInterval(int id, View view) throws InterruptedException, IOException {
        if (list.getTask(id).isRepeated()){
            switcherChangeSEI(id, view);
        } else {
            System.out.println("Task is not repeated.Continuous?(true or false)");
            if (view.scanBoolean())
                switcherChangeSEI(id, view);
        }
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
        loadInDb();
    }

    /**
     * Method switcherChangeSEI.
     * Selection of action execution.
     * @param id id task.
     * @throws InterruptedException
     */
    private void switcherChangeSEI(int id, View view) throws InterruptedException {
        System.out.println("Change start - 1, change end - 2, change interval - 3, change full - 4.");
        switch (view.scanInt()){
            case 1:
                list.getTask(id).setTime(view.getTime("'start'"), list.getTask(id).getEndTime(), list.getTask(id).getRepeatInterval());
                break;
            case 2:
                list.getTask(id).setTime(list.getTask(id).getStartTime(), view.getTime("'end'"), list.getTask(id).getRepeatInterval());
                break;
            case 3:
                list.getTask(id).setTime(list.getTask(id).getStartTime(), list.getTask(id).getEndTime(), view.getInterval());
                break;
            case 4:
                list.getTask(id).setTime(view.getTime("'start'"), view.getTime("'end'"), view.getInterval());
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
    public void changeActive(int id, View view) throws InterruptedException, IOException {
        list.getTask(id).setActive(view.scanBoolean());
        logger.info("Task with title " + list.getTask(id).getTitle() + " was changed.");
        loadInDb();
    }

    /**
     * Method removeTask.
     * Removes the required task from the list.
     * @throws InterruptedException
     */
    public void removeTask(View view) throws InterruptedException, IOException {
        System.out.println("Enter id.");
        int id = view.scanInt();
        String title = "";
        if (list.size()!=0 && list.getTask(id)!=null) {
            title = list.getTask(id).getTitle();
            list.remove(list.getTask(id));
        }
        else {
            System.out.println("You are mistaken.");
        }
        logger.info("Task with title " + title + " was deleted.");
        loadInDb();
    }

    /**
     * Method addTask.
     * Adds a task to the list.
     * @throws InterruptedException
     */
    public void addTask(View view) throws InterruptedException, IOException {
        Task task = readTask(view);
        task.setActive(true);
        if (haveCopyTask(task))
            System.out.println("List have copy task");
        else
            list.add(task);
        logger.info("Task with title " + task.getTitle() + " was added.");
        loadInDb();
    }

    /**
     * Method readTask.
     * Reads data about a task and creates an instance of it.
     * @return A finished copy of the task.
     * @throws InterruptedException
     */
    public Task readTask(View view) throws InterruptedException {
        Task task;
        System.out.println("Enter 'title'.");
        String title = view.scanString();
        System.out.println("Enter repeated 'true' or 'false'.");
        boolean repeated = view.scanBoolean();
        if (repeated){
            System.out.println("Enter 'interval'.");
            int interval = view.scanInt();
            LocalDateTime start = view.getTime("'start'");
            LocalDateTime end = view.getTime("'end'");
            task = new Task(title,start,end,interval);
        }
        else {
            LocalDateTime time = view.getTime("'time'");
            task = new Task(title,time);
        }
        return task;
    }

    /**
     * Method Notification.
     * Looks for a task that is scheduled for the near future, and if the time of this task is close enough to the current one, it notifies the user about it.
     * @return Task name.
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws CloneNotSupportedException
     * @throws InvocationTargetException
     */
    public String Notification() throws NoSuchMethodException, InstantiationException, IllegalAccessException, CloneNotSupportedException, InvocationTargetException {
        Task nextTask = getNextTaskToGo();
        LocalTime timeToTask = Objects.requireNonNull(nextTask.nextTimeAfter(LocalDateTime.now())).toLocalTime();
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(2);
        if (timeToTask.equals(now)){
            return nextTask.getTitle();
        }
        return "Not found";
    }

    /**
     * Method getNextTaskToGo.
     * Looks for a nearby scheduled task.
     * @return task
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws CloneNotSupportedException
     * @throws IllegalAccessException
     */
    public Task getNextTaskToGo() throws InvocationTargetException, NoSuchMethodException, InstantiationException, CloneNotSupportedException, IllegalAccessException {
        LinkedTaskList newList =  list;
        newList.incoming(LocalDateTime.now(),getLastTask().getEndTime());
        if (newList.size()>0){
            return getFirstInListToGo(newList);
        }
        return null;
    }

    /**
     * Looks for the task that is the first scheduled on the list.
     * @param list The list in which the comparison is made.
     * @return task
     */
    public Task getFirstInListToGo(LinkedTaskList list){
        LocalDateTime min = LocalDateTime.of(9999,1,1,0,0);
        LocalDateTime now = LocalDateTime.now();
        int id = -1;
        int size = list.size();
        for (int i = 0; i < size; i++){
            assert min != null;
            if (list.getTask(i).nextTimeAfter(now)!=null)
            if (Objects.requireNonNull(list.getTask(i).nextTimeAfter(now)).isBefore(min)) {
                min = list.getTask(i).nextTimeAfter(now);
                id = i;
            }
        }
        if (id!=-1)
            return list.getTask(id);
        return null;
    }

    /**
     * Looks for the latest scheduled task.
     * @return task
     */
    public Task getLastTask(){
        LocalDateTime max = LocalDateTime.of(2000,1,1,0,0);
        int id = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.getTask(i).isRepeated()) {
                if (list.getTask(i).getEndTime().isAfter(max)) {
                    max = list.getTask(i).getEndTime();
                    id = i;
                }
            }
            else{
                if (list.getTask(i).getTime().isAfter(max)) {
                    max = list.getTask(i).getEndTime();
                    id = i;
                }
            }
        }
        if (id!=-1)
            return list.getTask(id);
        return null;
    }


    /**
     * Method exit.
     * Closes the application and sends the last log.
     */
    public void exit(){
        logger.info("App execution was ended by user.");
    }

    /**
     * Writes a message to the log file.
     * @param message message log.
     */
    public void getMessageException(String message) {
        logger.info(message);
    }
}
