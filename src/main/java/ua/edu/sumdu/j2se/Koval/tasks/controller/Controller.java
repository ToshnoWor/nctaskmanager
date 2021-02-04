package ua.edu.sumdu.j2se.Koval.tasks.controller;

import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class. On the other hand, the user changes the model.
 * @author Aleksei Koval
 */
public class Controller {

    public View view;
    public Model model;
    /**
     *  Map for a life cycle
     */
    private Map<Integer, SelectActionInLifeCycle> map;

    /**
     * Constructor Controller.
     * Creates an instance of the model, view and map.
     */
    public Controller(){
        view = new View();
        model = new Model();
        CreateMap();
    }

    /**
     * Method CreateMap.
     * Creates a map for a life cycle.
     */
    private void CreateMap(){
        map = new HashMap<>();
        map.put(1, new AddTask());
        map.put(2, new ShowTask());
        map.put(3, new RemoveTask());
        map.put(4, new ChangeTask());
        map.put(5, new GetNextTasks());
        map.put(6, new Exit());
    }
    /**
     * The createEmptyList method is responsible for passing the command to create an empty initial task list.
     */
    public void createEmptyList(){
        model.createEmptyList();
    }

    /**
     * The configureLogging method is responsible for passing the command and configuring the program logging.
     */
    public void configureLogging(){
        model.configureLogging();
    }

    /**
     * The readOnDb method is responsible for passing a command and reading a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readOnDb() throws InterruptedException, IOException, ClassNotFoundException {
        model.readOnDb(view);
    }

    /**
     * The loadInDb method is responsible for passing a command and writing a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */

    /**
     * The getMessageException method is responsible for passing the error message that occurred in the program.
     * @param message Error message like string
     */
    public void getMessageException(String message){
        model.getMessageException(message);
    }

    /**
     * Scan some int - code action
     * @return some int
     * @throws InterruptedException
     */
    public int scanAction() throws InterruptedException {
        return view.scanAction();
    }

    /**
     * Life cycle program
     * return continue or not
     * @return true or false
     */
    public boolean lifeCycle(){
        try {
            System.out.println("Add - 1\nShow - 2\nRemove - 3\nChange task - 4\nFilter task by time - 5\nExit - 6.\nYou can write 'quit' at anytime, when you want back to main menu.");
            int action = scanAction();
            if (map.get(action)!=null)
                map.get(action).doSomeThing(model, view);
        } catch (IOException | InterruptedException e) {
            getMessageException(e.getMessage());
            return true;
        }
        return true;
    }

    /**
     * Method Notification.
     * Launches reminder methods.
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws CloneNotSupportedException
     * @throws IllegalAccessException
     */
    public void Notification() throws InvocationTargetException, NoSuchMethodException, InstantiationException, CloneNotSupportedException, IllegalAccessException {
        if (Controller.listIsEmpty())
            return;
        String message = model.Notification();
        if (message.equals("Not found"))
            return;
        view.Notification(message);
    }

    /**
     * Check isEmpty list in model
     * @return true or false
     */
    public static boolean listIsEmpty() {
        return Model.listIsEmpty();
    }

    /**
     * Show task list in model
     */
    public static void showTasks() {
        Model.showTasks();
    }
}