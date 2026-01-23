class Todo extends Task {
    static final String TASK_ICON = "T";

    Todo(String taskName) {
        super(taskName);
    }

    Todo(Todo todo, boolean isDone) {
        super(todo, isDone);
    }

    Todo changeTaskStatus(boolean newStatus) {
        return new Todo(this, newStatus);
    }

    @Override
    public String getSaveableString() {
        return TASK_ICON
                + DIVIDER + super.getSaveableString();
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]" + super.toString();
    }
}
