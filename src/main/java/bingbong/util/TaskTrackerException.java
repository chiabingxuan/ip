package bingbong.util;

/**
 * Represents an exception thrown when the task list
 * cannot complete an operation as expected.
 */
public class TaskTrackerException extends BingBongException {
    /**
     * Initialises a new <code>TaskTrackerException</code>.
     *
     * @param message Error message to be shown in the exception.
     */
    public TaskTrackerException(String message) {
        super(message);
    }
}
