package bingbong;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import bingbong.command.Command;
import bingbong.message.ErrorMessage;
import bingbong.message.Message;
import bingbong.message.WarningMessage;
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
    private Optional<Message> loadedMessage;

    // tools for the bot
    private Storage storage;
    private TaskTracker taskTracker;

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
        } catch (FileNotFoundException ex) {
            WarningMessage noFileWarningMsg = new WarningMessage("There is no "
                    + "task file named "
                    + tasksFilename
                    + " in "
                    + dataFolderPath
                    + ". An empty task list will be initialised.");
            this.loadedMessage = Optional.of(noFileWarningMsg);
            this.taskTracker = new TaskTracker();
        } catch (StorageException ex) {
            // if existing file is incorrectly formatted or corrupted
            WarningMessage fileCorruptedWarningMsg = new WarningMessage(ex.getMessage());
            this.loadedMessage = Optional.of(fileCorruptedWarningMsg);
            this.taskTracker = new TaskTracker();
        } catch (IOException ex) {
            // cannot even initialise storage correctly
            ErrorMessage storageInitErrorMsg = new ErrorMessage(MessageFormatter
                    .getExceptionMessage("Something went wrong when "
                            + "initialising task storage: "
                            + ex.getMessage()
                            + "\nPlease fix the problem and try again."));
            this.loadedMessage = Optional.of(storageInitErrorMsg);
        }
    }

    /**
     * Returns the message obtained from the chatbot after it has been loaded,
     * if any. If there is no such message, <code>Optional.empty</code>
     * is returned.
     *
     * @return The message obtained from the chatbot after it has been loaded, if any.
     */
    public Optional<Message> getLoadedMessage() {
        assert this.loadedMessage != null : "Loaded message has not been initialised";
        return this.loadedMessage;
    }

    /**
     * Processes the given user input and subsequently returns a list of
     * messages from the chatbot's output.
     *
     * @return The list of messages from the chatbot's output.
     */
    public List<Message> getResponses(String inputLine) {
        try {
            assert this.storage != null : "Storage has not been initialised";
            assert this.taskTracker != null : "Task list has not been initialised";
            Command commandParsed = Parser.parse(inputLine);
            this.taskTracker = commandParsed.execute(this.taskTracker, storage);
            return commandParsed.getOutputMessages();
        } catch (BingBongException ex) {
            ErrorMessage errorMsg = new ErrorMessage(MessageFormatter.getExceptionMessage(ex.getMessage()));
            return List.of(errorMsg);
        }
    }
}
