class Event extends Task {
    static final String TASK_ICON = "E";
    private final String startTime;
    private final String endTime;

    Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    Event(Event event, boolean isDone) {
        super(event, isDone);
        this.startTime = event.startTime;
        this.endTime = event.endTime;
    }

    Event changeTaskStatus(boolean newStatus) {
        return new Event(this, newStatus);
    }

    @Override
    public String getSaveableString() {
        return TASK_ICON
                + DIVIDER + super.getSaveableString()
                + DIVIDER + this.startTime
                + DIVIDER + this.endTime;
    }

    @Override
    public String toString() {
        return "[" + TASK_ICON + "]"
                + super.toString()
                + " (from: " + this.startTime
                + " to: " + this.endTime + ")";
    }
}
