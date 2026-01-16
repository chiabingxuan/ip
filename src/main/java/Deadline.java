class Deadline extends Task {
    private static final String TASK_ICON = "D";
    private final String byWhen;

    Deadline(String taskName, String byWhen) {
        super(taskName);
        this.byWhen = byWhen;
    }

    private Deadline(Deadline deadline, boolean isDone) {
        super(deadline, isDone);
        this.byWhen = deadline.byWhen;
    }

    Deadline changeTaskStatus(boolean newStatus) {
        return new Deadline(this, newStatus);
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]"
                + super.toString()
                + " (by: " + this.byWhen + ")";
    }
}
