import java.util.Scanner;
import java.util.stream.IntStream;

public class BingBong {
    // bot properties
    private static final String BOT_NAME = "BingBong";
    private static final String LIST_COMMAND = "list";
    private static final String BYE_COMMAND = "bye";

    // message templates
    private static final String START_MESSAGE = "Yo, my name is "
            + BOT_NAME
            + ". Hit me up if you need any help.";
    private static final String END_MESSAGE = "Hasta la vista, baby!";

    // task storage
    private static final int MAX_TASKS = 100;

    // list tasks in storage
    private static void listTasks(Task[] tasks, int currTaskIndex) {
        // use 1-indexing for printed list
        String listOfTasks = IntStream.rangeClosed(1, currTaskIndex)
                // create list item in String
                .mapToObj(num -> num + ". " + tasks[num - 1])
                // combine list items
                .reduce("", (x, y) -> x + "\n" + y);

        System.out.println(new Message(listOfTasks));
    }

    // add new task to the storage, and return the incremented array index
    private static int addTask(Task[] tasks, String taskName, int currTaskIndex) {
        if (currTaskIndex >= MAX_TASKS) {
            // storage is full, throw exception
            throw new IllegalStateException("Task storage is full");
        }

        tasks[currTaskIndex] = new Task(taskName);
        currTaskIndex++;
        System.out.println(new Message("added: " + taskName));

        return currTaskIndex;
    }

    public static void main(String[] args) {
        System.out.println(new Message(START_MESSAGE));

        Task[] tasks = new Task[MAX_TASKS];
        int currTaskIndex = 0;

        // echo user input if it is not bye
        Scanner sc = new Scanner(System.in);
        String inputCommand = sc.nextLine();
        while (!inputCommand.equals(BYE_COMMAND)) {
            if (inputCommand.equals(LIST_COMMAND)) {
                listTasks(tasks, currTaskIndex);
            } else {
                currTaskIndex = addTask(tasks, inputCommand, currTaskIndex);
            }
            inputCommand = sc.nextLine();
        }

        System.out.println(new Message(END_MESSAGE));
    }
}