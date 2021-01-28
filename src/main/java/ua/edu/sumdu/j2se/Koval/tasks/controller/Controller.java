package ua.edu.sumdu.j2se.Koval.tasks.controller;

import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;

import static ua.edu.sumdu.j2se.Koval.tasks.model.Model.*;

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
    public void caseChangeTask() throws InterruptedException {
        int idSelectTest = view.scanInt();
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
    public void caseRemoveTask() throws InputMismatchException, InterruptedException {
        removeTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to add the task to the model.
     * @throws InterruptedException
     */
    public void caseAddTask() throws InterruptedException {
        model.addTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to create an empty initial task list.
     */
    public void createEmptyList(){
        Model.createEmptyList();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command and configuring the program logging.
     */
    public void configureLogging(){
        Model.configureLogging();
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and reading a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readOnDb() throws InterruptedException, IOException, ClassNotFoundException {
        Model.readOnDb(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and writing a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadInDb() throws InterruptedException, IOException, ClassNotFoundException {
        Model.loadInDb();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to sort tasks by condition from and to.
     * @throws InterruptedException
     */
    public void caseFilterTask() throws InterruptedException {
        Model.caseFilterTask(view);
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to exit the program.
     */
    public static void exit(){
        Model.exit();
    }

    /**
     * The caseRemoveTask method is responsible for passing the error message that occurred in the program.
     * @param message Error message like string
     */
    public static void getMessageException(String message){
        Model.getMessageException(message);
    }

    public int scanAction() throws InterruptedException {
        return view.scanAction();
    }

    public boolean lifeCycle(){
        try {
            System.out.println("Add - 1\nShow - 2\nRemove - 3\nChange task - 4\nSave - 5\nLoad - 6\nFilter task by time - 7\nAlarm - 8\nExit - 9.\nYou can write 'quit' at anytime, when you want back to main menu.");
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
                case 6:
                    readOnDb();
                    return true;
                case 5:
                    loadInDb();
                    return true;
                case 7:
                    caseFilterTask();
                    return true;
                case 8:
                    view.caseNextTimeTask(view);
                    return true;
                case 9:
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
}
