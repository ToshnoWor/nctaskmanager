package ua.edu.sumdu.j2se.Koval.tasks;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Class Tasks
 * @author Aleskei Koval
 */
public class Tasks {
    /**
     * Method incoming.
     * Saves tasks to the collection.
     * @param tasks to save
     * @param start filter from
     * @param end filter to
     * @return list tasks
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        ArrayTaskList arrayTaskList = new ArrayTaskList();

        for (Task task : tasks) {
            if (task.getStartTime().isAfter(start) && (task.getEndTime().isBefore(end) || task.getEndTime().isEqual(end)) && task.isActive()) {
                arrayTaskList.add(task);
            }
            else {
                if (task.isRepeated() && task.isActive()) {
                    LocalDateTime prevTime = task.getStartTime();
                    for (LocalDateTime i = task.getStartTime(); prevTime.isBefore(task.getEndTime()) || prevTime.isEqual(task.getEndTime()); i = i.plusSeconds(task.getRepeatInterval())) {
                        if(((prevTime.isAfter(start) && prevTime.isBefore(end)) || (prevTime.isEqual(end)) ) && i.isAfter(start)) {
                            arrayTaskList.add(task);
                            break;
                        }
                        prevTime = i;
                    }
                }
            }

        }

        return arrayTaskList;
    }

    /**
     * Method calendar.
     * Build a calendar of tasks for a given period - a table where each date corresponds
     * to a set of tasks to be performed at this time, and one task can occur according to several dates,
     * if it must be performed several times during the specified period.
     * @param tasks list tasks
     * @param start filter from
     * @param end filter to
     * @return calendar
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();

        for (Task task : tasks) {
            if (task.isRepeated() && task.isActive()) {
                LocalDateTime prevTime = task.getStartTime();
                for (LocalDateTime i = task.getStartTime();
                     prevTime.isBefore(task.getEndTime()) || prevTime.isEqual(task.getEndTime());
                     i = i.plusSeconds(task.getRepeatInterval())
                ) {
                    if ((prevTime.isAfter(start) && prevTime.isBefore(end)) || prevTime.isEqual(end)) {
                        if (map.containsKey(prevTime)) {
                            Set<Task> initSet = map.get(prevTime);
                            initSet.add(task);
                            map.put(prevTime, initSet);
                        }
                        else {
                            Set<Task> initSet = new HashSet<>(Collections.singletonList(task));
                            map.put(prevTime, initSet);
                        }
                    }
                    prevTime = i;
                }
            }
            else {
                if (task.isActive()) {
                    if (map.containsKey(task.getStartTime())) {
                        Set<Task> initSet = new HashSet<>();
                        initSet.add(task);
                        map.put(task.getTime(), initSet);
                    }
                    else {
                        Set<Task> initSet = map.get(task.getStartTime());
                        initSet.add(task);
                        map.put(task.getStartTime(), initSet);
                    }
                }
            }
        }

        return map;
    }
}