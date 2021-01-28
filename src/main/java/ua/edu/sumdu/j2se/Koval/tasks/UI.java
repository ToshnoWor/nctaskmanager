package ua.edu.sumdu.j2se.Koval.tasks;


import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;

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
        Controller controller = new Controller();
        controller.configureLogging();
        controller.createEmptyList();
        boolean life = true;
        System.out.println("Welcome to task manager!\n");
        while (life){
            life = controller.lifeCycle();
        }
    }
}
