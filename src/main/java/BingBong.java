import java.util.*;

public class BingBong {
    // bot properties
    private static final String BOT_NAME = "BingBong";
    private static final HashMap<String,
            ThrowingBiFunction<TaskTracker, String, TaskTracker>> COMMANDS_TO_OPERATIONS = new HashMap<>();
    private static final String LIST_COMMAND = "list";
    private static final String BYE_COMMAND = "bye";

    // examples to be shown in error messages
    private static final String MARK_EXAMPLE = "\"mark 1\" to mark the first task as completed";
    private static final String UNMARK_EXAMPLE = "\"unmark 1\" to mark the first task as incomplete";
    private static final String DELETE_EXAMPLE = "\"delete 1\" to delete the first task";
    private static final String TODO_EXAMPLE = "\"todo go grocery shopping\"";
    private static final String DEADLINE_EXAMPLE = "\"deadline finish homework /by 9pm\"";
    private static final String EVENT_EXAMPLE = "\"event go for a jog /from 9am /to 10am\"";

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

    // list all tasks in current tracker
    private static void listTrackerTasks(TaskTracker taskTracker) {
        String listOfTasks = taskTracker.listTasks();
        System.out.println(new Message(getListTasksMessage(listOfTasks)));
    }

    // fetch correct thing to do, based on the given input command
    private static ThrowingBiFunction<TaskTracker, String, TaskTracker> getOp(String inputCommand)
            throws BingBongException {
        if (!COMMANDS_TO_OPERATIONS.containsKey(inputCommand)) {
            Set<String> commands = new HashSet<>(COMMANDS_TO_OPERATIONS.keySet());
            commands.addAll(List.of(LIST_COMMAND, BYE_COMMAND));

            throw new BingBongException("I have no idea what that "
                    + "means. You could try:\n"
                    + commands
            );
        }

        return COMMANDS_TO_OPERATIONS.get(inputCommand);
    }

    // populate mapping of commands to the ThrowingBiFunction to be called
    private static void init_commands_to_operations() {
        // mark chosen task done
        COMMANDS_TO_OPERATIONS.put("mark", (taskTracker, inputLine) -> {
            String[] inputTokens = inputLine.split("\\s+");

            try {
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
        COMMANDS_TO_OPERATIONS.put("unmark", (taskTracker, inputLine) -> {
            String[] inputTokens = inputLine.split("\\s+");

            try {
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
        COMMANDS_TO_OPERATIONS.put("delete", (taskTracker, inputLine) -> {
            String[] inputTokens = inputLine.split("\\s+");

            try {
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
        COMMANDS_TO_OPERATIONS.put("todo", (taskTracker, inputLine) -> {
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
        COMMANDS_TO_OPERATIONS.put("deadline", (taskTracker, inputLine) -> {
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
            String byWhen = deadlineDetails[1];
            Deadline newDeadline = new Deadline(deadlineName, byWhen);

            taskTracker = taskTracker.addTask(newDeadline);
            int newNumOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newDeadline, newNumOfTasks)));
            return taskTracker;
        });

        // add an event
        COMMANDS_TO_OPERATIONS.put("event", (taskTracker, inputLine) -> {
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

            String startTime = detailsAfterSplittingTo[0];
            String endTime = detailsAfterSplittingTo[1];
            Event newEvent = new Event(eventName, startTime, endTime);

            taskTracker = taskTracker.addTask(newEvent);
            int newNumOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newEvent, newNumOfTasks)));
            return taskTracker;
        });
    }

    public static void main(String[] args) {
        // initialise mapping of commands to operations
        init_commands_to_operations();

        System.out.println(new Message(getStartMessage()));

        TaskTracker taskTracker = new TaskTracker();
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine().strip();
        while (!inputLine.equals(BYE_COMMAND)) {
            // if user wants to list the tasks stored, do so
            if (inputLine.equals(LIST_COMMAND)) {
                listTrackerTasks(taskTracker);
            } else {
                // consider first token and see if it is a known command
                String[] inputTokens = inputLine.split("\\s+");
                String inputCommand = inputTokens[0];

                try {
                    ThrowingBiFunction<TaskTracker, String, TaskTracker> op = getOp(inputCommand);
                    taskTracker = op.apply(taskTracker, inputLine);
                } catch (BingBongException ex) {
                    System.out.println(new Message(getExceptionMessage(ex.getMessage())));
                }
            }

            inputLine = sc.nextLine().strip();
        }

        System.out.println(new Message(getEndMessage()));
    }
}