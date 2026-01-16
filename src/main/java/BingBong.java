import java.util.Scanner;
import java.util.stream.IntStream;

public class BingBong {
    // bot properties
    private static final String botName = "BingBong";
    private static final String listCommand = "list";
    private static final String byeCommand = "bye";

    // message templates
    private static final String startMessage = "Yo, my name is "
            + botName
            + ". Hit me up if you need any help.";
    private static final String endMessage = "Hasta la vista, baby!";

    // task storage
    private static final int maxTasks = 100;

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
        if (currTaskIndex >= maxTasks) {
            // storage is full, throw exception
            throw new IllegalStateException("Task storage is full");
        }

        tasks[currTaskIndex] = new Task(taskName);
        currTaskIndex++;
        System.out.println(new Message("added: " + taskName));

        return currTaskIndex;
    }

    public static void main(String[] args) {
        System.out.println(new Message(startMessage));

        Task[] tasks = new Task[maxTasks];
        int currTaskIndex = 0;

        // echo user input if it is not bye
        Scanner sc = new Scanner(System.in);
        String inputCommand = sc.nextLine();
        while (!inputCommand.equals(byeCommand)) {
            if (inputCommand.equals(listCommand)) {
                listTasks(tasks, currTaskIndex);
            } else {
                currTaskIndex = addTask(tasks, inputCommand, currTaskIndex);
            }
            inputCommand = sc.nextLine();
        }

        System.out.println(new Message(endMessage));
    }
}