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
                String taskString = fileScanner.nextLine();
                String[] taskDetails = taskString.split(" \\| ");

                // get task details
                String taskType = taskDetails[0];
                String isDoneIcon = taskDetails[1];
                if (!isDoneIcon.equals(Task.DONE_ICON) && !isDoneIcon.equals(Task.NOT_DONE_ICON)) {
                    throw new IllegalArgumentException("Invalid progress icons in saved task file");
                }
                boolean isDone = isDoneIcon.equals(Task.DONE_ICON);
                String taskName = taskDetails[2];

                // create new task object
                Task newTask;
                switch (taskType) {
                case Todo.TASK_ICON:
                    Todo todo = new Todo(taskName);
                    newTask = new Todo(todo, isDone);
                    break;
                case Deadline.TASK_ICON:
                    LocalDateTime byWhen = Parser.parseDate(taskDetails[3]);
                    Deadline deadline = new Deadline(taskName, byWhen);
                    newTask = new Deadline(deadline, isDone);
                    break;
                case Event.TASK_ICON:
                    // task is an event
                    LocalDateTime startTime = Parser.parseDate(taskDetails[3]);
                    LocalDateTime endTime = Parser.parseDate(taskDetails[4]);
                    Event event = new Event(taskName, startTime, endTime);
                    newTask = new Event(event, isDone);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid task icons in saved task file");
                }

                // add this new task object
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
