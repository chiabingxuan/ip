package bingbong.task;

import java.time.LocalDateTime;

/**
 * Represents a deadline that can be recorded in the chatbot. Contains
 * details of the deadline, including the deadline name, the completion status of the
 * deadline, and the date by which the deadline must be completed.
 */
public class Deadline extends Task {
    public static final String TASK_ICON = "D";
    private final LocalDateTime byWhen;

    /**
     * Initialises an incomplete deadline with the specified name and date.
     *
     * @param taskName Name of the deadline.
     * @param byWhen   Date by which the deadline must be completed.
     */
    public Deadline(String taskName, LocalDateTime byWhen) {
        super(taskName, TASK_ICON);
        this.byWhen = byWhen;
    }

    /**
     * Initialises a copy of the chosen deadline with the specified completion status.
     *
     * @param deadline Chosen deadline.
     * @param isDone   Whether the deadline has been completed.
     */
    public Deadline(Deadline deadline, boolean isDone) {
        super(deadline, isDone);
        this.byWhen = deadline.byWhen;
    }

    /**
     * Returns a copy of this deadline, but with the specified completion status instead.
     *
     * @param isDoneNew New completion status for this deadline.
     * @return Copy of this deadline, with the chosen completion status.
     */
    Deadline changeTaskStatus(boolean isDoneNew) {
        return new Deadline(this, isDoneNew);
    }

    /**
     * Returns a <code>String</code> that represents this deadline.
     * This <code>String</code> can be saved to the task storage.
     *
     * @return <code>String</code> representation of this deadline.
     */
    @Override
    public String getSavableString() {
        return TASK_ICON
                + DIVIDER + super.getSavableString()
                + DIVIDER + super.getSavableDate(this.byWhen);
    }

    @Override
    public String toString() {
        return super.toString()
                + " (by: " + super.getOutputDate(this.byWhen) + ")";
    }
}
