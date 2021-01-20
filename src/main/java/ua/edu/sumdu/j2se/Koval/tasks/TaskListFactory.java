package ua.edu.sumdu.j2se.Koval.tasks;

/**
 * Class TaskListFactor.
 * @author Aleksei Koval
 */
public class TaskListFactory {
    /**
     * This method, according to the type parameter, must return an object of class ArrayTaskList or LinkedTaskList
     * @param type array
     * @return empty list ArrayTaskList or LinkedTaskList.
     */
    public static AbstractTaskList createTaskList(ListTypes.types type){
        switch (type){
            case ARRAY:
                return new ArrayTaskList();
            case LINKED:
                return new LinkedTaskList();
        }
        return null;
    }
}
