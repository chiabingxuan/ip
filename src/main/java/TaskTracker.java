import java.util.ArrayList;
import java.util.stream.IntStream;

class TaskTracker {
    private final ArrayList<Task> tasks;

    TaskTracker() {
        this.tasks = new ArrayList<>();
    }

    // for adding new task
    private TaskTracker(TaskTracker taskTracker, Task newTask) {
        this.tasks = taskTracker.tasks;
        this.tasks.add(newTask);
    }

    // for editing existing task
    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex) {
        this.tasks = taskTracker.tasks;
        this.tasks.set(taskIndex, task);
    }

    // for deleting existing task
    private TaskTracker(TaskTracker taskTracker, int taskIndex) {
        this.tasks = taskTracker.tasks;
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

    TaskTracker editTask(int index, Task newTask) throws BingBongException {
        try {
            return new TaskTracker(this, newTask, index);
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    TaskTracker addTask(Task newTask) {
        return new TaskTracker(this, newTask);
    }

    TaskTracker deleteTask(int index) throws BingBongException {
        try {
            return new TaskTracker(this, index);
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