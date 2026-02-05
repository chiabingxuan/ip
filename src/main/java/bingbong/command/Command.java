package bingbong.command;

import java.util.ArrayList;

import bingbong.message.Message;
import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.TaskTrackerException;

/**
 * Represents a command that is to be executed, according
 * to what is requested in the user input.
 */
public abstract class Command {
    private ArrayList<Message> outputMessages;

    /**
     * Initialises a command.
     */
    Command() {
        this.outputMessages = new ArrayList<>();
    }

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
