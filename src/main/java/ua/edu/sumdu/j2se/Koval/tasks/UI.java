package ua.edu.sumdu.j2se.Koval.tasks;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import static ua.edu.sumdu.j2se.Koval.tasks.View.*;
import static ua.edu.sumdu.j2se.Koval.tasks.Controller.*;

/**
 * Class UI.
 * @author Aleksei Koval
 */
public class UI {
    /**
     * The method is responsible for the life cycle of the program.
     * The relationship between the user and the model.
     */
    public static void app(){
        configureLogging();
        Scanner in = new Scanner(System.in);
        createEmptyList();
        boolean life = true;
        System.out.println("Welcome\n");
        while (life){
            try {
                System.out.println("Add - 1\nShow - 2\nRemove - 3\nChange task - 4\nSave - 5\nLoad - 6\nFilter task by time - 7\nAlarm - 8\nExit - 9.\nYou can write 'quit' at anytime, when you want back to main menu.");
                int action = scanInt();
                switch (action){
                    case 1:
                        caseAddTask();
                        break;
                    case 2:
                        caseShowTasks();
                        break;
                    case 3:
                        caseRemoveTask();
                        break;
                    case 4:
                        caseChangeTask();
                        break;
                    case 6:
                        readOnDb();
                        break;
                    case 5:
                        loadInDb();
                        break;
                    case 7:
                        caseFilterTask();
                        break;
                    case 8:
                        caseNextTimeTask();
                        break;
                    case 9:
                        exit();
                        life = false;
                        in.close();
                        break;
                }
            } catch (IOException | CloneNotSupportedException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException | InterruptedException e) {
                getMessageException(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }
}
