package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

import java.time.format.DateTimeFormatter;

public class AddCommand extends Command {
    private final Task task;
    private final DateTimeFormatter dateFormatter;

    public AddCommand(Task task, String dateFormat) {
        super(false);
        this.task = task;
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        // add to tracker
        taskTracker = taskTracker.addTask(task);
        int newNumOfTasks = taskTracker.getNumOfTasks();

        // print in display
        ui.printAddTaskMessage(task, newNumOfTasks);

        // update storage
        try {
            storage.saveTasks(taskTracker.getCombinedSaveableTasks(this.dateFormatter));
        } catch (BingBongException ex) {
            ui.printWarning(ex.getMessage());
        }

        return taskTracker;
    }

    @Override
    public String toString() {
        return "add command: " + this.task.toString();
    }
}
