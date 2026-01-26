package bingbong.util;

import bingbong.task.Task;

public class Ui {
    private static final String HORIZONTAL_LINE =
            "________________________________________________________";

    // printing messages
    public void greet() {
        System.out.println("Yo, my name is BingBong. Hit me up if you need any help.");
    }

    public void printListTasksMessage(String listOfTasks) {
        System.out.println("Here are the tasks that you have added to the list:"
                + "\n\n"
                + listOfTasks);
    }

    public void printMatchingTasksMessage(String listOfTasks) {
        System.out.println("Here are the matching tasks that I've found:"
                + "\n\n"
                + listOfTasks);
    }

    public void printAddTaskMessage(Task task, int numOfTasks) {
        System.out.println("You're getting busier. I've added this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    public void printMarkedTaskMessage(Task task) {
        System.out.println("Congratulations on being hardworking for once! "
                + "This task has been marked as done:"
                + "\n"
                + task);
    }

    public void printUnmarkedTaskMessage(Task task) {
        System.out.println("This task has been marked as incomplete:"
                + "\n"
                + task);
    }

    public void printDeletedTaskMessage(Task task, int numOfTasks) {
        System.out.println("Alright, I've gotten rid of this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    public void printExceptionMessage(String msg) {
        System.out.println("SORRY... :("
                + "\n"
                + msg);
    }

    public void sayGoodbye() {
        System.out.println("Hasta la vista, baby!");
    }

    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void printWarning(String warning) {
        System.out.println(warning);
    }
}
