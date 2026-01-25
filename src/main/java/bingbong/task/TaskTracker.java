package bingbong.task;

import bingbong.util.BingBongException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class TaskTracker {
    private final ArrayList<Task> tasks;

    public TaskTracker() {
        this.tasks = new ArrayList<>();
    }

    public TaskTracker(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // for adding new task
    private TaskTracker(TaskTracker taskTracker, Task newTask) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.add(newTask);
    }

    // for editing existing task
    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.set(taskIndex, task);
    }

    // for deleting existing task
    private TaskTracker(TaskTracker taskTracker, int taskIndex) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.remove(taskIndex);
    }

    private String getWrongIndexExceptionMsg(int index) {
        int indexInInput = index + 1;  // user uses 1-indexing
        return "The task index that you have provided ("
                + indexInInput
                + ") does not exist. There are currently "
                + this.getNumOfTasks()
                + " task(s) in the list.";
    }

    // get the combined string of tasks, which can be saved
    public String getCombinedSaveableTasks(DateTimeFormatter dateFormatter) {
        // get combined string to write to saved file
        return IntStream.range(0, this.getNumOfTasks())
                .mapToObj(index -> this.tasks.get(index).getSaveableString(dateFormatter))
                .reduce((x, y) -> x + System.lineSeparator() + y)
                .orElse("");
    }

    public int getNumOfTasks() {
        return this.tasks.size();
    }

    public Task getTask(int index) throws BingBongException {
        try {
            return this.tasks.get(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    public TaskTracker editTask(int index, Task newTask)
            throws BingBongException {
        try {
            return new TaskTracker(this, newTask, index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    public TaskTracker addTask(Task newTask) {
        return new TaskTracker(this, newTask);
    }

    public TaskTracker deleteTask(int index)
            throws BingBongException {
        try {
            return new TaskTracker(this, index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    // list tasks in storage
    public String listTasks() {
        // use 1-indexing for printed list
        return IntStream.rangeClosed(1, this.getNumOfTasks())
                // create list item in String
                .mapToObj(num -> num + ". " + this.tasks.get(num - 1))
                // combine list items
                .reduce((x, y) -> x + "\n" + y)
                .orElse("");
    }

    public Task changeTaskStatusAtIndex(int taskIndex, boolean newStatus) throws BingBongException {
        Task oldTask = this.getTask(taskIndex);
        return oldTask.changeTaskStatus(newStatus);
    }

    @Override
    public String toString() {
        return IntStream.range(0, this.getNumOfTasks())
                // create list item in String
                .mapToObj(i -> this.tasks.get(i).toString())
                // combine list items
                .reduce((x, y) -> x + "\n" + y)
                .orElse("");
    }
}