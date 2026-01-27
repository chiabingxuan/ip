package bingbong.task;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import bingbong.util.BingBongException;

/**
 * Manages the current list of tasks recorded, during the running of the
 * chatbot. Supports numerous operation on the task list, such as the
 * addition, removal, retrieval, editing and listing of tasks.
 */
public class TaskTracker {
    private final ArrayList<Task> tasks;

    /**
     * Initialises an empty task list.
     */
    public TaskTracker() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initialises a task list populated by the specified
     * list of tasks.
     *
     * @param tasks List of tasks that the list will contain
     *              upon initialisation.
     */
    public TaskTracker(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Initialises a new task list containing all the tasks from
     * a previous task list, as well as a new task. Effectively
     * adds a new task to the list.
     *
     * @param taskTracker Previous task list.
     * @param newTask     New task to be added.
     */
    private TaskTracker(TaskTracker taskTracker, Task newTask) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.add(newTask);
    }

    /**
     * Initialises a new task list containing all the tasks from
     * a previous task list, except at the specified index, where
     * the previous task has been replaced by the new task provided.
     * Effectively modifies the task at that index.
     *
     * @param taskTracker Previous task list.
     * @param task        New task.
     * @param taskIndex   Index at which the replacement should occur.
     */
    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.set(taskIndex, task);
    }

    /**
     * Initialises a new task list containing all the tasks from
     * a previous task list, except at the specified index, where
     * the previous task has been deleted.
     * Effectively deletes the task at that index.
     *
     * @param taskTracker Previous task list.
     * @param taskIndex   Index at which the deletion should occur, with
     *                    respect to the previous task list.
     */
    private TaskTracker(TaskTracker taskTracker, int taskIndex) {
        this.tasks = new ArrayList<>(taskTracker.tasks);
        this.tasks.remove(taskIndex);
    }

    private String getWrongIndexExceptionMsg(int index) {
        // user uses 1-indexing
        int indexInInput = index + 1;

        return "The task index that you have provided ("
                + indexInInput
                + ") does not exist. There are currently "
                + this.getNumOfTasks()
                + " task(s) in the list.";
    }

    private List<Task> getFilteredTasks(Predicate<Task> predicate) {
        return this.tasks.stream()
                .filter(predicate)
                .toList();
    }

    private String getNumberedTaskList(List<Task> tasks) {
        // use 1-indexing for printed list
        return IntStream.rangeClosed(1, tasks.size())
                // create list item in String
                .mapToObj(num -> num + ". " + tasks.get(num - 1))
                // combine list items
                .reduce((x, y) -> x + "\n" + y)
                .orElse("");
    }

    /**
     * Returns a concatenated <code>String</code> of all the tasks currently being
     * recorded. This <code>String</code> can be saved to the task storage.
     *
     * @param dateFormatter A <code>DateTimeFormatter</code> that converts
     *                      <code>LocalDateTime</code> objects to <code>String</code> type.
     * @return Combined <code>String</code> of all the tasks currently in the list.
     */
    public String getCombinedSaveableTasks(DateTimeFormatter dateFormatter) {
        // get combined string to write to saved file
        return this.getFilteredTasks(task -> true) // get all tasks
                .stream()
                .map(task -> task.getSaveableString(dateFormatter))
                .reduce((x, y) -> x + System.lineSeparator() + y)
                .orElse("");
    }

    /**
     * Returns the current number of recorded tasks.
     *
     * @return Current number of recorded tasks.
     */
    public int getNumOfTasks() {
        return this.tasks.size();
    }

    /**
     * Returns the task at the list index specified.
     *
     * @param index List index of the task to be returned.
     * @return Task at the chosen index.
     * @throws BingBongException If <code>index</code> is out of bounds of the task list.
     */
    public Task getTask(int index) throws BingBongException {
        try {
            return this.tasks.get(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    /**
     * Returns a new task list, where the task at the chosen
     * index has now been replaced with the new task specified.
     *
     * @param index   List index of the task to be edited.
     * @param newTask New task to replace the previous task with.
     * @throws BingBongException If <code>index</code> is out of bounds of the task list.
     */
    public TaskTracker editTask(int index, Task newTask)
            throws BingBongException {
        try {
            return new TaskTracker(this, newTask, index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    /**
     * Returns a new task list, which also contains the new task specified,
     * in addition to all previous tasks.
     *
     * @param newTask New task to be added.
     */
    public TaskTracker addTask(Task newTask) {
        return new TaskTracker(this, newTask);
    }

    /**
     * Returns a new task list, where the task at the chosen
     * index has now been deleted.
     *
     * @param index List index of the task to be deleted.
     * @throws BingBongException If <code>index</code> is out of bounds of the task list.
     */
    public TaskTracker deleteTask(int index)
            throws BingBongException {
        try {
            return new TaskTracker(this, index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    /**
     * Returns a <code>String</code> showing the current tasks
     * in a numbered list.
     *
     * @return Numbered list of all the tasks currently being recorded.
     */
    public String listTasks() {
        // get all tasks
        List<Task> tasks = this.getFilteredTasks(task -> true);

        return this.getNumberedTaskList(tasks);
    }

    /**
     * Returns a <code>String</code> showing all the tasks whose names
     * match the given substring, in a numbered list.
     *
     * @return Numbered list of all the tasks currently being recorded.
     */
    public String findTasks(String substring) {
        // get tasks containing the given substring
        List<Task> tasks = this.getFilteredTasks(task ->
                task.hasSubstringInName(substring));

        return this.getNumberedTaskList(tasks);
    }

    /**
     * Returns a copy of the task at the chosen index,
     * except that its status corresponds to what is specified.
     * Effectively updates the status of the task at the index of choice.
     *
     * @param taskIndex List index of the task whose status is to be changed.
     * @param isDoneNew New completion status of the chosen task
     * @return Copy of the task at the chosen index, with its status updated accordingly.
     * @throws BingBongException If <code>index</code> is out of bounds of the task list.
     */
    public Task changeTaskStatusAtIndex(int taskIndex, boolean isDoneNew)
            throws BingBongException {
        Task oldTask = this.getTask(taskIndex);
        return oldTask.changeTaskStatus(isDoneNew);
    }

    @Override
    public String toString() {
        return this.getFilteredTasks(task -> true) // get all tasks
                .stream()
                // create list item in String
                .map(task -> task.toString())
                // combine list items
                .reduce((x, y) -> x + "\n" + y)
                .orElse("");
    }
}
