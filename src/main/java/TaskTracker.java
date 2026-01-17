import java.util.stream.IntStream;

class TaskTracker {
    private static final int MAX_TASKS = 100;
    private final Task[] tasks;
    private final int numOfTasks;

    TaskTracker() {
        this.tasks = new Task[MAX_TASKS];
        this.numOfTasks = 0;
    }

    private TaskTracker(TaskTracker taskTracker, Task newTask) {
        this.tasks = taskTracker.tasks;
        this.tasks[taskTracker.numOfTasks] = newTask;
        this.numOfTasks = taskTracker.numOfTasks + 1;
    }

    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex) {
        this.tasks = taskTracker.tasks;
        this.tasks[taskIndex] = task;
        this.numOfTasks = taskTracker.numOfTasks;
    }

    private String getWrongIndexExceptionMsg(int index) {
        int indexInInput = index + 1;  // user uses 1-indexing
        return "The task index that you have provided ("
                + indexInInput
                + ") does not exist. There are currently "
                + this.numOfTasks
                + " task(s) in the list.";
    }

    private String getFullListExceptionMsg() {
        return "The task tracker is full with "
                + MAX_TASKS
                + " tasks, and cannot store any more tasks.";
    }

    Task getTask(int index) throws BingBongException {
        if (index < 0 || index >= this.numOfTasks) {
            // index out of range
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }

        return this.tasks[index];
    }

    TaskTracker editTask(int index, Task newTask) throws BingBongException {
        if (index < 0 || index >= this.numOfTasks) {
            // index out of range
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }

        return new TaskTracker(this, newTask, index);
    }

    // list tasks in storage
    String listTasks() {
        // use 1-indexing for printed list
        return IntStream.rangeClosed(1, this.numOfTasks)
                // create list item in String
                .mapToObj(num -> num + ". " + this.tasks[num - 1])
                // combine list items
                .reduce("", (x, y) -> x + "\n" + y);
    }

    TaskTracker addTask(Task newTask) throws BingBongException {
        if (this.numOfTasks >= MAX_TASKS) {
            // storage is full, throw exception
            throw new BingBongException(this.getFullListExceptionMsg());
        }

        return new TaskTracker(this, newTask);
    }

    Task changeTaskStatusAtIndex(int taskIndex, boolean newStatus) throws BingBongException {
        Task oldTask = this.getTask(taskIndex);
        return oldTask.changeTaskStatus(newStatus);
    }

    int getNumOfTasks() {
        return this.numOfTasks;
    }
}