package ua.edu.sumdu.j2se.Koval.tasks.controller;

import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;

/**
 * Controller class. On the other hand, the user changes the model.
 * @author Aleksei Koval
 */
public class Controller {

    View view;
    Model model;

    public Controller(){
        view = new View();
        model = new Model();
    }

    /**
     * The caseChangeTask method is responsible for modifying an existing task.
     * @throws InterruptedException
     */
    public void caseChangeTask() throws InterruptedException, IOException {
        System.out.println("Enter id task");
        int idSelectTest = view.scanInt();
        System.out.println("Enter change action: 1 - title; 2 - time; 3 - start, end,interval; 4 - active.");
        int actionChange = view.scanAction();
        switch (actionChange){
            case 1:
                System.out.println("Enter your new title");
                model.changeTitle(idSelectTest, view);
                break;
            case 2:
                model.changeTime(idSelectTest, view);
                break;
            case 3:
                model.changeStartEndInterval(idSelectTest, view);
                break;
            case 4:
                System.out.println("Enter true if you want to activate the task, and false otherwise.");
                model.changeActive(idSelectTest, view);
                break;
            default:
                System.out.println("Error select");
        }
    }

    /**
     * The caseRemoveTask method is responsible for passing the command and removing the task to the model.
     * @throws InputMismatchException
     * @throws InterruptedException
     */
    public void caseRemoveTask() throws InputMismatchException, InterruptedException, IOException {
        model.removeTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to add the task to the model.
     * @throws InterruptedException
     */
    public void caseAddTask() throws InterruptedException, IOException, ClassNotFoundException {
        model.addTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to create an empty initial task list.
     */
    public void createEmptyList(){
        model.createEmptyList();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command and configuring the program logging.
     */
    public void configureLogging(){
        model.configureLogging();
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and reading a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readOnDb() throws InterruptedException, IOException, ClassNotFoundException {
        model.readOnDb(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and writing a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadInDb() throws InterruptedException, IOException, ClassNotFoundException {
        model.loadInDb();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to sort tasks by condition from and to.
     * @throws InterruptedException
     */
    public void caseFilterTask() throws InterruptedException {
        model.caseFilterTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to exit the program.
     */
    public void exit(){
        model.exit();
    }

    /**
     * The caseRemoveTask method is responsible for passing the error message that occurred in the program.
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
            System.out.println("Add - 1\nShow - 2\nRemove - 3\nChange task - 4\nFilter task by time - 5\nAlarm - 6\nExit - 7.\nYou can write 'quit' at anytime, when you want back to main menu.");
            int action = scanAction();
            switch (action){
                case 1:
                    caseAddTask();
                    return true;
                case 2:
                    view.caseShowTasks();
                    return true;
                case 3:
                    caseRemoveTask();
                    return true;
                case 4:
                    caseChangeTask();
                    return true;
                case 5:
                    caseFilterTask();
                    return true;
                case 6:
                    view.caseNextTimeTask(view);
                    return true;
                case 7:
                    exit();
                    return false;
            }
        } catch (IOException | CloneNotSupportedException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InterruptedException e) {
            getMessageException(e.getMessage());
            System.out.println(e.getMessage());
            return true;
        }
        return true;
    }

    /**
     * Print next time task in list
     * @param view - scanner
     * @throws NoSuchMethodException
     * @throws InterruptedException
     * @throws InstantiationException
     * @throws CloneNotSupportedException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void NextTimeTask(View view) throws NoSuchMethodException, InterruptedException, InstantiationException, CloneNotSupportedException, IllegalAccessException, InvocationTargetException {
        Model.NextTimeTask(view);
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
