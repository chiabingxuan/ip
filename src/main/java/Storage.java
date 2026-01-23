import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

class Storage {
    private final String filePath;
    private final DateTimeFormatter dateFormatter;

    Storage(String dataFolderPath, String filename, String dateFormat) throws IOException {
        this.filePath = dataFolderPath + "/" + filename;
        this.dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

        // create data folder if we have not done so
        Path dataFolderPathObj = Paths.get(dataFolderPath);
        if (!Files.exists(dataFolderPathObj)) {
            Files.createDirectories(dataFolderPathObj);
        }
    }

    // parse dates that are in String
    private LocalDateTime parseDate(String dateString) {
        return LocalDateTime.parse(dateString, this.dateFormatter);
    }

    // read saved task file
    TaskTracker readSavedTasks(File f) throws FileNotFoundException, BingBongException {
        try {
            Scanner fileScanner = new Scanner(f);
            TaskTracker taskTracker = new TaskTracker(this.filePath);

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
                    LocalDateTime byWhen = parseDate(taskDetails[3]);
                    Deadline deadline = new Deadline(taskName, byWhen);
                    newTask = new Deadline(deadline, isDone);
                    break;
                case Event.TASK_ICON:
                    // task is an event
                    LocalDateTime startTime = parseDate(taskDetails[3]);
                    LocalDateTime endTime = parseDate(taskDetails[4]);
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

    // load saved tasks
    TaskTracker loadSavedTasks() throws FileNotFoundException, BingBongException {
        // populate tracker with saved tasks
        File f = new File(filePath);
        return readSavedTasks(f);
    }

    // save recorded tasks to file
    void saveTasks(String textToWrite) throws BingBongException {
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
