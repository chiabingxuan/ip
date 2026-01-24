abstract class Command {
    private final boolean isExit;

    Command(boolean isExit) {
        this.isExit = isExit;
    }

    abstract TaskTracker execute(TaskTracker taskTracker, Ui ui, Storage storage) throws BingBongException;

    boolean isExit() {
        return this.isExit;
    }
}
