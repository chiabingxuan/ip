package bingbong.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import bingbong.task.Deadline;
import bingbong.task.Event;
import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.task.Todo;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

/**
 * Manages the storage of existing task lists in the disk.
 * Loads the task list that has been saved in previous runs, if any.
 * Saves task list to the disk, if updates have occurred.
 */
public class Storage {
    private final String filePath;

    /**
     * Initialises a Storage class that points to the pre-saved
     * task file (if any).
     *
     * @param dataFolderPath Path to the folder in which tasks are saved.
     * @param filename       Name of the file in which tasks are saved. The
     *                       file is stored in <code>dataFolderPath</code>.
     */
    public Storage(String dataFolderPath, String filename) throws IOException {
        this.filePath = dataFolderPath + "/" + filename;

        // create data folder if we have not done so
        Path dataFolderPathObj = Paths.get(dataFolderPath);
        if (!Files.exists(dataFolderPathObj)) {
            Files.createDirectories(dataFolderPathObj);
        }
    }

    /**
     * Reads the next line from the saved task file and splits it
     * into individual task details using the standard delimiter. Returns
     * the resultant array.
     *
     * @param fileScanner <code>Scanner</code> used to read from the saved task file.
     * @return Array of task detail tokens extracted from the line.
     */
    private String[] getTaskDetails(Scanner fileScanner) {
        String taskString = fileScanner.nextLine();
        return taskString.split(" \\| ");
    }

    /**
     * Extracts and returns the task type icon from the parsed task details.
     *
     * @param taskDetails Tokenised representation of a saved task.
     * @return <code>String</code> representing the task type icon.
     */
    private String getTaskType(String[] taskDetails) {
        return taskDetails[0];
    }

    /**
     * Determines and returns whether a task is marked as completed, based on
     * its saved progress icon.
     *
     * @param taskDetails Tokenised representation of a saved task.
     * @return <code>true</code> if the task is completed, <code>false</code> otherwise.
     * @throws IllegalArgumentException If the progress icon is invalid.
     */
    private boolean getIsDone(String[] taskDetails) {
        String isDoneIcon = taskDetails[1];
        if (!isDoneIcon.equals(Task.DONE_ICON) && !isDoneIcon.equals(Task.NOT_DONE_ICON)) {
            throw new IllegalArgumentException("Invalid progress icons in saved task file");
        }
        return isDoneIcon.equals(Task.DONE_ICON);
    }

    /**
     * Extracts and returns the task name from the parsed task details.
     *
     * @param taskDetails Tokenised representation of a saved task.
     * @return Name of the task.
     */
    private String getTaskName(String[] taskDetails) {
        return taskDetails[2];
    }

    /**
     * Creates and returns a todo from stored data.
     *
     * @param taskName Name of the todo.
     * @param isDone Whether the todo is marked as completed.
     * @return A reconstructed todo.
     */
    private Task createTodo(String taskName, boolean isDone) {
        Todo todo = new Todo(taskName);
        return new Todo(todo, isDone);
    }

    /**
     * Creates and returns a deadline from stored data.
     *
     * @param taskDetails Array containing task information read from storage.
     * @param taskName Name of the deadline.
     * @param isDone Whether the deadline is marked as completed.
     * @return A reconstructed deadline.
     * @throws ParserException If the stored deadline date cannot be parsed.
     */
    private Task createDeadline(String[] taskDetails, String taskName, boolean isDone)
            throws ParserException {
        LocalDateTime byWhen = Parser.parseDate(taskDetails[3]);
        Deadline deadline = new Deadline(taskName, byWhen);
        return new Deadline(deadline, isDone);
    }

    /**
     * Creates and returns an event from stored data.
     *
     * @param taskDetails Array containing task information read from storage.
     * @param taskName Name of the event.
     * @param isDone Whether the event is marked as completed.
     * @return A reconstructed event.
     * @throws ParserException If the stored event dates cannot be parsed.
     */
    private Task createEvent(String[] taskDetails, String taskName, boolean isDone)
            throws ParserException {
        LocalDateTime startTime = Parser.parseDate(taskDetails[3]);
        LocalDateTime endTime = Parser.parseDate(taskDetails[4]);
        Event event = new Event(taskName, startTime, endTime);
        return new Event(event, isDone);
    }

    /**
     * Creates a <code>Task</code> object from the parsed task details.
     * The task type is determined using the saved task icon, and
     * additional datetime fields are parsed where required.
     *
     * @param taskDetails Tokenised representation of a saved task.
     * @return Newly constructed <code>Task</code> instance.
     * @throws ParserException If date parsing fails.
     * @throws IllegalArgumentException If the task type is invalid.
     */
    private Task createTask(String[] taskDetails) throws ParserException {
        // get separate info on the task
        String taskType = this.getTaskType(taskDetails);
        boolean isDone = this.getIsDone(taskDetails);
        String taskName = this.getTaskName(taskDetails);

        // create and return new task object
        Task newTask;
        switch (taskType) {
        case Todo.TASK_ICON:
            newTask = this.createTodo(taskName, isDone);
            break;
        case Deadline.TASK_ICON:
            newTask = this.createDeadline(taskDetails, taskName, isDone);
            break;
        case Event.TASK_ICON:
            newTask = this.createEvent(taskDetails, taskName, isDone);
            break;
        default:
            throw new IllegalArgumentException("Invalid task icons in saved task file");
        }

        return newTask;
    }

    /**
     * Returns a <code>TaskTracker</code> object containing
     * a list of loaded tasks from the disk.
     *
     * @return <code>TaskTracker</code> object with a list of existing tasks from previous runs.
     * @throws FileNotFoundException If there is no existing file
     *                               containing a list of pre-saved tasks.
     * @throws StorageException      If the task file is incorrectly formatted
     *                               or corrupted.
     */
    public TaskTracker loadSavedTasks() throws FileNotFoundException, StorageException {
        try {
            File f = new File(this.filePath);
            Scanner fileScanner = new Scanner(f);

            ArrayList<Task> loadedTasks = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String[] taskDetails = this.getTaskDetails(fileScanner);
                Task newTask = this.createTask(taskDetails);
                loadedTasks.add(newTask);
            }

            return new TaskTracker(loadedTasks);
        } catch (ArrayIndexOutOfBoundsException | ParserException | IllegalArgumentException ex) {
            throw new StorageException("Something went wrong loading the saved task file: "
                    + ex.getMessage()
                    + "\nThe file might be corrupted (ie. wrongly formatted)."
                    + "\nAn empty task list will be initialised.");
        }
    }

    /**
     * Writes a list of tasks (provided in concatenated <code>String</code>
     * format) to the disk.
     *
     * @param textToWrite Concatenated <code>String</code> representing the current list
     *                    of tasks recorded.
     * @throws StorageException If the task file cannot be saved due to an
     *                          <code>IOException</code> being thrown.
     */
    public void saveTasks(String textToWrite) throws StorageException {
        try {
            // write to file
            FileWriter fw = new FileWriter(this.filePath);
            fw.write(textToWrite);
            fw.close();
        } catch (IOException ex) {
            throw new StorageException("Something went wrong when saving the tasks: "
                    + ex.getMessage()
                    + "\nHence, the tasks are not being saved to disk.");
        }
    }
}
