package bingbong.util;

import bingbong.task.Deadline;
import bingbong.task.Event;
import bingbong.task.Task;
import bingbong.task.TaskTracker;
import bingbong.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String dataFolderPath, String filename) throws IOException {
        this.filePath = dataFolderPath + "/" + filename;

        // create data folder if we have not done so
        Path dataFolderPathObj = Paths.get(dataFolderPath);
        if (!Files.exists(dataFolderPathObj)) {
            Files.createDirectories(dataFolderPathObj);
        }
    }

    // read saved task file
    public TaskTracker loadSavedTasks() throws FileNotFoundException, BingBongException {
        try {
            File f = new File(this.filePath);
            Scanner fileScanner = new Scanner(f);

            TaskTracker taskTracker = new TaskTracker();

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
                taskTracker.addTask(newTask);
            }

            return taskTracker;
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException | IllegalArgumentException ex) {
            throw new BingBongException("Something went wrong loading the saved task file: "
                    + ex.getMessage()
                    + "\n The file might be corrupted (ie. wrongly formatted)."
                    + "\nAn empty task list will be initialised.");
        }
    }

    // save recorded tasks to file
    public void saveTasks(String textToWrite) throws BingBongException {
        try {
            // write to file
            FileWriter fw = new FileWriter(this.filePath);
            fw.write(textToWrite);
            fw.close();
        } catch (IOException ex) {
            throw new BingBongException("Something went wrong when saving the tasks: "
                    + ex.getMessage()
                    + "\nHence, the tasks are not being saved to disk.");
        }
    }
}
