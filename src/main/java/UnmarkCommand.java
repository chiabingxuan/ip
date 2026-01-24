class UnmarkCommand extends Command {
    private final int index;

    UnmarkCommand(int index) {
        super(false);
        this.index = index;
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) throws BingBongException {
        Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(this.index, false);
        taskTracker = taskTracker.editTask(this.index, unmarkedTask);

        ui.printUnmarkedTaskMessage(unmarkedTask);

        return taskTracker;
    }
}
