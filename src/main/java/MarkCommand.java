class MarkCommand extends Command {
    private final int index;

    MarkCommand(int index) {
        super(false);
        this.index = index;
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) throws BingBongException {
        Task markedTask = taskTracker.changeTaskStatusAtIndex(this.index, true);
        taskTracker = taskTracker.editTask(this.index, markedTask);

        ui.printMarkedTaskMessage(markedTask);

        return taskTracker;
    }
}