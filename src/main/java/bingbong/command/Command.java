package bingbong.command;

import java.util.Optional;

import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;

/**
 * Represents a command that is to be executed, according
 * to what is requested in the user input.
 */
public abstract class Command {
    private final boolean isExit;
    private Optional<String> commandOutput;

    /**
     * Initialises a command.
     *
     * @param isExit Whether the command should terminate the application.
     */
    Command(boolean isExit) {
        this.isExit = isExit;
        this.commandOutput = Optional.empty();
    }

    protected void addToCommandOutput(String outputToAdd) {
        this.commandOutput = this.commandOutput
                .map(out -> out + "\n\n" + outputToAdd)
                .or(() -> Optional.of(outputToAdd));
    }

    /**
     * Executes this command and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     * @throws BingBongException If the command was not executed successfully.
     */
    public abstract TaskTracker execute(TaskTracker taskTracker, Storage storage)
            throws BingBongException;

    /**
     * Returns the output message after the command has been executed.
     *
     * @return The output message after the command has been executed.
     */
    public String getString() {
        return this.commandOutput.orElse("");
    }

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
