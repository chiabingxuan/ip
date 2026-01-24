package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class ByeCommand extends Command {
    public ByeCommand() {
        super(true);
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        ui.sayGoodbye();
        return taskTracker;
    }
}