package bingbong.command;

import java.time.format.DateTimeFormatter;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

/**
 * Represents a command where a new task is to be added.
 */
public class AddCommand extends Command {
    private final Task task;
    private final DateTimeFormatter dateFormatter;

    /**
     * Initialises an <code>AddCommand</code>.
     *
     * @param task       Task to be added.
     * @param dateFormat Date format used to convert <code>LocalDateTime</code>
     *                   objects to <code>String</code>, for the saving of tasks to the storage.
     */
    public AddCommand(Task task, String dateFormat) {
        super(false);
        this.task = task;
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    /**
     * Executes this <code>AddCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param ui          User interface that displays messages to the user,
     *                    during the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     */
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
