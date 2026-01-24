package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class ListCommand extends Command {
    public ListCommand() {
        super(false);
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        String listOfTasks = taskTracker.listTasks();
        ui.printListTasksMessage(listOfTasks);
        return taskTracker;
    }
}
