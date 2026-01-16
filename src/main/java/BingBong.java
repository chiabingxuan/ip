import java.util.Scanner;

public class BingBong {
    // bot properties
    private static final String BOT_NAME = "BingBong";
    private static final String LIST_COMMAND = "list";
    private static final String BYE_COMMAND = "bye";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";

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

    private static String getAddTaskMessage(String taskName) {
        return "added: " + taskName;
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

    public static void main(String[] args) {
        System.out.println(new Message(getStartMessage()));

        TaskTracker taskTracker = new TaskTracker();
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();
        while (!inputLine.equals(BYE_COMMAND)) {
            // consider first token and see if it is a known command
            String[] inputTokens = inputLine.split(" ");
            String inputCommand = inputTokens[0];

            switch (inputCommand) {
                // list existing tasks
                case LIST_COMMAND:
                    String listOfTasks = taskTracker.listTasks();
                    System.out.println(new Message(getListTasksMessage(listOfTasks)));
                    break;
                // marked chosen task as done
                case MARK_COMMAND:
                    int indexToMark = Integer.parseInt(inputTokens[1]) - 1;
                    Task markedTask = taskTracker.changeTaskStatus(indexToMark, true);
                    taskTracker = taskTracker.editTask(indexToMark, markedTask);
                    System.out.println(new Message(getMarkedTaskMessage(markedTask)));
                    break;
                // mark chosen task as not done
                case UNMARK_COMMAND:
                    int indexToUnmark = Integer.parseInt(inputTokens[1]) - 1;
                    Task unmarkedTask = taskTracker.changeTaskStatus(indexToUnmark, false);
                    taskTracker = taskTracker.editTask(indexToUnmark, unmarkedTask);
                    System.out.println(new Message(getUnmarkedTaskMessage(unmarkedTask)));
                    break;
                // by default we add the input given as a Task
                default:
                    taskTracker = taskTracker.addTask(inputLine);
                    System.out.println(new Message(getAddTaskMessage(inputLine)));
            }
            inputLine = sc.nextLine();
        }

        System.out.println(new Message(getEndMessage()));
    }
}