package ua.edu.sumdu.j2se.Koval.tasks.model;

import ua.edu.sumdu.j2se.Koval.tasks.view.View;

public interface SelectScanTime {
    void doSomeThing(int id, View view, LinkedTaskList list) throws InterruptedException;
}

class Start implements SelectScanTime{

    @Override
    public void doSomeThing(int id, View view, LinkedTaskList list ) throws InterruptedException {
        list.getTask(id).setTime(view.getTime("'start'"), list.getTask(id).getEndTime(), list.getTask(id).getRepeatInterval());
    }
}

class End implements SelectScanTime{
    @Override
    public void doSomeThing(int id, View view, LinkedTaskList list) throws InterruptedException {
        list.getTask(id).setTime(list.getTask(id).getStartTime(), view.getTime("'end'"), list.getTask(id).getRepeatInterval());
    }
}

class Interval implements SelectScanTime{

    @Override
    public void doSomeThing(int id, View view, LinkedTaskList list) throws InterruptedException {
        list.getTask(id).setTime(list.getTask(id).getStartTime(), list.getTask(id).getEndTime(), view.getInterval());
    }
}

class FullChange implements SelectScanTime{

    @Override
    public void doSomeThing(int id, View view, LinkedTaskList list) throws InterruptedException {
        list.getTask(id).setTime(view.getTime("'start'"), view.getTime("'end'"), view.getInterval());
    }
}