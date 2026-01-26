package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.Ui;

/**
 * Represents a command where all tasks whose names match a given substring
 * are to be listed out.
 */
public class FindCommand extends Command {
    private final String substring;

    /**
     * Initialises a <code>FindCommand</code>.
     */
    public FindCommand(String substring) {
        super(false);
        this.substring = substring;
    }

    /**
     * Executes this <code>FindCommand</code> and returns the new task list, upon
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
        String listOfTasks = taskTracker.findTasks(this.substring);
        ui.printMatchingTasksMessage(listOfTasks);
        return taskTracker;
    }

    @Override
    public String toString() {
        return "find command: " + substring;
    }
}
