package bingbong.command;

import bingbong.task.TaskTracker;
import bingbong.util.Storage;
import bingbong.util.Ui;

public class FindCommand extends Command {
    private final String substring;

    public FindCommand(String substring) {
        super(false);
        this.substring = substring;
    }

    public TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        String listOfTasks = taskTracker.findTasks(this.substring);
        ui.printMatchingTasksMessage(listOfTasks);
        return taskTracker;
    }

    @Override
    public String toString() {
        return "find command: " + substring;
    }
}