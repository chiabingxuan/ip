package bingbong.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public static final String TASK_ICON = "E";
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(Event event, boolean isDone) {
        super(event, isDone);
        this.startTime = event.startTime;
        this.endTime = event.endTime;
    }

    Event changeTaskStatus(boolean isDoneNew) {
        return new Event(this, isDoneNew);
    }

    @Override
    public String getSaveableString(DateTimeFormatter dateFormatter) {
        return TASK_ICON
                + DIVIDER + super.getSaveableString(dateFormatter)
                + DIVIDER + this.startTime.format(dateFormatter)
                + DIVIDER + this.endTime.format(dateFormatter);
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]"
                + super.toString()
                + " (from: " + super.outputDate(this.startTime)
                + " to: " + super.outputDate(this.endTime) + ")";
    }
}
