import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.IntStream;

class TaskTracker {
    private final ArrayList<Task> tasks;
    private final String tasksFilePath;
    TaskTracker(String tasksFilePath) {
        this.tasks = new ArrayList<>();
        this.tasksFilePath = tasksFilePath;
    }

    // for adding new task
    private TaskTracker(TaskTracker taskTracker, Task newTask, String tasksFilePath) {
        this.tasks = taskTracker.tasks;
        this.tasks.add(newTask);

        this.tasksFilePath = tasksFilePath;
    }

    // for editing existing task
    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex,
                        String tasksFilePath) {
        this.tasks = taskTracker.tasks;
        this.tasks.set(taskIndex, task);

        this.tasksFilePath = tasksFilePath;
    }

    // for deleting existing task
    private TaskTracker(TaskTracker taskTracker, int taskIndex,
                        String tasksFilePath) {
        this.tasks = taskTracker.tasks;
        this.tasks.remove(taskIndex);

        this.tasksFilePath = tasksFilePath;
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
    String getCombinedSaveableTasks(DateTimeFormatter dateFormatter) {
        // get combined string to write to saved file
        return IntStream.range(0, this.getNumOfTasks())
                .mapToObj(index -> this.tasks.get(index).getSaveableString(dateFormatter))
                .reduce((x, y) -> x + System.lineSeparator() + y)
                .orElse("");
    }

    int getNumOfTasks() {
        return this.tasks.size();
    }

    Task getTask(int index) throws BingBongException {
        try {
            return this.tasks.get(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    TaskTracker editTask(int index, Task newTask)
            throws BingBongException {
        try {
            return new TaskTracker(this, newTask, index,
                    this.tasksFilePath);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    TaskTracker addTask(Task newTask) {
        return new TaskTracker(this, newTask,
                this.tasksFilePath);
    }

    TaskTracker deleteTask(int index)
            throws BingBongException {
        try {
            return new TaskTracker(this, index,
                    this.tasksFilePath);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    // list tasks in storage
    String listTasks() {
        // use 1-indexing for printed list
        return IntStream.rangeClosed(1, this.getNumOfTasks())
                // create list item in String
                .mapToObj(num -> num + ". " + this.tasks.get(num - 1))
                // combine list items
                .reduce("", (x, y) -> x + "\n" + y);
    }

    Task changeTaskStatusAtIndex(int taskIndex, boolean newStatus) throws BingBongException {
        Task oldTask = this.getTask(taskIndex);
        return oldTask.changeTaskStatus(newStatus);
    }
}