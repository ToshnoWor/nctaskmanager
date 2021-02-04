package ua.edu.sumdu.j2se.Koval.tasks.modules;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;

import java.lang.reflect.InvocationTargetException;

public class Notification extends Thread {
    Controller controller;

    static private final Logger logger = Logger.getLogger(Notification.class);

    public Notification(Controller controller){
        setDaemon(true);
        this.controller = controller;
    }

    /**
     * Method run.
     * Reminder action.
     */
    public void run(){

        while (true) {

            try {
                this.controller.Notification();
            } catch (InvocationTargetException | IllegalAccessException | CloneNotSupportedException | InstantiationException | NoSuchMethodException e) {
                logger.info("Error: ",e);
            }

            try {
                sleep(1000);
            }
            catch (InterruptedException e){
                logger.info("Error: ",e);
            }
        }
    }
}
