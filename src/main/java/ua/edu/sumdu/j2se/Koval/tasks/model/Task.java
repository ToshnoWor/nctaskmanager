package ua.edu.sumdu.j2se.Koval.tasks.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class Task. 
 * A class that can create, modify, display tasks. 
 *     Has seven fields:
 *     <ul>
 *         <li>title - This field stores the name of the task. (String type)</li>
 *         <li>time - This field stores the time of the task if it does not repeat, otherwise -1. (LocalDateTime type)</li>
 *         <li>start - This field stores the start time of the task in case it repeats, otherwise -1. (LocalDateTime type)</li>
 *         <li>end - This field stores the end time of the task in case it repeats, otherwise -1. (LocalDateTime type)</li>
 *         <li>interval - This field stores the interval of the task in cases if it is repeated, otherwise 0. (int type)</li>
 *         <li>active - This field stores the activity of the task. (boolean type)</li>
 *         <li>repeated - This field stores the repeatability or not of the task. (boolean type)</li>
 *     </ul>
 * @author Aleksei Koval
 */
public class Task implements Cloneable, Serializable {
    /**
     * This field stores the name of the task. (String type)
     */
    private String title;
    /**
     * This field stores the time of the task if it does not repeat, otherwise -1. (LocalDateTime type)
     */
    private LocalDateTime time;
    /**
     * This field stores the start time of the task in case it repeats, otherwise -1. (LocalDateTime type)
     */
    private LocalDateTime start;
    /**
     * This field stores the end time of the task in case it repeats, otherwise -1. (LocalDateTime type)
     */
    private LocalDateTime end;
    /**
     * This field stores the interval of the task in cases if it is repeated, otherwise 0. (int type)
     * Minutes!
     */
    private int interval = 0;
    /**
     * This field stores the activity of the task. (boolean type)
     */
    private boolean active = false;
    /**
     * This field stores the repeatability or not of the task. (boolean type)
     */
    private boolean repeated = false;

    /**
     * Empty constructor.
     */
    public Task(){}

    /**
     * Class constructor for non-repetitive tasks.
     * Have 2 parameters.
     * @param title task name.
     * @param time task date or time or other value.
     */
    public Task(final String title, final LocalDateTime time) throws IllegalArgumentException, NullPointerException {

        if (time != null) {
            this.time = time;
        } else {
            throw new IllegalArgumentException("Time must be not < 0.\ttime = null");
        }
        if (title!=null) {
            this.title = title;
        } else {
            throw new NullPointerException("Title must be not null");
        }
    }

    /**
     * Class constructor for repetitive tasks.
     * Have 4 parameters.
     * @param title Name the task.
     * @param start Start time of the task.
     * @param end End time of the task.
     * @param interval Regularity of the task.(The number of repetitions)
     */
    public Task(
            final String title,
            final LocalDateTime start,
            final LocalDateTime end,
            final int interval) throws IllegalArgumentException, NullPointerException {

        if (title!=null) {
            this.title = title;
        } else {
            throw new NullPointerException("Title must be not null");
        }

        if (start != null && end != null) {
            this.start = start;
            this.end = end;
        } else {
            throw new IllegalArgumentException("Start and end must be not < 0.\tstart = "
                    + start + "\tend = "
                    + end);
        }

        if (interval > 0) {
            this.interval = interval;
        } else {
            throw new IllegalArgumentException("Interval must be > 0.\tInterval = "
                    + Integer.toString(interval)
                    + ".");
        }

        this.repeated = true;
    }

    /**
     * A method <code>getTitle</code> that returns the name of the task.
     * @return Task name.
     */
    public final String getTitle() {
        return title;
    }

    /**
     * The method <code>setTitle</code> that sets the name of the task.
     * @param title Task name.
     */
    public final void setTitle(final String title) {
        this.title = title;
    }

    /**
     * A method <code>isActive</code> that returns the activity of the task. (The task is active or not at the moment)
     * @return True or false condition above.
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * The method <code>setActive</code> sets the activity of the method.
     * @param active Task activity. (True or False)
     */
    public final void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * A method <code>getTime</code> that returns the time of the task or the start of the task if it is repeated.
     * @return time
     */
    public final LocalDateTime getTime() {
        if (time == null) {
            System.out.println("Задача повторяется. Результат метода: -1.");
            return this.start;
        } else {
            return time;
        }
    }

    /**
     * A method <code>setTime</code> that sets the time of a task if it is not repeated.
     *     Gets one parameter.
     * @param time Task time.
     */
    public final void setTime(final LocalDateTime time) {
        this.time = time;
        this.repeated = false;
        this.start = null;
        this.end = null;
        this.interval = 0;
    }

    /**
     * A method <code>setTime</code> that sets the start, end, and interval of a task.
     *     Gets 3 parameters.
     * @param start Start of the task.
     * @param end End of the task.
     * @param interval Interval of the task.
     */
    public final void setTime(final LocalDateTime start,
                              final LocalDateTime end,
                              final int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;
        this.time = null;
    }

    /**
     * The method <code>getStartTime</code> returns the start time of the task if it is repeated, otherwise it returns the task time.
     * @return Start time of the task.
     */
    public final LocalDateTime getStartTime() {
        if (start == null) {
            return time;
        } else return start;
    }

    /**
     * The method <code>getEndTime</code> returns the end time of the task if it is repeated, otherwise it returns the time of the task.
     * @return End time of the task.
     */
    public final LocalDateTime getEndTime() {
        if (end == null) {
            return time;
        } else return end;
    }

    /**
     * The method <code>getRepeatInterval</code> returns the interval of the task execution.
     * @return Task interval.
     */
    public final int getRepeatInterval() {
        return interval;
    }

    /**
     * The method <code>isRepeated</code> returns whether the task is repeated or not.
     * @return True or false expressions above.
     */
    public final boolean isRepeated() {
        return repeated;
    }

    /**
     * The method <code>nextTimeAfter</code> returns the next task time from the requested one, if possible.
     * @param current Current time.
     * @return The next task time, if any, otherwise -1.
     */
    public final LocalDateTime nextTimeAfter(final LocalDateTime current) {
        if (!this.active)
            return null;
        if (this.time != null) {
            if (current.isAfter(this.time) || current.isEqual(this.time))
                return null;
            else return this.time;
        }
        else {
            if (current.isBefore(this.start))
                return this.start;
            if (current.isAfter(this.end))
                return null;
            LocalDateTime prevTime = this.start;
            for (LocalDateTime i = this.start; i.isBefore(this.end) || i.isEqual(this.end); i = i.plusMinutes(this.interval)) {
                if((prevTime.isBefore(current) || prevTime.isEqual(current)) && i.isAfter(current)) {
                    return i;
                }
                prevTime = i;
            }
            return null;
        }
    }
    /**
     * Compares our object and the comer.
     * @param o object of comparison.
     * @return <strong>True</strong> if they are equal, <strong>false</strong> if not equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (repeated != ((Task) o).isRepeated())
            return false;
        if (repeated)
            return  start.equals(task.start) &&
                    end.equals(task.end) &&
                    interval == task.interval &&
                    active == task.active &&
                    Objects.equals(title, task.title);
        else
            return  time.equals(task.time) &&
                    interval == task.interval &&
                    active == task.active &&
                    Objects.equals(title, task.title);
    }
    /**
     * Method hashCode.
     * Creates a numeric item ID.
     * @return int
     */
    @Override
    public int hashCode() {
        if (time == null){
            return Objects.hash(title, start, end, interval, active, repeated);
        }
        else
            return Objects.hash(title, time, active, repeated);
    }
    /**
     * Method toString.
     * Transforms the class to a string.
     * @return string
     */
    @Override
    public String toString() {
        return "Task:" +
                "\t\ttitle: " + title +
                "\t\ttime: " + time +
                "\t\tstart: " + start +
                "\t\tend: " + end +
                "\t\tinterval: " + interval +
                "\t\tactive: " + active +
                "\t\trepeated: " + repeated;
    }
    /**
     * Method clone.
     * Creates a column of our object.
     * @return  clone out object.
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}