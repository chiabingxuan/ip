package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

/**
 * Represents a command that is to be executed, according
 * to what is requested in the user input.
 */
public abstract class Command {
    private final boolean isExit;

    /**
     * Initialises a command.
     *
     * @param isExit Whether the command should terminate the application.
     */
    Command(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * Executes this command and returns the new task list, upon
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
    abstract public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage)
            throws BingBongException;

    /**
     * Returns a flag which shows whether this command
     * should terminate the application.
     *
     * @return Whether this command should terminate the application.
     */
    public boolean isExit() {
        return this.isExit;
    }
}
