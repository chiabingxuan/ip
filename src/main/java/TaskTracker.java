import java.util.stream.IntStream;

class TaskTracker {
    private static final int MAX_TASKS = 100;
    private final Task[] tasks;
    private final int numOfTasks;

    TaskTracker() {
        this.tasks = new Task[MAX_TASKS];
        this.numOfTasks = 0;
    }

    private TaskTracker(TaskTracker taskTracker, String newTaskName) {
        this.tasks = taskTracker.tasks;
        this.tasks[taskTracker.numOfTasks] = new Task(newTaskName);
        this.numOfTasks = taskTracker.numOfTasks + 1;
    }

    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex) {
        this.tasks = taskTracker.tasks;
        this.tasks[taskIndex] = task;
        this.numOfTasks = taskTracker.numOfTasks;
    }

    Task getTask(int index) {
        if (index < 0 || index >= this.numOfTasks) {
            // index out of range
            throw new IndexOutOfBoundsException("Task index " + index + "does not exist");
        }

        return this.tasks[index];
    }

    TaskTracker editTask(int index, Task newTask) {
        if (index < 0 || index >= this.numOfTasks) {
            // index out of range
            throw new IndexOutOfBoundsException("Task index " + index + " does not exist");
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

    TaskTracker addTask(String taskName) {
        if (this.numOfTasks >= MAX_TASKS) {
            // storage is full, throw exception
            throw new IllegalStateException("Task storage is full");
        }

        return new TaskTracker(this, taskName);
    }

    Task changeTaskStatus(int taskIndex, boolean newStatus) {
        Task oldTask = this.getTask(taskIndex);
        return new Task(oldTask, newStatus);
    }
}
