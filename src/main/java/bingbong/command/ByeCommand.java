package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.Ui;

/**
 * Represents a command where the application is to be terminated.
 */
public class ByeCommand extends Command {
    /**
     * Initialises a <code>ByeCommand</code>.
     */
    public ByeCommand() {
        super(true);
    }

    /**
     * Executes this <code>ByeCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param ui User interface that displays messages to the user,
     * during the command's execution.
     * @param storage Storage which updates the task file with the new
     * task list (if modifications have been made),
     * at the end of the command's execution.
     * @return New task list.
     */
    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        ui.sayGoodbye();
        return taskTracker;
    }

    @Override
    public String toString() {
        return "bye command";
    }
}
