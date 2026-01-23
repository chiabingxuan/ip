import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDateTime;

public class BingBong {
    // bot properties
    private static final String BOT_NAME = "BingBong";

    // examples to be shown in error messages
    private static final String MARK_EXAMPLE = "\"mark 1\" to mark the first task as completed";
    private static final String UNMARK_EXAMPLE = "\"unmark 1\" to mark the first task as incomplete";
    private static final String DELETE_EXAMPLE = "\"delete 1\" to delete the first task";
    private static final String TODO_EXAMPLE = "\"todo go grocery shopping\"";
    private static final String DEADLINE_EXAMPLE = "\"deadline finish homework /by 9pm\"";
    private static final String EVENT_EXAMPLE = "\"event go for a jog /from 9am /to 10am\"";
    private static final String DATE_FORMATTING_EXAMPLE = "\"2/1/2003 13:18\" which means "
            + "2 Jan 2003, 1:18 pm";

    // for parsing dates
    private static final String DATE_FORMAT = "d/M/yyyy HH:mm";

    // tools given to the bot
    private Storage storage;
    private TaskTracker taskTracker;
    private HashMap<Command,
            ThrowingBiFunction<TaskTracker, String, TaskTracker>> commandsToOperations;

    // messages to print out
    private static String getStartMessage() {
        return "Yo, my name is "
                + BOT_NAME
                + ". Hit me up if you need any help.";
    }

    private static String getListTasksMessage(String listOfTasks) {
        return "Here are the tasks that you have added to the list:"
                + "\n"
                + listOfTasks;
    }

    private static String getAddTaskMessage(Task task, int numOfTasks) {
        return "You're getting busier. I've added this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.";
    }

    private static String getMarkedTaskMessage(Task task) {
        return "Congratulations on being hardworking for once! This task has been marked as done:"
                + "\n"
                + task;
    }

    private static String getUnmarkedTaskMessage(Task task) {
        return "This task has been marked as incomplete:"
                + "\n"
                + task;
    }

    private static String getDeletedTaskMessage(Task task, int numOfTasks) {
        return "Alright, I've gotten rid of this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.";
    }

    private static String getExceptionMessage(String msg) {
        return "SORRY... :("
                + "\n"
                + msg;
    }

    private static String getEndMessage() {
        return "Hasta la vista, baby!";
    }

    // parse dates that are in String
    private static LocalDateTime parseDate(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    // populate mapping of commands to the ThrowingBiFunction to be called
    private static HashMap<Command,
            ThrowingBiFunction<TaskTracker, String, TaskTracker>> init_commands_to_operations() {
        HashMap<Command, ThrowingBiFunction<TaskTracker, String, TaskTracker>>
                commandsToOperations = new HashMap<>();

        // mark chosen task done
        commandsToOperations.put(Command.MARK, (taskTracker, inputLine) -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToMark = Integer.parseInt(inputTokens[1]) - 1;
                Task markedTask = taskTracker.changeTaskStatusAtIndex(indexToMark, true);
                taskTracker = taskTracker.editTask(indexToMark, markedTask);
                System.out.println(new Message(getMarkedTaskMessage(markedTask)));
                return taskTracker;
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"mark\" command."
                        + "\nEg. "
                        + MARK_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"mark\" command."
                        + "\nEg. "
                        + MARK_EXAMPLE);
            }
        });

        // mark chosen task as not done
        commandsToOperations.put(Command.UNMARK, (taskTracker, inputLine) -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToUnmark = Integer.parseInt(inputTokens[1]) - 1;
                Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(indexToUnmark, false);
                taskTracker = taskTracker.editTask(indexToUnmark, unmarkedTask);
                System.out.println(new Message(getUnmarkedTaskMessage(unmarkedTask)));
                return taskTracker;
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"unmark\" command."
                        + "\nEg. "
                        + UNMARK_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"unmark\" command."
                        + "\nEg. "
                        + UNMARK_EXAMPLE);
            }
        });

        // delete chosen task
        commandsToOperations.put(Command.DELETE, (taskTracker, inputLine) -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToDelete = Integer.parseInt(inputTokens[1]) - 1;
                Task taskToDelete = taskTracker.getTask(indexToDelete);
                taskTracker = taskTracker.deleteTask(indexToDelete);
                int newNumOfTasks = taskTracker.getNumOfTasks();
                System.out.println(new Message(getDeletedTaskMessage(taskToDelete, newNumOfTasks)));
                return taskTracker;
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"delete\" command."
                        + "\nEg. "
                        + DELETE_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"delete\" command."
                        + "\nEg. "
                        + DELETE_EXAMPLE);
            }
        });

        // add a todo
        commandsToOperations.put(Command.TODO, (taskTracker, inputLine) -> {
            String[] detailsAfterSplit = inputLine.split("todo\\s+", 2);

            if (detailsAfterSplit.length < 2) {
                throw new BingBongException("The description of a todo cannot be empty. "
                        + "Add a task name after the \"todo\" command."
                        + "\nEg. "
                        + TODO_EXAMPLE);
            }

            String todoName = detailsAfterSplit[1];
            Todo newTodo = new Todo(todoName);

            taskTracker = taskTracker.addTask(newTodo);
            int newNumOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newTodo, newNumOfTasks)));
            return taskTracker;
        });

        // add a deadline
        commandsToOperations.put(Command.DEADLINE, (taskTracker, inputLine) -> {
            String[] detailsAfterSplittingCommand = inputLine.split("deadline\\s+", 2);
            if (detailsAfterSplittingCommand.length < 2) {
                throw new BingBongException("The description of a deadline cannot be empty. "
                        + "Add a task name after the \"deadline\" command."
                        + "\nEg. "
                        + DEADLINE_EXAMPLE);
            }

            String[] deadlineDetails = detailsAfterSplittingCommand[1]
                    .split("\\s+/by\\s+", 2);
            if (deadlineDetails.length < 2) {
                throw new BingBongException("For deadlines, the \"/by\" delimiter "
                        + "must be placed between the task description and the chosen date."
                        + "\nEg. "
                        + DEADLINE_EXAMPLE);
            }

            String deadlineName = deadlineDetails[0];
            LocalDateTime byWhen = parseDate(deadlineDetails[1]);
            Deadline newDeadline = new Deadline(deadlineName, byWhen);

            taskTracker = taskTracker.addTask(newDeadline);
            int newNumOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newDeadline, newNumOfTasks)));
            return taskTracker;
        });

        // add an event
        commandsToOperations.put(Command.EVENT, (taskTracker, inputLine) -> {
            String[] detailsAfterSplittingCommand = inputLine.split("event\\s+", 2);
            if (detailsAfterSplittingCommand.length < 2) {
                throw new BingBongException("The description of an event cannot be empty. "
                        + "Add a task name after the \"event\" command."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            String[] detailsAfterSplittingFrom = detailsAfterSplittingCommand[1]
                    .split("\\s+/from\\s+",2);
            if (detailsAfterSplittingFrom.length < 2) {
                throw new BingBongException("For events, the \"/from\" delimiter "
                        + "must be placed between the task description and the chosen start time."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            String eventName = detailsAfterSplittingFrom[0];
            String[] detailsAfterSplittingTo = detailsAfterSplittingFrom[1]
                    .split("\\s+/to\\s+",2);
            if (detailsAfterSplittingTo.length < 2) {
                throw new BingBongException("For events, the \"/to\" delimiter "
                        + "must be placed between the chosen start time and the chosen end time."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            LocalDateTime startTime = parseDate(detailsAfterSplittingTo[0]);
            LocalDateTime endTime = parseDate(detailsAfterSplittingTo[1]);
            Event newEvent = new Event(eventName, startTime, endTime);

            taskTracker = taskTracker.addTask(newEvent);
            int newNumOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newEvent, newNumOfTasks)));
            return taskTracker;
        });

        commandsToOperations.put(Command.LIST, ((taskTracker, inputLine) -> {
            String listOfTasks = taskTracker.listTasks();
            System.out.println(new Message(getListTasksMessage(listOfTasks)));
            return taskTracker;
        }));

        return commandsToOperations;
    }

    // get the correct command from the input
    private static Command getCommand(String inputLine) throws BingBongException {
        Command chosenCommand;

        try {
            // attempt to convert the whole of the input to a command (i.e. LIST or BYE)
            chosenCommand = Command.valueOf(inputLine.toUpperCase());
        } catch (IllegalArgumentException notListOrByeEx) {
            // not LIST or BYE - get the first token and try to convert to a valid command
            try {
                String[] inputTokens = inputLine.split("\\s+");
                String inputCommand = inputTokens[0];
                chosenCommand = Command.valueOf(inputCommand.toUpperCase());
                if (chosenCommand.equals(Command.LIST) || chosenCommand.equals(Command.BYE)) {
                    throw new IllegalArgumentException("LIST or BYE command was given with other arguments - invalid");
                }
            } catch (IllegalArgumentException notValidCommandEx) {
                throw new BingBongException("I have no idea what that "
                        + "means. You could try:\n"
                        + Arrays.toString(Command.values()));
            }
        }

        return chosenCommand;
    }

    public BingBong(String dataFolderPath, String tasksFilename){
        try {
            // initialise mapping of commands to operations
            commandsToOperations = init_commands_to_operations();

            storage = new Storage(dataFolderPath, tasksFilename, DATE_FORMAT);
            taskTracker = storage.loadSavedTasks();
        } catch (FileNotFoundException ex) {
            System.out.println(new Message("No existing task file detected. "
                    + "An empty task list will be initialised."));
            taskTracker = new TaskTracker(dataFolderPath, tasksFilename);
        } catch (IOException ex) {
            System.out.println(new Message(getExceptionMessage("Something went wrong when "
                    + "initialising task storage: "
                    + ex.getMessage()
                    + "\nPlease fix the problem and try again.")));
        } catch (BingBongException ex) {
            System.out.println(new Message(getExceptionMessage(ex.getMessage())));
            taskTracker = new TaskTracker(dataFolderPath, tasksFilename);
        }
    }

    public void run() {
        // greet user
        System.out.println(new Message(getStartMessage()));

        // main processing loop for input
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine().strip();
        while (true) {
            try {
                // get command requested by user
                Command chosenCommand = getCommand(inputLine);

                if (chosenCommand.equals(Command.BYE)) {
                    // user wants to end the chat
                    System.out.println(new Message(getEndMessage()));
                    return;
                }

                // get operation needed for this command
                ThrowingBiFunction<TaskTracker, String, TaskTracker> op =
                        commandsToOperations.get(chosenCommand);

                // apply operation
                taskTracker = op.apply(taskTracker, inputLine);

                // save to storage
                storage.saveTasks(taskTracker.getCombinedSaveableTasks(DateTimeFormatter.ofPattern(DATE_FORMAT)));

            } catch (DateTimeParseException ex) {
                System.out.println(new Message(getExceptionMessage("The date "
                        + "that you have provided cannot be parsed: "
                        + ex.getMessage()
                        + "\nPlease use the correct format for dates. For example: "
                        + DATE_FORMATTING_EXAMPLE)));
            } catch (BingBongException ex) {
                System.out.println(new Message(getExceptionMessage(ex.getMessage())));
            }

            inputLine = sc.nextLine().strip();
        }
    }

    public static void main(String[] args) {
        new BingBong("./data", "tasks.txt").run();
    }
}