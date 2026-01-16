class Todo extends Task {
    private static final String TASK_ICON = "T";

    Todo(String taskName) {
        super(taskName);
    }

    private Todo(Todo todo, boolean isDone) {
        super(todo, isDone);
    }

    Todo changeTaskStatus(boolean newStatus) {
        return new Todo(this, newStatus);
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]" + super.toString();
    }
}
