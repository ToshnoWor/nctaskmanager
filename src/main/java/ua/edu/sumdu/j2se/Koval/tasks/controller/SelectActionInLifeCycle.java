package ua.edu.sumdu.j2se.Koval.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SelectActionInLifeCycle {
    boolean doSomeThing(Model model, View view, Logger logger) throws IOException, InterruptedException;
}

class AddTask implements SelectActionInLifeCycle{
    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) throws IOException, InterruptedException {
        model.addTask(view);
        return true;
    }
}

class ShowTask implements SelectActionInLifeCycle{
    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) {
        view.caseShowTasks();
        return true;
    }
}

class RemoveTask implements SelectActionInLifeCycle {
    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) throws IOException, InterruptedException {
        model.removeTask(view);
        return true;
    }
}

class ChangeTask implements SelectActionInLifeCycle {
    Map<Integer, SelectHowChangeInTask> map;

    ChangeTask(){
        createMap();
    }

    private void createMap(){
        map = new HashMap<>();
        map.put(1, new Title());
        map.put(2, new Time());
        map.put(3, new TimeRepeatedTask());
        map.put(4, new Active());
    }

    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) throws IOException, InterruptedException {
        System.out.println("Enter id task");
        int idSelectTest = view.scanInt();
        System.out.println("Enter change action: 1 - title; 2 - time; 3 - start, end,interval; 4 - active.");
        int actionChange = view.scanAction();
        if (map.get(actionChange)!=null)
            map.get(actionChange).doSomeThing(model, view, idSelectTest);
        return true;
    }
}

class GetNextTasks implements SelectActionInLifeCycle {
    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) throws InterruptedException {
        model.caseFilterTask(view);
        return true;
    }
}

class Exit implements SelectActionInLifeCycle {
    @Override
    public boolean doSomeThing(Model model, View view, Logger logger) {
        logger.info("App execution was ended by user.");
        return false;
    }
}