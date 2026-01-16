import java.util.function.BiFunction;
import java.util.HashMap;
import java.util.Scanner;

public class BingBong {
    // bot properties
    private static final String BOT_NAME = "BingBong";
    private static final HashMap<String,
            BiFunction<TaskTracker, String, TaskTracker>> COMMANDS_TO_OPERATIONS = new HashMap<>();
    private static final String BYE_COMMAND = "bye";

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
                + "Now you have " + numOfTasks + " tasks in the list.";
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

    private static String getEndMessage() {
        return "Hasta la vista, baby!";
    }

    // populate mapping of commands to the BiFunction to be called
    private static void init_commands_to_operations() {
        // list all tasks in current tracker
        COMMANDS_TO_OPERATIONS.put("list", (taskTracker, inputLine) -> {
            String listOfTasks = taskTracker.listTasks();
            System.out.println(new Message(getListTasksMessage(listOfTasks)));
            return taskTracker;
        });

        // mark chosen task done
        COMMANDS_TO_OPERATIONS.put("mark", (taskTracker, inputLine) -> {
            String[] inputTokens = inputLine.split(" ");
            int indexToMark = Integer.parseInt(inputTokens[1]) - 1;
            Task markedTask = taskTracker.changeTaskStatusAtIndex(indexToMark, true);
            taskTracker = taskTracker.editTask(indexToMark, markedTask);
            System.out.println(new Message(getMarkedTaskMessage(markedTask)));
            return taskTracker;
        });

        // mark chosen task as not done
        COMMANDS_TO_OPERATIONS.put("unmark", (taskTracker, inputLine) -> {
            String[] inputTokens = inputLine.split(" ");
            int indexToUnmark = Integer.parseInt(inputTokens[1]) - 1;
            Task unmarkedTask = taskTracker.changeTaskStatusAtIndex(indexToUnmark, false);
            taskTracker = taskTracker.editTask(indexToUnmark, unmarkedTask);
            System.out.println(new Message(getUnmarkedTaskMessage(unmarkedTask)));
            return taskTracker;
        });

        // add a todo
        COMMANDS_TO_OPERATIONS.put("todo", (taskTracker, inputLine) -> {
            String todoName = inputLine.split("todo ", 2)[1];
            Todo newTodo = new Todo(todoName);

            taskTracker = taskTracker.addTask(newTodo);
            int numOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newTodo, numOfTasks)));
            return taskTracker;
        });

        // add a deadline
        COMMANDS_TO_OPERATIONS.put("deadline", (taskTracker, inputLine) -> {
            String[] deadlineDetails = inputLine.split("deadline ", 2)[1]
                    .split(" /by ");
            String deadlineName = deadlineDetails[0];
            String byWhen = deadlineDetails[1];
            Deadline newDeadline = new Deadline(deadlineName, byWhen);

            taskTracker = taskTracker.addTask(newDeadline);
            int numOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newDeadline, numOfTasks)));
            return taskTracker;
        });

        // add an event
        COMMANDS_TO_OPERATIONS.put("event", (taskTracker, inputLine) -> {
            String[] eventDetails = inputLine.split("event ", 2)[1]
                    .split(" /from | /to ");
            String eventName = eventDetails[0];
            String startTime = eventDetails[1];
            String endTime = eventDetails[2];
            Event newEvent = new Event(eventName, startTime, endTime);

            taskTracker = taskTracker.addTask(newEvent);
            int numOfTasks = taskTracker.getNumOfTasks();
            System.out.println(new Message(getAddTaskMessage(newEvent, numOfTasks)));
            return taskTracker;
        });
    }

    public static void main(String[] args) {
        // initialise mapping of commands to operations
        init_commands_to_operations();

        System.out.println(new Message(getStartMessage()));

        TaskTracker taskTracker = new TaskTracker();
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();
        while (!inputLine.equals(BYE_COMMAND)) {
            // consider first token and see if it is a known command
            String[] inputTokens = inputLine.split(" ");
            String inputCommand = inputTokens[0];

            BiFunction<TaskTracker, String, TaskTracker> op = COMMANDS_TO_OPERATIONS.get(inputCommand);
            taskTracker = op.apply(taskTracker, inputLine);

            inputLine = sc.nextLine();
        }

        System.out.println(new Message(getEndMessage()));
    }
}