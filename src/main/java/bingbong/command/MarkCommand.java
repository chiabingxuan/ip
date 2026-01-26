package bingbong.command;

import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        super(false);
        this.index = index;
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage)
            throws BingBongException {
        Task markedTask = taskTracker.changeTaskStatusAtIndex(this.index, true);
        taskTracker = taskTracker.editTask(this.index, markedTask);

        ui.printMarkedTaskMessage(markedTask);

        return taskTracker;
    }

    @Override
    public String toString() {
        return "mark command: " + this.index;
    }
}
