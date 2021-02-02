package ua.edu.sumdu.j2se.Koval.tasks.modules;

import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;

import java.lang.reflect.InvocationTargetException;

public class Notification extends Thread {
    Controller controller;

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
                e.printStackTrace();
            }

            try {
                sleep(1000);
            }
            catch (InterruptedException e){
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
}
