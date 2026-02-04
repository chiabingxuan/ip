package bingbong;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import bingbong.command.Command;
import bingbong.task.TaskTracker;
import bingbong.util.BingBongException;
import bingbong.util.MessageFormatter;
import bingbong.util.Parser;
import bingbong.util.Storage;
import bingbong.util.StorageException;

/**
 * Initialises the task list storage, current task
 * list and user interface, before running the chatbot application.
 */
public class BingBong {
    // describe loading status
    private Optional<String> loadedMessage;
    private boolean isInitSuccessful;

    // tools for the bot
    private Storage storage;
    private TaskTracker taskTracker;

    // tracks the command parsed. if
    // it is not a known command, this is empty
    private Optional<Command> chosenCommand = Optional.empty();

    /**
     * Initialises the <code>BingBong</code> class for the running of the chatbot.
     *
     * @param dataFolderPath Path to the folder in which tasks are saved.
     * @param tasksFilename  Name of the file in which tasks are saved. The
     *                       file is stored in <code>dataFolderPath</code>.
     */
    public BingBong(String dataFolderPath, String tasksFilename) {
        try {
            this.loadedMessage = Optional.empty();
            this.storage = new Storage(dataFolderPath, tasksFilename);
            this.taskTracker = storage.loadSavedTasks();
            this.isInitSuccessful = true;
        } catch (FileNotFoundException ex) {
            this.loadedMessage = Optional.of("No existing task file detected. "
                    + "An empty task list will be initialised.");
            this.taskTracker = new TaskTracker();
            this.isInitSuccessful = true;
        } catch (StorageException ex) {
            // if existing file is incorrectly formatted or corrupted
            this.loadedMessage = Optional.of(ex.getMessage());
            this.taskTracker = new TaskTracker();
            this.isInitSuccessful = true;
        } catch (IOException ex) {
            // cannot even initialise storage correctly
            this.loadedMessage = Optional.of(MessageFormatter
                    .getExceptionMessage("Something went wrong when "
                            + "initialising task storage: "
                            + ex.getMessage()
                            + "\nPlease fix the problem and try again."));
            this.isInitSuccessful = false;
        }
    }

    public Optional<String> getLoadedMessage() {
        return this.loadedMessage;
    }

    public Optional<Command> getAndResetChosenCommand() {
        Optional<Command> chosenCommand = this.chosenCommand;
        this.chosenCommand = Optional.empty(); // clear the tracking of the command
        return chosenCommand;
    }

    public String getResponse(String inputLine) {
        try {
            Command commandParsed = Parser.parse(inputLine);
            this.chosenCommand = Optional.of(commandParsed);
            taskTracker = commandParsed.execute(taskTracker, storage);
            return commandParsed.getString();
        } catch (BingBongException ex) {
            return MessageFormatter.getExceptionMessage(ex.getMessage());
        }
    }
}
