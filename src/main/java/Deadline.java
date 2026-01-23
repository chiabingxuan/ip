class Deadline extends Task {
    static final String TASK_ICON = "D";
    private final String byWhen;

    Deadline(String taskName, String byWhen) {
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
    public String getSaveableString() {
        return TASK_ICON
                + DIVIDER + super.getSaveableString()
                + DIVIDER + this.byWhen;
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]"
                + super.toString()
                + " (by: " + this.byWhen + ")";
    }
}
