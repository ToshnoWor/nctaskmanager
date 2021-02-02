package ua.edu.sumdu.j2se.Koval.tasks;


import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;
import ua.edu.sumdu.j2se.Koval.tasks.modules.Notification;

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
        new Notification(controller).start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // handle here exception
        }
        while (life){
            life = controller.lifeCycle();
        }
    }
}
