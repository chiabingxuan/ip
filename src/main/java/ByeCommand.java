class ByeCommand extends Command {
    ByeCommand() {
        super(true);
    }

    TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) {
        ui.sayGoodbye();
        return taskTracker;
    }
}