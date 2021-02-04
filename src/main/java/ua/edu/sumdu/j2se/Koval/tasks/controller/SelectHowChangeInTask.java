package ua.edu.sumdu.j2se.Koval.tasks.controller;

import ua.edu.sumdu.j2se.Koval.tasks.model.Model;
import ua.edu.sumdu.j2se.Koval.tasks.view.View;

import java.io.IOException;

public interface SelectHowChangeInTask {
    void doSomeThing(Model model, View view, int id) throws IOException, InterruptedException;
}

class Title implements SelectHowChangeInTask {

    @Override
    public void doSomeThing(Model model, View view, int id) throws IOException, InterruptedException {
        System.out.println("Enter your new title");
        model.changeTitle(id, view);
    }
}

class Time implements SelectHowChangeInTask {

    @Override
    public void doSomeThing(Model model, View view, int id) throws IOException, InterruptedException {
        model.changeTime(id, view);
    }
}

class TimeRepeatedTask implements SelectHowChangeInTask {

    @Override
    public void doSomeThing(Model model, View view, int id) throws IOException, InterruptedException {
        model.changeStartEndInterval(id, view);
    }
}

class Active implements SelectHowChangeInTask {

    @Override
    public void doSomeThing(Model model, View view, int id) throws IOException, InterruptedException {
        System.out.println("Enter true if you want to activate the task, and false otherwise.");
        model.changeActive(id, view);
    }
}