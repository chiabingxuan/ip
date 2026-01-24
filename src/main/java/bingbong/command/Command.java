package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.Storage;
import bingbong.util.Ui;

public abstract class Command {
    private final boolean isExit;

    Command(boolean isExit) {
        this.isExit = isExit;
    }

    abstract public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage)
            throws BingBongException;

    public boolean isExit() {
        return this.isExit;
    }
}
