package ua.edu.sumdu.j2se.Koval.tasks.controller;

import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SelectActionInLifeCycle {
    void doSomeThing(Model model, View view) throws IOException, InterruptedException;
}

class AddTask implements SelectActionInLifeCycle{
    @Override
    public void doSomeThing(Model model, View view) throws IOException, InterruptedException {
        model.addTask(view);
    }
}

class ShowTask implements SelectActionInLifeCycle{
    @Override
    public void doSomeThing(Model model, View view) {
        view.caseShowTasks();
    }
}

class RemoveTask implements SelectActionInLifeCycle {
    @Override
    public void doSomeThing(Model model, View view) throws IOException, InterruptedException {
        model.removeTask(view);
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
    public void doSomeThing(Model model, View view) throws IOException, InterruptedException {
        System.out.println("Enter id task");
        int idSelectTest = view.scanInt();
        System.out.println("Enter change action: 1 - title; 2 - time; 3 - start, end,interval; 4 - active.");
        int actionChange = view.scanAction();
        map.get(actionChange).doSomeThing(model, view, idSelectTest);
    }
}

class GetNextTasks implements SelectActionInLifeCycle {
    @Override
    public void doSomeThing(Model model, View view) throws InterruptedException {
        model.caseFilterTask(view);
    }
}

class Exit implements SelectActionInLifeCycle {
    @Override
    public void doSomeThing(Model model, View view) {
        model.exit();
    }
}