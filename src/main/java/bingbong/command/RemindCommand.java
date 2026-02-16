package bingbong.command;

import java.time.LocalDateTime;

import bingbong.message.SuccessMessage;
import bingbong.task.TaskTracker;
import bingbong.util.MessageFormatter;
import bingbong.util.Storage;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

/**
 * Represents a command where we want to remind users
 * of impending, outstanding tasks.
 */
public class RemindCommand extends Command {
    private final int daysFromNow;

    /**
     * Initialises a <code>RemindCommand</code>.
     *
     * @param daysFromNow Number of days from the current date, defining the future time
     *                    window for which tasks should be flagged
     */
    public RemindCommand(int daysFromNow) {
        super();
        this.daysFromNow = daysFromNow;
    }

    /**
     * Executes this <code>RemindCommand</code> and returns the new task list, upon
     * completion of the command.
     *
     * @param taskTracker Task list before the command's execution.
     * @param storage     Storage which updates the task file with the new
     *                    task list (if modifications have been made),
     *                    at the end of the command's execution.
     * @return New task list.
     */
    public TaskTracker execute(TaskTracker taskTracker, Storage storage) {
        // start checking for closest tasks from current time
        String listOfTasks = taskTracker.remindImpendingTasks(LocalDateTime.now(),
                this.daysFromNow);

        // add message to output
        SuccessMessage successMessage = new SuccessMessage(MessageFormatter
                .getReminderMessage(this.daysFromNow, listOfTasks));
        super.addToOutputMessages(successMessage);

        return taskTracker;
    }

    @Override
    public String toString() {
        return "remind command: " + this.daysFromNow;
    }
}
