package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        super(false);
        this.index = index;
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) throws BingBongException {
        Task taskToDelete = taskTracker.getTask(this.index);
        taskTracker = taskTracker.deleteTask(this.index);
        int newNumOfTasks = taskTracker.getNumOfTasks();

        ui.printDeletedTaskMessage(taskToDelete, newNumOfTasks);

        return taskTracker;
    }

    @Override
    public String toString() {
        return "delete command: " + this.index;
    }
}
