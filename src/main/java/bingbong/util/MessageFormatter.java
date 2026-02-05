package bingbong.util;

import bingbong.task.Task;

/**
 * Formats chatbot messages which can be viewed
 * by the user.
 */
public class MessageFormatter {
    /**
     * Returns the opening message of the chatbot when the application
     * is first opened.
     *
     * @return Opening message.
     */
    public static String getOpeningMessage() {
        return "Yo, my name is BingBong. Hit me up if you need any help.";
    }

    /**
     * Returns a message consisting of the current list of tasks
     * saved in the <code>TaskTracker</code>.
     *
     * @param listOfTasks Concatenated <code>String</code> representing the current
     *                    list of tasks recorded.
     * @return Output message.
     */
    public static String getListTasksMessage(String listOfTasks) {
        return "Here are the tasks that you have added to the list:"
                + "\n\n"
                + listOfTasks;
    }

    /**
     * Returns a message consisting of the list of tasks
     * whose names match the substring provided in the user's input.
     *
     * @param listOfTasks Concatenated <code>String</code> representing the
     *                    list of matching tasks.
     * @return Output message.
     */
    public static String getMatchingTasksMessage(String listOfTasks) {
        return "Here are the matching tasks that I've found:"
                + "\n\n"
                + listOfTasks;
    }

    /**
     * Returns a message notifying the user that the chosen task
     * has been added, as requested.
     *
     * @param task       Task that has been added.
     * @param numOfTasks Number of tasks saved, upon addition of the new task.
     * @return Output message.
     */
    public static String getAddTaskMessage(Task task, int numOfTasks) {
        return "You're getting busier. I've added this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.";
    }

    /**
     * Returns a message notifying the user that the chosen task
     * has been marked as complete, as requested.
     *
     * @param task Task that has been marked as complete.
     * @return Output message.
     */
    public static String getMarkedTaskMessage(Task task) {
        return "Congratulations on being hardworking for once! "
                + "This task has been marked as done:"
                + "\n"
                + task;
    }

    /**
     * Returns a message notifying the user that the chosen task
     * has been marked as incomplete, as requested.
     *
     * @param task Task that has been marked as incomplete.
     * @return Output message.
     */
    public static String getUnmarkedTaskMessage(Task task) {
        return "This task has been marked as incomplete:"
                + "\n"
                + task;
    }

    /**
     * Returns a message notifying the user that the chosen task
     * has been deleted, as requested.
     *
     * @param task       Task that has been deleted.
     * @param numOfTasks Number of tasks saved, upon deletion of the chosen task.
     * @return Output message.
     */
    public static String getDeletedTaskMessage(Task task, int numOfTasks) {
        return "Alright, I've gotten rid of this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.";
    }

    /**
     * Returns a message notifying the user that an exception has occurred.
     *
     * @param msg Message of the exception thrown.
     * @return Error message to be viewed by the user.
     */
    public static String getExceptionMessage(String msg) {
        return "SORRY... :("
                + "\n"
                + msg;
    }

    /**
     * Returns the closing message of the chatbot when the application
     * is closed.
     *
     * @return Goodbye message.
     */
    public static String getGoodbyeMessage() {
        return "Hasta la vista, baby!";
    }
}
