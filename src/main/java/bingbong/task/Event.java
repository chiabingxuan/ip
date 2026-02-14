package bingbong.task;

import java.time.LocalDateTime;

/**
 * Represents an event that can be recorded in the chatbot. Contains
 * details of the event, including the event name, the completion status of the
 * event, the start time of the event, and the end time of the event.
 */
public class Event extends Task {
    public static final String TASK_ICON = "E";
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Initialises an incomplete event with the specified name and date.
     *
     * @param taskName  Name of the event.
     * @param startTime Time at which the event starts.
     * @param endTime   Time at which the event ends.
     */
    public Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName, TASK_ICON);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Initialises a copy of the chosen event with the specified completion status.
     *
     * @param event  Chosen event.
     * @param isDone Whether the event has been completed.
     */
    public Event(Event event, boolean isDone) {
        super(event, isDone);
        this.startTime = event.startTime;
        this.endTime = event.endTime;
    }

    /**
     * Returns a copy of this event, but with the specified completion status instead.
     *
     * @param isDoneNew New completion status for this event.
     * @return Copy of this event, with the chosen completion status.
     */
    Event changeTaskStatus(boolean isDoneNew) {
        return new Event(this, isDoneNew);
    }

    /**
     * Returns whether the event will start within the
     * selected number of days from the specified date.
     *
     * @param windowStartDate The start of the time window to check.
     * @param daysFromWindowStartDate Number of days from <code>windowStartDate</code>,
     *                                defining the future time window for which
     *                                events should be flagged.
     * @return Whether the event will start soon.
     */
    boolean isHappeningSoon(LocalDateTime windowStartDate, int daysFromWindowStartDate) {
        LocalDateTime windowEndDate = windowStartDate.plusDays(daysFromWindowStartDate);
        return this.startTime.isAfter(windowStartDate) && this.startTime.isBefore(windowEndDate);
    }

    /**
     * Returns a <code>String</code> that represents this event.
     * This <code>String</code> can be saved to the task storage.
     *
     * @return <code>String</code> representation of this event.
     */
    @Override
    public String getSavableString() {
        return TASK_ICON
                + DIVIDER + super.getSavableString()
                + DIVIDER + super.getSavableDate(this.startTime)
                + DIVIDER + super.getSavableDate(this.endTime);
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + super.getOutputDate(this.startTime)
                + " to: " + super.getOutputDate(this.endTime) + ")";
    }
}
