package ua.edu.sumdu.j2se.Koval.tasks;


import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;

import java.io.IOException;

/**
 * Class UI.
 * @author Aleksei Koval
 */
public class UI {
    /**
     * The method is responsible for the life cycle of the program.
     * The relationship between the user and the model.
     */
    public static void app() throws InterruptedException, IOException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.configureLogging();
        controller.createEmptyList();
        boolean life = true;
        System.out.println("Welcome to task manager!\n");
        controller.readOnDb();
        while (life){
            life = controller.lifeCycle();
        }
    }
}
