package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.MessageFormatter;
import bingbong.util.Storage;

/**
 * Represents a command where all current tasks are to be listed out.
 */
public class ListCommand extends Command {
    /**
     * Initialises a <code>ListCommand</code>.
     */
    public ListCommand() {
        super(false);
    }

    /**
     * Executes this <code>ListCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     */
    public TaskTracker execute(TaskTracker taskTracker, Storage storage) {
        String listOfTasks = taskTracker.listTasks();
        super.addToCommandOutput(MessageFormatter.getListTasksMessage(listOfTasks));
        return taskTracker;
    }

    @Override
    public String toString() {
        return "list command";
    }
}
