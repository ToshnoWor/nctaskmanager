package ua.edu.sumdu.j2se.Koval.tasks.model;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Class TaskIO.
 * This class contains methods for reading and writing to a file.
 * @author Aleksei Koval
 */
public class TaskIO {

    static private final Logger logger = Logger.getLogger(TaskIO.class);

    /**
     * Writes tasks from the list to the stream in the binary format described below.
     * @param tasks data to write
     * @param out stream to record
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DataOutputStream outStream = new DataOutputStream(out);
        outStream.writeInt(tasks.size());
        tasks.getStream().filter(Objects::nonNull).forEach(task -> {
            String title = task.getTitle();
            try {
                outStream.writeInt(title.length());
                outStream.writeUTF(title);
                outStream.writeBoolean(task.isActive());
                outStream.writeInt(task.getRepeatInterval());
                if (task.isRepeated()) {
                    outStream.writeLong(task.getStartTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                    outStream.writeLong(task.getEndTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                }
                else {
                    outStream.writeLong(task.getTime().atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli());
                }
            } catch (IOException e) {
                logger.info("Error: ", e);
            }
        });
        outStream.flush();
    }

    /**
     * Reads tasks from the stream to this task list.
     * @param tasks what
     * @param in where to
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DataInputStream inStream = new DataInputStream(in);
        int taskCount = inStream.readInt();
        for (int i = 0; i < taskCount; i++) {
            try {
                Task task;
                int titleLength = inStream.readInt();
                String title = inStream.readUTF();
                boolean isActive = inStream.readBoolean();
                int interval = inStream.readInt();
                if (interval > 0) {
                    LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                }
                else {
                    LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(inStream.readLong()),
                            TimeZone.getDefault().toZoneId());
                    task = new Task(title, time);
                    task.setActive(isActive);
                }
                tasks.add(task);
            } catch (IOException e) {
                logger.info("Error: ", e);
            }
        }

    }

    /**
     * writes tasks from the list to a file.
     * @param tasks what
     * @param file where to
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileOutputStream fos = new FileOutputStream(file);
        write(tasks, fos);
        fos.flush();
    }

    /**
     * reads tasks from the file to the task list.
     * @param tasks what
     * @param file from where
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void readBinary(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        FileInputStream fos = new FileInputStream(file);
        read(tasks, fos);
        fos.close();
    }

    /**
     * writes tasks from the list to the stream in JSON format.
     * @param tasks what
     * @param out where to
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Gson gson = new Gson();
        ArrayTaskList tasksArray = new ArrayTaskList();
        tasks.getStream().filter(Objects::nonNull).forEach(tasksArray::add);
        gson.toJson(tasksArray, out);
        out.flush();
        out.close();
    }

    /**
     * reads tasks from the stream to the list.
     * @param tasks what
     * @param in from where
     * @throws IOException
     */
    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        Gson gson = new Gson();
        ArrayTaskList taskArray = gson.fromJson(in, ArrayTaskList.class);
        for (Task task: taskArray) {
            if (task!=null)
                tasks.add(task);
        }
    }

    /**
     * writes tasks to a file in JSON format
     * @param tasks what
     * @param file where to
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void writeText(AbstractTaskList tasks, File file) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Gson gson = new Gson();
        ArrayTaskList tasksArray = new ArrayTaskList();
        tasks.getStream().filter(Objects::nonNull).forEach(tasksArray::add);
        gson.toJson(tasksArray, new FileWriter(file));
    }

    /**
     * reads tasks from a file
     * @param tasks what
     * @param file from where
     * @throws IOException
     */
    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        Gson gson = new Gson();
        ArrayTaskList taskArray = gson.fromJson(new FileReader(file), ArrayTaskList.class);
        for (Task task: taskArray) {
            if (task!=null)
                tasks.add(task);
        }
    }
}