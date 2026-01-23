import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

class TaskTracker {
    private final ArrayList<Task> tasks;
    private final String dataFolderPath;
    private final String tasksFilename;

    TaskTracker(String dataFolderPath, String tasksFilename) {
        this.tasks = new ArrayList<>();
        this.dataFolderPath = dataFolderPath;
        this.tasksFilename = tasksFilename;
    }

    // for adding new task
    private TaskTracker(TaskTracker taskTracker, Task newTask,
                        String dataFolderPath, String tasksFilename) {
        this.tasks = taskTracker.tasks;
        this.tasks.add(newTask);

        this.dataFolderPath = dataFolderPath;
        this.tasksFilename = tasksFilename;
    }

    // for editing existing task
    private TaskTracker(TaskTracker taskTracker, Task task, int taskIndex,
                        String dataFolderPath, String tasksFilename) {
        this.tasks = taskTracker.tasks;
        this.tasks.set(taskIndex, task);

        this.dataFolderPath = dataFolderPath;
        this.tasksFilename = tasksFilename;
    }

    // for deleting existing task
    private TaskTracker(TaskTracker taskTracker, int taskIndex,
                        String dataFolderPath, String tasksFilename) {
        this.tasks = taskTracker.tasks;
        this.tasks.remove(taskIndex);

        this.dataFolderPath = dataFolderPath;
        this.tasksFilename = tasksFilename;
    }

    private String getWrongIndexExceptionMsg(int index) {
        int indexInInput = index + 1;  // user uses 1-indexing
        return "The task index that you have provided ("
                + indexInInput
                + ") does not exist. There are currently "
                + this.getNumOfTasks()
                + " task(s) in the list.";
    }

    // save recorded tasks to file
    void saveTasks() {
        try {
            // get combined string to write to saved file
            String textToWrite = IntStream.range(0, this.getNumOfTasks())
                    .mapToObj(index -> this.tasks.get(index).getSaveableString())
                    .reduce((x, y) -> x + System.lineSeparator() + y)
                    .orElse("");

            // write to file
            String tasksFilePath = dataFolderPath + "/" + this.tasksFilename;
            FileWriter fw = new FileWriter(tasksFilePath);
            fw.write(textToWrite);
            fw.close();
        } catch (IOException ex) {
            System.out.println(new Message("Something went wrong when saving the tasks: "
                    + ex.getMessage()
                    + "\nHence, the tasks are not being saved to disk."));
        }
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
            TaskTracker modifiedTracker = new TaskTracker(this, newTask, index,
                    this.dataFolderPath, this.tasksFilename);
            modifiedTracker.saveTasks();
            return modifiedTracker;
        } catch (IndexOutOfBoundsException ex) {
            throw new BingBongException(this.getWrongIndexExceptionMsg(index));
        }
    }

    TaskTracker addTask(Task newTask) {
        TaskTracker modifiedTracker = new TaskTracker(this, newTask,
                this.dataFolderPath, this.tasksFilename);
        modifiedTracker.saveTasks();
        return modifiedTracker;
    }

    TaskTracker deleteTask(int index) throws BingBongException {
        try {
            TaskTracker modifiedTracker = new TaskTracker(this, index,
                    this.dataFolderPath, this.tasksFilename);
            modifiedTracker.saveTasks();
            return modifiedTracker;
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