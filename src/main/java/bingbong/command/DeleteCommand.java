package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.MessageFormatter;
import bingbong.util.Storage;

/**
 * Represents a command where a task is to be deleted.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Initialises a <code>DeleteCommand</code>.
     *
     * @param index List index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        super(false);
        this.index = index;
    }

    /**
     * Executes this <code>DeleteCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     * @throws BingBongException If the command was not executed successfully.
     */
    public TaskTracker execute(TaskTracker taskTracker, Storage storage)
            throws BingBongException {
        Task taskToDelete = taskTracker.getTask(this.index);
        taskTracker = taskTracker.deleteTask(this.index);
        int newNumOfTasks = taskTracker.getNumOfTasks();

        super.addToCommandOutput(MessageFormatter.getDeletedTaskMessage(taskToDelete, newNumOfTasks));

        return taskTracker;
    }

    @Override
    public String toString() {
        return "delete command: " + this.index;
    }
}
