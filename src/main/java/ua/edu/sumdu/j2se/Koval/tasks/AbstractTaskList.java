package ua.edu.sumdu.j2se.Koval.tasks;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class AbstractTaskList.
 * @author Aleksei Koval
 */
public abstract class AbstractTaskList implements Cloneable, Serializable, Iterable<Task> {
    /**
     * Method add.
     * Add task to list.
     * @param task to add.
     */
    public abstract void add(Task task);

    /**
     * Method remove.
     * Remove task from list.
     * @param task to remove.
     * @return removal result.
     */
    public abstract boolean remove(Task task);

    /**
     * Method size.
     * Counts list size.
     * @return list size.
     */
    public abstract int size();

    /**
     * Method getTask.
     * Return task on index from list.
     * @param index task from list.
     * @return task on index.
     */
    public abstract Task getTask(int index);

    /**
     * Method getStream.
     * Transformation list to stream and returns stream.
     * @return stream.
     */
    public abstract Stream<Task> getStream();

    /**
     * Method incoming.
     * Returns filtered list from and to.
     * @param from Countdown start.
     * @param to End of countdown.
     * @return filtered list.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws CloneNotSupportedException
     */
    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, CloneNotSupportedException {

        AbstractTaskList list = getClass().getDeclaredConstructor().newInstance();
        this.getStream().filter(Objects::nonNull).filter(i -> {
            if (i.nextTimeAfter(from)!=null)
                return Objects.requireNonNull(i.nextTimeAfter(from)).isBefore(to) || Objects.requireNonNull(i.nextTimeAfter(from)).isEqual(to);
            return false;
        }).forEach(list::add);
        return list;

    }

    /**
     * Compares our object and the comer.
     * @param obj object of comparison.
     * @return <strong>True</strong> if they are equal, <strong>false</strong> if not equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj==this)
            return true;
        if (obj==null)
            return false;
        if (!(obj instanceof AbstractTaskList))
            return false;
        AbstractTaskList that = (AbstractTaskList) obj;
        int thisSize = size();
        int thatSize = that.size();
        if (thisSize == thatSize){
            if (thisSize == 0) {
                return true;
            }
            for (int i = 0; i < thisSize; i++) {
                if (!this.getTask(i).equals(that.getTask(i))){
                    return false;
                }
            }
            return true;
        }
        else return false;
    }

    /**
     * Method hashCode.
     * Creates a numeric item ID.
     * @return int
     */
    @Override
    public int hashCode() {
        int size = this.size();
        int hasCode = 1;
        for (int i = 0; i < size; i++) {
            hasCode = 31 * hasCode + (this.getTask(i) == null ? 0 : this.hashCode());
        }
        return hasCode;
    }

    /**
     * Method clone.
     * Creates a column of our object.
     * @return  clone out object.
     * @throws CloneNotSupportedException
     */
    @Override
    public AbstractTaskList clone() throws CloneNotSupportedException {
        AbstractTaskList cloneObj = (AbstractTaskList)super.clone();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(cloneObj);
            ByteArrayInputStream baits = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(baits);
            return (AbstractTaskList) objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
