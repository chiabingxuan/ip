package bingbong.command;

import bingbong.message.SuccessMessage;
import bingbong.message.WarningMessage;
import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.MessageFormatter;
import bingbong.util.Storage;
import bingbong.util.StorageException;
import bingbong.util.TaskTrackerException;

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
        super();
        this.index = index;
    }

    /**
     * Executes this <code>UnmarkCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     * @throws TaskTrackerException If the command was not executed successfully.
     */
    public TaskTracker execute(TaskTracker taskTracker, Storage storage)
            throws TaskTrackerException {
        Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(this.index, false);
        taskTracker = taskTracker.editTask(this.index, unmarkedTask);

        // add message to output
        SuccessMessage successMessage = new SuccessMessage(MessageFormatter
                .getUnmarkedTaskMessage(unmarkedTask));
        super.addToOutputMessages(successMessage);

        // update storage
        try {
            storage.saveTasks(taskTracker.getCombinedSaveableTasks());
        } catch (StorageException ex) {
            WarningMessage warningMessage = new WarningMessage(ex.getMessage());
            super.addToOutputMessages(warningMessage);
        }

        return taskTracker;
    }

    @Override
    public String toString() {
        return "unmark command: " + this.index;
    }
}
