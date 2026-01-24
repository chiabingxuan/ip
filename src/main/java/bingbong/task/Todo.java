package bingbong.task;

import java.time.format.DateTimeFormatter;

public class Todo extends Task {
    public static final String TASK_ICON = "T";

    public Todo(String taskName) {
        super(taskName);
    }

    public Todo(Todo todo, boolean isDone) {
        super(todo, isDone);
    }

    Todo changeTaskStatus(boolean newStatus) {
        return new Todo(this, newStatus);
    }

    @Override
    public String getSaveableString(DateTimeFormatter dateFormatter) {
        return TASK_ICON
                + DIVIDER + super.getSaveableString(dateFormatter);
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]" + super.toString();
    }
}
