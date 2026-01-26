package bingbong.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public static final String TASK_ICON = "D";
    private final LocalDateTime byWhen;

    public Deadline(String taskName, LocalDateTime byWhen) {
        super(taskName);
        this.byWhen = byWhen;
    }

    public Deadline(Deadline deadline, boolean isDone) {
        super(deadline, isDone);
        this.byWhen = deadline.byWhen;
    }

    Deadline changeTaskStatus(boolean isDoneNew) {
        return new Deadline(this, isDoneNew);
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
