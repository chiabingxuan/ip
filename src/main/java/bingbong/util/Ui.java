package bingbong.util;

import bingbong.task.Task;

/**
 * Outputs chatbot messages in a display, which can be viewed
 * by the user.
 */
public class Ui {
    private static final String HORIZONTAL_LINE =
            "________________________________________________________";

    /**
     * Outputs the opening message of the chatbot when the application
     * is first opened.
     */
    public void greet() {
        System.out.println("Yo, my name is BingBong. Hit me up if you need any help.");
    }

    /**
     * Outputs the current list of tasks saved in the <code>TaskTracker</code>.
     *
     * @param listOfTasks Concatenated <code>String</code> representing the current
     *                    list of tasks recorded.
     */
    public void printListTasksMessage(String listOfTasks) {
        System.out.println("Here are the tasks that you have added to the list:"
                + "\n\n"
                + listOfTasks);
    }

    /**
     * Outputs a message notifying the user that the chosen task
     * has been added, as requested.
     *
     * @param task       Task that has been added.
     * @param numOfTasks Number of tasks saved, upon addition of the new task.
     */
    public void printAddTaskMessage(Task task, int numOfTasks) {
        System.out.println("You're getting busier. I've added this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    /**
     * Outputs a message notifying the user that the chosen task
     * has been marked as complete, as requested.
     *
     * @param task Task that has been marked as complete.
     */
    public void printMarkedTaskMessage(Task task) {
        System.out.println("Congratulations on being hardworking for once! "
                + "This task has been marked as done:"
                + "\n"
                + task);
    }

    /**
     * Outputs a message notifying the user that the chosen task
     * has been marked as incomplete, as requested.
     *
     * @param task Task that has been marked as incomplete.
     */
    public void printUnmarkedTaskMessage(Task task) {
        System.out.println("This task has been marked as incomplete:"
                + "\n"
                + task);
    }

    /**
     * Outputs a message notifying the user that the chosen task
     * has been deleted, as requested.
     *
     * @param task       Task that has been deleted.
     * @param numOfTasks Number of tasks saved, upon deletion of the chosen task.
     */
    public void printDeletedTaskMessage(Task task, int numOfTasks) {
        System.out.println("Alright, I've gotten rid of this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    /**
     * Outputs a message notifying the user that an error has occurred.
     *
     * @param msg Message of the error thrown.
     */
    public void printExceptionMessage(String msg) {
        System.out.println("SORRY... :("
                + "\n"
                + msg);
    }

    /**
     * Outputs the closing message of the chatbot when the application
     * is closed.
     */
    public void sayGoodbye() {
        System.out.println("Hasta la vista, baby!");
    }

    /**
     * Outputs a horizontal line which acts as a divider between
     * individual chatbot messages.
     */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Outputs a message notifying the user of a warning, such as
     * when a pre-saved task file has been corrupted. The chatbot
     * will still function as per normal.
     *
     * @param warning Warning message to be shown in output.
     */
    public void printWarning(String warning) {
        System.out.println(warning);
    }
}
