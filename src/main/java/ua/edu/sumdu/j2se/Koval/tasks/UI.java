package ua.edu.sumdu.j2se.Koval.tasks;


import org.apache.log4j.*;
import ua.edu.sumdu.j2se.Koval.tasks.controller.Controller;
import ua.edu.sumdu.j2se.Koval.tasks.modules.Notification;

import java.io.IOException;

/**
 * Class UI.
 * @author Aleksei Koval
 */
public class UI {

    static private final Logger logger = Logger.getLogger(UI.class);

    /**
     * The method is responsible for the life cycle of the program.
     * The relationship between the user and the model.
     */
    public static void app() throws InterruptedException, IOException, ClassNotFoundException {
        Controller controller = new Controller();
        configureLogging();
        controller.createEmptyList();
        boolean life = true;
        System.out.println("Welcome to task manager!\n");
        controller.readOnDb();
        new Notification(controller).start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            logger.info("Error: ", e);
        }
        while (life){
            life = controller.lifeCycle();
        }
    }

    /**
     * Method configureLogging.
     * Configures the login.
     */
    public static void configureLogging() {
        // creates pattern layout
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
        layout.setConversionPattern(conversionPattern);

        // creates console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();

        // creates file appender
        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile("logs.txt");
        fileAppender.setLayout(layout);
        fileAppender.activateOptions();

        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }
}
