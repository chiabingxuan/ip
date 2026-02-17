package bingbong.task;

import java.time.LocalDateTime;

/**
 * Represents a todo that can be recorded in the chatbot. Contains
 * details of the todo, including the todo name and the completion status of the
 * todo.
 */
public class Todo extends Task {
    public static final String TASK_ICON = "T";

    /**
     * Initialises an incomplete todo with the specified name.
     *
     * @param taskName Name of the todo.
     */
    public Todo(String taskName) {
        super(taskName, TASK_ICON);
    }

    /**
     * Initialises a copy of the chosen todo with the specified completion status.
     *
     * @param todo   Chosen todo.
     * @param isDone Whether the todo has been completed.
     */
    public Todo(Todo todo, boolean isDone) {
        super(todo, isDone);
    }

    /**
     * Returns a copy of this todo, but with the specified completion status instead.
     *
     * @param isDoneNew New completion status for this todo.
     * @return Copy of this todo, with the chosen completion status.
     */
    Todo changeTaskStatus(boolean isDoneNew) {
        return new Todo(this, isDoneNew);
    }

    /**
     * Returns whether the todo will be occurring within the
     * selected number of days from the specified date.
     *
     * @param windowStartDate The start of the time window to check.
     * @param daysFromWindowStartDate Number of days from<code>windowStartDate</code>,
     *                                defining the future time window for which todos
     *                                should be flagged.
     * @return Whether the todo will be occurring soon.
     */
    boolean isHappeningSoon(LocalDateTime windowStartDate, int daysFromWindowStartDate) {
        // todos will never have a due date
        return false;
    }

    /**
     * Returns a <code>String</code> that represents this todo.
     * This <code>String</code> can be saved to the task storage.
     *
     * @return <code>String</code> representation of this todo.
     */
    @Override
    public String getSavableString() {
        return TASK_ICON
                + DIVIDER + super.getSavableString();
    }
}
