class Ui {
    private static final String HORIZONTAL_LINE =
            "________________________________________________________";

    // printing messages
    void greet() {
        System.out.println("Yo, my name is BingBong. Hit me up if you need any help.");
    }

    void printListTasksMessage(String listOfTasks) {
        System.out.println("Here are the tasks that you have added to the list:"
                + "\n"
                + listOfTasks);
    }

    void printAddTaskMessage(Task task, int numOfTasks) {
        System.out.println("You're getting busier. I've added this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    void printMarkedTaskMessage(Task task) {
        System.out.println("Congratulations on being hardworking for once! "
                + "This task has been marked as done:"
                + "\n"
                + task);
    }

    void printUnmarkedTaskMessage(Task task) {
        System.out.println("This task has been marked as incomplete:"
                + "\n"
                + task);
    }

    void printDeletedTaskMessage(Task task, int numOfTasks) {
        System.out.println("Alright, I've gotten rid of this task:"
                + "\n"
                + task
                + "\n"
                + "Now you have " + numOfTasks + " task(s) in the list.");
    }

    void printExceptionMessage(String msg) {
        System.out.println("SORRY... :("
                + "\n"
                + msg);
    }

    void sayGoodbye() {
        System.out.println("Hasta la vista, baby!");
    }

    void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    void printWarning(String warning) {
        System.out.println(warning);
    }
}
