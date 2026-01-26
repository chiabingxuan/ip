package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

/**
 * Represents a command where a task is to be marked as incomplete.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Initialises an <code>UnmarkCommand</code>.
     *
     * @param index List index of the task to be marked as incomplete.
     */
    public UnmarkCommand(int index) {
        super(false);
        this.index = index;
    }

    /**
     * Executes this <code>UnmarkCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param ui User interface that displays messages to the user,
     * during the command's execution.
     * @param storage Storage which updates the task file with the new
     * task list (if modifications have been made),
     * at the end of the command's execution.
     * @return New task list.
     * @throws BingBongException If the command was not executed successfully.
     */
    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage)
            throws BingBongException {
        Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(this.index, false);
        taskTracker = taskTracker.editTask(this.index, unmarkedTask);

        ui.printUnmarkedTaskMessage(unmarkedTask);

        return taskTracker;
    }

    @Override
    public String toString() {
        return "unmark command: " + this.index;
    }
}
