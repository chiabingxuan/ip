package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        super(false);
        this.index = index;
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage)
            throws BingBongException {
        Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(this.index, false);
        taskTracker = taskTracker.editTask(this.index, unmarkedTask);

        ui.printUnmarkedTaskMessage(unmarkedTask);

        return taskTracker;
    }
}
