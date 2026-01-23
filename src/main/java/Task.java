abstract class Task {
    static final String DONE_ICON = "X";
    static final String NOT_DONE_ICON = " ";
    protected static final String DIVIDER = " | ";

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

    abstract Task changeTaskStatus(boolean newStatus);

    public String getSaveableString() {
        return this.getStatusIcon() + DIVIDER + this.taskName;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.taskName;
    }
}
