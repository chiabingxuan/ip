class ListCommand extends Command {
    ListCommand() {
        super(false);
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        String listOfTasks = taskTracker.listTasks();
        ui.printListTasksMessage(listOfTasks);
        return taskTracker;
    }
}
