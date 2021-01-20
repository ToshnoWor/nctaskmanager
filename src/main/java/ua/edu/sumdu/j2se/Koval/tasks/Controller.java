package ua.edu.sumdu.j2se.Koval.tasks;

import java.io.IOException;
import java.util.InputMismatchException;

import static ua.edu.sumdu.j2se.Koval.tasks.Model.changeTitle;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.changeTime;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.changeStartEndInterval;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.changeActive;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.addTask;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.removeTask;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.scanId;
import static ua.edu.sumdu.j2se.Koval.tasks.Model.scanAction;

/**
 * Controller class. On the other hand, the user changes the model.
 * @author Aleksei Koval
 */
public class Controller {

    /*public static void changeTask(int action, int idTask) throws InterruptedException {
        switch (action){
            case 1:
                System.out.println("Enter your new title");
                changeTitle(idTask);
                break;
            case 2:
                changeTime(idTask);
                break;
            case 3:
                changeStartEndInterval(idTask);
                break;
            case 4:
                System.out.println("Enter true if you want to activate the task, and false otherwise.");
                changeActive(idTask);
                break;
            default:
                System.out.println("Error select");

        }
    }*/

    /**
     * The caseChangeTask method is responsible for modifying an existing task.
     * @throws InterruptedException
     */
    public static void caseChangeTask() throws InterruptedException {
        int idSelectTest = scanId();
        int actionChange = scanAction();
        switch (actionChange){
            case 1:
                System.out.println("Enter your new title");
                changeTitle(idSelectTest);
                break;
            case 2:
                changeTime(idSelectTest);
                break;
            case 3:
                changeStartEndInterval(idSelectTest);
                break;
            case 4:
                System.out.println("Enter true if you want to activate the task, and false otherwise.");
                changeActive(idSelectTest);
                break;
            default:
                System.out.println("Error select");

        }
        /*changeTask(actionChange,idSelectTest);*/
    }

    /**
     * The caseRemoveTask method is responsible for passing the command and removing the task to the model.
     * @throws InputMismatchException
     * @throws InterruptedException
     */
    public static void caseRemoveTask() throws InputMismatchException, InterruptedException {
        removeTask();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to add the task to the model.
     * @throws InterruptedException
     */
    public static void caseAddTask() throws InterruptedException {
        addTask();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to create an empty initial task list.
     */
    public static void createEmptyList(){
        Model.createEmptyList();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command and configuring the program logging.
     */
    public static void configureLogging(){
        Model.configureLogging();
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and reading a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void readOnDb() throws InterruptedException, IOException, ClassNotFoundException {
        Model.readOnDb();
    }

    /**
     * The caseRemoveTask method is responsible for passing a command and writing a list of tasks from a file.
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void loadInDb() throws InterruptedException, IOException, ClassNotFoundException {
        Model.loadInDb();
    }

    /**
     * The caseRemoveTask method is responsible for passing the command to sort tasks by condition from and to.
     * @throws InterruptedException
     */
    public static void caseFilterTask() throws InterruptedException {
        Model.caseFilterTask();
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
}
