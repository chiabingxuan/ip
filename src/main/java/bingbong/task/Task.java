package bingbong.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    public static final String DONE_ICON = "X";
    public static final String NOT_DONE_ICON = " ";
    protected static final String DIVIDER = " | ";
    private static final String DATE_OUTPUT_FORMAT = "d MMM yyyy, h:mm a";

    private final String taskName;
    private final boolean isDone;

    Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    protected Task(Task task, boolean isDone) {
        this.taskName = task.taskName;
        this.isDone = isDone;
    }

    private String getStatusIcon() {
        return this.isDone ? DONE_ICON : NOT_DONE_ICON;
    }

    abstract Task changeTaskStatus(boolean isDoneNew);

    protected String outputDate(LocalDateTime datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_OUTPUT_FORMAT);
        return datetime.format(formatter);
    }

    String getSaveableString(DateTimeFormatter dateFormatter) {
        return this.getStatusIcon() + DIVIDER + this.taskName;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.taskName;
    }
}
