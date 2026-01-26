package bingbong.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that can be recorded in the chatbot. Contains
 * details of the task, including the task name and the completion status of the
 * task.
 */
public abstract class Task {
    public static final String DONE_ICON = "X";
    public static final String NOT_DONE_ICON = " ";
    protected static final String DIVIDER = " | ";
    private static final String DATE_OUTPUT_FORMAT = "d MMM yyyy, h:mm a";

    private final String taskName;
    private final boolean isDone;

    /**
     * Initialises an incomplete task with the specified name.
     *
     * @param taskName Name of the task.
     */
    Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    /**
     * Initialises a copy of the chosen task with the specified completion status.
     *
     * @param task Chosen task.
     * @param isDone Whether the task has been completed.
     */
    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    private String getStatusIcon() {
        return this.isDone ? DONE_ICON : NOT_DONE_ICON;
    }

    /**
     * Returns a copy of this task, but with the specified completion status instead.
     *
     * @param isDoneNew New completion status for this task.
     * @return Copy of this task, with the chosen completion status.
     */
    abstract Task changeTaskStatus(boolean isDoneNew);

    /**
     * Returns a <code>String</code> corresponding to the input
     * <code>LocalDateTime</code> object. To be displayed
     * as output for the user.
     *
     * @param datetime <code>LocalDateTime</code> object.
     * @return The chosen date in <code>String</code> type.
     */
    protected String outputDate(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT);
        return datetime.format(formatter);
    }

    /**
     * Returns a <code>String</code> that represents this task.
     * This <code>String</code> can be saved to the task storage.
     *
     * @param dateFormatter A <code>DateTimeFormatter</code> that converts
     * <code>LocalDateTime</code> objects to <code>String</code> type.
     * @return <code>String</code> representation of this task.
     */
    String getSaveableString(DateTimeFormatter dateFormatter) {
        return this.getStatusIcon() + DIVIDER + this.taskName;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.taskName;
    }
}
