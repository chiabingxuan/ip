package bingbong.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

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
    private static final String DATE_SAVE_FORMAT = "d/M/yyyy HH:mm";

    private final String taskName;
    private final String taskIcon;
    private final boolean isDone;

    /**
     * Initialises an incomplete task with the specified name.
     *
     * @param taskName Name of the task.
     * @param taskIcon Icon that represents the type of task.
     */
    Task(String taskName, String taskIcon) {
        this.taskName = taskName;
        this.taskIcon = taskIcon;
        this.isDone = false;
    }

    /**
     * Initialises a copy of the chosen task with the specified completion status.
     *
     * @param task   Chosen task.
     * @param isDone Whether the task has been completed.
     */
    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.taskIcon = task.taskIcon;
        this.isDone = isDone;
    }

    /**
     * Returns an icon that indicates whether this task has been marked as complete.
     *
     * @return Icon indicating this task's completion status.
     */
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
     * Returns whether the task will be occurring within the
     * selected number of days from the specified date. In the case of deadlines,
     * we check whether the deadline is due soon. In the case of
     * events, we check whether the event is starting soon.
     *
     * @param windowStartDate The start of the time window to check.
     * @param daysFromWindowStartDate Number of days from <code>windowStartDate</code>,
     *                                defining the future time window for which tasks
     *                                should be flagged.
     * @return Whether the task will be occurring soon.
     */
    abstract boolean isHappeningSoon(LocalDateTime windowStartDate, int daysFromWindowStartDate);

    /**
     * Returns a <code>String</code> corresponding to the input
     * <code>LocalDateTime</code> object. This <code>String</code> is formatted for
     * the chatbot's output, which can be viewed by the user.
     *
     * @param datetime <code>LocalDateTime</code> object.
     * @return The chosen date in <code>String</code> type.
     */
    protected String getOutputDate(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT);
        return datetime.format(formatter);
    }

    /**
     * Returns a <code>String</code> corresponding to the input
     * <code>LocalDateTime</code> object. This <code>String</code> is formatted for
     * the task storage (in the form of a .txt file).
     *
     * @param datetime <code>LocalDateTime</code> object.
     * @return The chosen date in <code>String</code> type.
     */
    protected String getSavableDate(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern(DATE_SAVE_FORMAT));
    }

    /**
     * Returns a <code>String</code> that represents this task.
     * This <code>String</code> can be saved to the task storage.
     *
     * @return <code>String</code> representation of this task.
     */
    String getSavableString() {
        return this.getStatusIcon() + DIVIDER + this.taskName;
    }

    /**
     * Returns whether the name of this task
     * contains the substring provided.
     *
     * @param substring The substring queried.
     * @return Whether the name of this task contains <code>substring</code>.
     */
    boolean hasSubstringInName(String substring) {
        return this.taskName.contains(substring);
    }

    /**
     * Returns whether this task has been completed.
     *
     * @return Whether this task has been completed.
     */
    boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[" + this.taskIcon + "]"
                + "[" + this.getStatusIcon() + "] "
                + this.taskName;
    }
}
