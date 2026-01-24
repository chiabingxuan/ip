class DeleteCommand extends Command {
    private final int index;

    DeleteCommand(int index) {
        super(false);
        this.index = index;
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) throws BingBongException {
        Task taskToDelete = taskTracker.getTask(this.index);
        taskTracker = taskTracker.deleteTask(this.index);
        int newNumOfTasks = taskTracker.getNumOfTasks();

        ui.printDeletedTaskMessage(taskToDelete, newNumOfTasks);

        return taskTracker;
    }
}