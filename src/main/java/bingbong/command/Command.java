package bingbong.command;

import java.util.ArrayList;

import bingbong.message.Message;
import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.TaskTrackerException;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

/**
 * Represents a command that is to be executed, according
 * to what is requested in the user input.
 */
public abstract class Command {
    private final ArrayList<Message> outputMessages;

    /**
     * Initialises a command.
     */
    Command() {
        this.outputMessages = new ArrayList<>();
    }

    /**
     * Adds a message to the list of output messages produced by this command.
     * This method is intended to be used by subclasses during command
     * execution to accumulate messages for display to the user.
     *
     * @param msgToAdd The message to be added to the output message list.
     */
    protected void addToOutputMessages(Message msgToAdd) {
        this.outputMessages.add(msgToAdd);
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
     * @throws TaskTrackerException If the command was not executed successfully.
     */
    public abstract TaskTracker execute(TaskTracker taskTracker, Storage storage)
            throws TaskTrackerException;

    /**
     * Returns the output messages after the command has been executed.
     *
     * @return The output messages after the command has been executed.
     */
    public ArrayList<Message> getOutputMessages() {
        return this.outputMessages;
    }
}
