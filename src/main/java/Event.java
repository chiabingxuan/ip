import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Event extends Task {
    static final String TASK_ICON = "E";
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    Event(Event event, boolean isDone) {
        super(event, isDone);
        this.startTime = event.startTime;
        this.endTime = event.endTime;
    }

    Event changeTaskStatus(boolean newStatus) {
        return new Event(this, newStatus);
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
