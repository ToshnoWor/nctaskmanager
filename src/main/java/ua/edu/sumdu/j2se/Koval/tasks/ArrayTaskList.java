package ua.edu.sumdu.j2se.Koval.tasks;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Class ArrayTaskList.
 * List of type Array TaskList.
 * @author Aleksei Koval
 */
public class ArrayTaskList extends AbstractTaskList {
    /**
     * The size of the ArrayList (the number of elements it contains).
     * @serial
     */
    private int size;
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Shared empty array instance used for empty instances.
     */
    private final Task[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private Task[] tasks;
    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayTaskList(){
        this.tasks = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
    /**
     * Add task in list end.
     *
     * @param task element to be inserted
     * @throws IndexOutOfBoundsException
     */
    public void add(Task task){
        if (size == tasks.length)
            tasks = grow();
        tasks[size] = task;
        size = size + 1;
    }

    private int newLength(int oldLength, int minGrowth, int prefGrowth){
        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private int hugeLength(int oldLength, int minGrowth){
        int minLength = oldLength + minGrowth;
        if (minLength < 0) {
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= MAX_ARRAY_LENGTH) {
            return MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }
    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param min the desired minimum capacity
     */
    private Task[] grow(int min){
        int old = tasks.length;
        if (old > 0 || tasks != DEFAULTCAPACITY_EMPTY_ELEMENTDATA){
            int newCapacity = newLength(old, min - old, old >> 1);
            Task[] copy = new Task[newCapacity];
            System.arraycopy(tasks, 0, copy, 0, Math.min(tasks.length, newCapacity));
            return copy;
        } else {
            return tasks = new Task[Math.max(DEFAULT_CAPACITY, min)];
        }
    }

    private Task[] grow() {
        return grow(size + 1);
    }

    /**
     * Method remove.
     * Remove task from list.
     * @param task to remove.
     * @return removal result.
     */
    public boolean remove(Task task){
        Task[] es = new Task[this.size-1];
        boolean in = false;
        int index = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.tasks[i].equals(task)){
                index = i;
                in = true;
            }
        }

        if (in){
            for (int i = 0; i < this.size-1; i++) {
                if (i < index){
                    es[i] = this.tasks[i];
                } else {
                    es[i] = this.tasks[i+1];
                }
            }
            this.tasks = es;
            this.size = this.size -1;
        }

        return in;
    }
    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size(){
        return size;
    }
    /**
     * Method getTask.
     * Return task on index from list.
     * @param index task from list.
     * @return task on index.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException{
            if (index < size && index >= 0)
                return tasks[index];
            else throw new IndexOutOfBoundsException("Index must be < size and >= 0.\tindex = "
                    + Integer.toString(index) + "\tsize = "
                    + Integer.toString(this.size));
    }
    /**
     * Method getStream.
     * Transformation list to stream and returns stream.
     * @return stream.
     */
    @Override
    public Stream<Task> getStream() {
        Task[] array = tasks;
        return Stream.of(array);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayListIterator(this);
    }

    /**
     * Class ArrayListIterator.
     */
    public class ArrayListIterator implements Iterator<Task>{
        /**
         * Data array.
         */
        private final ArrayTaskList array;
        /**
         * The number of the current item.
         */
        private int index;
        /**
         * The number of the last item.
         */
        private int last;

        /**
         * Method hasNext.
         * Checks if there is a next item.
         * @return True if there is, false if not.
         */
        @Override
        public boolean hasNext() {
            return (index < array.size());
        }

        /**
         * Method next.
         * Returns the next item.
         * @return next item.
         */
        @Override
        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = index;
            return array.getTask(index++);
        }
        /**
         * Constructor ArrayListIterator.
         * Writes an list to the current one.
         * @param array the element to be written to the list.
         */
        public ArrayListIterator(ArrayTaskList array) {
            this.array = array;
            this.index = 0;
            this.last = -1;
        }

        /**
         * Method remove.
         * Removes the current item.
         */
        public void remove()
        {
            if (last < 0) {
                throw new IllegalStateException();
            } else {
                array.remove(array.getTask(last));
                index = last;
                last = -1;
            }
        }
    }

    /**
     * Method toString.
     * Transforms the class to a string.
     * @return string
     */
    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "size=" + size +
                ", tasks=" + Arrays.toString(tasks).toString() +
                '}';
    }
}
