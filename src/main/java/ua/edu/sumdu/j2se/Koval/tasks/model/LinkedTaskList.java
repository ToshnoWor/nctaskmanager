package ua.edu.sumdu.j2se.Koval.tasks.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class LinkedTaskList.
 * List of type linked TaskList.
 * @author Aleksei Koval
 */
public class LinkedTaskList extends AbstractTaskList {
    /**
     * Reference to the current item.
     */
    Node head;

    /**
     * Class Node
     * Contains our element and a link to the next one.
     */
    static class Node implements Cloneable, Serializable {
        /**
         * The data of the current item.
         */
        Task data;
        /**
         * Link to the next item.
         */
        Node next;

        /**
         * Constructor node.
         * Writes data about our item.
         * @param d
         */
        Node(Task d) {
            data = d;
            next = null;
        }
    }
    /**
     * Method add.
     * Add task to list.
     * @param data to add.
     */
    public void add(Task data){
        // Create a new node with given data
        Node new_node = new Node(data);
        new_node.next = null;

        // If the Linked List is empty,
        // then make the new node as head
        if (head == null) {
            head = new_node;
        }
        else {
            // Else traverse till the last node
            // and insert the new_node there
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }

            // Insert the new_node at last node
            last.next = new_node;
        }
    }

    /**
     * Method printList.
     * Prints all elements on terminal.
     */
    public void printList(){
        Node currNode = head;

        System.out.print("LinkedList: ");

        // Traverse through the LinkedList
        while (currNode != null) {
            // Print the data at current node
            System.out.print(currNode.data.getTitle() + " ");

            // Go to next node
            currNode = currNode.next;
        }
    }
    /**
     * Method remove.
     * Remove task from list.
     * @param task to remove.
     * @return removal result.
     */
    public boolean remove(Task task){
        // Store head node
        Node currNode = head, prev = null;
        if (currNode == null) {
            // Display the message
            System.out.println(task.getTitle() + " element not found");
            return false;
        }
        if (head.data == task) {
            head = currNode.next; // Changed head
            // Display the message
            System.out.println(task.getTitle() + " element deleted");
            // Return the updated List
            return true;
        }
        // If the index is greater than 0 but less than the size of LinkedList
        //
        // The counter
        int counter = 0;
        // Count for the index to be deleted,
        // keep track of the previous node
        // as it is needed to change currNode.next
        while (currNode != null) {
            if (currNode.data == task) {
                // Since the currNode is the required position
                // Unlink currNode from linked list
                prev.next = currNode.next;
                // Display the message
                System.out.println(task.getTitle() + " element deleted");
                return true;
            }
            else {
                // If current position is not the index
                // continue to next node
                prev = currNode;
                currNode = currNode.next;
                counter++;
            }
        }
        return false;
    }
    /**
     * Method getTask.
     * Return task on index from list.
     * @param index task from list.
     * @return task on index.
     */
    public Task getTask(int index) {
        Node currNode = head;

        if (index == 0 && currNode != null)
            return head.data;

        int counter = 0;

        while (currNode != null) {
            if (counter == index)
                return currNode.data;
            else {
                currNode = currNode.next;
                counter++;
            }
        }

        return null;
    }
    /**
     * Method getStream.
     * Transformation list to stream and returns stream.
     * @return stream.
     */
    @Override
    public Stream<Task> getStream() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            tasks.add(getTask(i));
        }
        return tasks.stream();
    }
    /**
     * Method size.
     * Counts list size.
     * @return list size.
     */
    public int size(){
        Node currNode = head;

        if (currNode == null)
            return 0;

        int size = 0;

        while (currNode != null){
            size++;
            currNode = currNode.next;
        }

        return size;
    }
    /**
     * Method toString.
     * Transforms the class to a string.
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            str.append(i);
            str.append(" ");
            str.append(getTask(i).toString());
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Task> iterator() {
        return new LinkedListIterator(this);
    }

    public class LinkedListIterator implements Iterator<Task>{
        /**
         * The number of the current item.
         */
        private Node current;
        /**
         * The number of the last item.
         */
        private Node last;
        /**
         * Data array.
         */
        private final LinkedTaskList list;

        /**
         * Constructor LinkedListIterator.
         * Writes an item to the current one.
         * @param tasks the element to be written to the list.
         */
        public LinkedListIterator(LinkedTaskList tasks) {
            this.list = tasks;
            this.current = tasks.head;
            this.last = null;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Task next() {
            if (current == null && head != null){
                current = head;
                return head.data;
            }
            else if (current != null){
                last = current;
                current = current.next;
                return last.data;
            }
            throw new NoSuchElementException();
        }
        /**
         * Method remove.
         * Removes the current item.
         */
        @Override
        public void remove() {
            if (last == null) {
                throw new IllegalStateException();
            } else {
                list.remove(last.data);
                last = null;
            }
        }
    }
}
