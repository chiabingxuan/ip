import java.time.format.DateTimeFormatter;

class AddCommand extends Command {
    private final Task task;
    private final DateTimeFormatter dateFormatter;

    AddCommand(Task task, String dateFormat) {
        super(false);
        this.task = task;
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        // add to tracker
        taskTracker = taskTracker.addTask(task);
        int newNumOfTasks = taskTracker.getNumOfTasks();

        // print in display
        ui.printAddTaskMessage(task, newNumOfTasks);

        // update storage
        try {
            storage.saveTasks(taskTracker.getCombinedSaveableTasks(this.dateFormatter));
        } catch (BingBongException ex) {
            ui.printWarning(ex.getMessage());
        }

        return taskTracker;
    }
}
