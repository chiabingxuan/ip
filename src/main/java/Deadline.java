import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Deadline extends Task {
    static final String TASK_ICON = "D";
    private final LocalDateTime byWhen;

    Deadline(String taskName, LocalDateTime byWhen) {
        super(taskName);
        this.byWhen = byWhen;
    }

    Deadline(Deadline deadline, boolean isDone) {
        super(deadline, isDone);
        this.byWhen = deadline.byWhen;
    }

    Deadline changeTaskStatus(boolean newStatus) {
        return new Deadline(this, newStatus);
    }

    @Override
    public String getSaveableString(DateTimeFormatter dateFormatter) {
        return TASK_ICON
                + DIVIDER + super.getSaveableString(dateFormatter)
                + DIVIDER + this.byWhen.format(dateFormatter);
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]"
                + super.toString()
                + " (by: " + super.outputDate(this.byWhen) + ")";
    }
}
