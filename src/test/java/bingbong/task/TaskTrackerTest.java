package bingbong.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskTrackerTest {
    private static final ArrayList<Task> TASKS = new ArrayList<>(List.of(new Todo("eat lunch"),
            new Todo("eat dinner"), new Todo("sleep")));

    @Test
    public void getTask_indexInRange_success() throws Exception {
        // get first task "eat lunch"
        assertEquals("[T][ ] eat lunch",
                new TaskTracker(TASKS).getTask(0).toString());

        // get last task "sleep"
        assertEquals("[T][ ] sleep",
                new TaskTracker(TASKS).getTask(2).toString());
    }

    @Test
    public void getTask_indexOutOfRange_exceptionThrown() {
        // index exceeds length of list - 1
        try {
            assertEquals("",
                    new TaskTracker(TASKS).getTask(10).toString());
            fail();
        } catch (Exception ex) {
            // the argument passed into getTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into getTask
            assertEquals("The task index that you have provided (11) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }

        // negative index
        try {
            assertEquals("",
                    new TaskTracker(TASKS).getTask(-3).toString());
            fail();
        } catch (Exception ex) {
            // the argument passed into getTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into getTask
            assertEquals("The task index that you have provided (-2) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }
    }

    @Test
    public void editTask_indexInRange_success() throws Exception {
        // edit second task from "eat lunch" to "go jogging"
        assertEquals("[T][ ] eat lunch\n[T][ ] go jogging\n[T][ ] sleep",
                new TaskTracker(TASKS).editTask(1, new Todo("go jogging")).toString());

        // edit second task from unmarked to marked
        assertEquals("[T][ ] eat lunch\n[T][X] eat dinner\n[T][ ] sleep",
                new TaskTracker(TASKS).editTask(1,
                        new Todo(new Todo("eat dinner"), true)).toString());
    }

    @Test
    public void editTask_indexOutOfRange_exceptionThrown() {
        // index exceeds length of list - 1
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .editTask(10, new Todo("go jogging"))
                    .toString());
            fail();
        } catch (Exception ex) {
            // the argument passed into editTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into editTask
            assertEquals("The task index that you have provided (11) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }

        // negative index
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .editTask(-3, new Todo("go jogging"))
                    .toString());

            fail();
        } catch (Exception ex) {
            // the argument passed into editTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into editTask
            assertEquals("The task index that you have provided (-2) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }
    }

    @Test
    public void addTask_success() {
        // add new task "go jogging"
        assertEquals("[T][ ] eat lunch\n[T][ ] eat dinner\n[T][ ] sleep\n[T][ ] go jogging",
                new TaskTracker(TASKS).addTask(new Todo("go jogging")).toString());
    }

    @Test
    public void deleteTask_indexInRange_success() throws Exception {
        // delete first task "eat lunch"
        assertEquals("[T][ ] eat dinner\n[T][ ] sleep",
                new TaskTracker(TASKS).deleteTask(0).toString());
    }

    @Test
    public void deleteTask_indexOutOfRange_exceptionThrown() {
        // index exceeds length of list - 1
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .deleteTask(10)
                    .toString());
            fail();
        } catch (Exception ex) {
            // the argument passed into deleteTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into deleteTask
            assertEquals("The task index that you have provided (11) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }

        // negative index
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .deleteTask(-3)
                    .toString());

            fail();
        } catch (Exception ex) {
            // the argument passed into deleteTask is always 1 lesser than what is provided
            // in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into deleteTask
            assertEquals("The task index that you have provided (-2) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }
    }

    @Test
    public void listTasks_success() {
        // list tasks
        assertEquals("1. [T][ ] eat lunch\n2. [T][ ] eat dinner\n3. [T][ ] sleep",
                new TaskTracker(TASKS).listTasks());
    }

    @Test
    public void findTasks_success() {
        // find tasks containing substring "eat" (first and second tasks)
        assertEquals("1. [T][ ] eat lunch\n2. [T][ ] eat dinner",
                new TaskTracker(TASKS).findTasks("eat"));

        // find tasks containing substring "sleep" (third task)
        assertEquals("1. [T][ ] sleep",
                new TaskTracker(TASKS).findTasks("sleep"));
    }

    @Test
    public void changeTaskStatusAtIndex_indexInRange_success() throws Exception {
        // get the completed version of the third task, "sleep"
        assertEquals("[T][X] sleep", new TaskTracker(TASKS)
                .changeTaskStatusAtIndex(2, true)
                .toString());
    }

    @Test
    public void changeTaskStatusAtIndex_indexOutOfRange_exceptionThrown() {
        // index exceeds length of list - 1
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .changeTaskStatusAtIndex(10, true)
                    .toString());
            fail();
        } catch (Exception ex) {
            // the argument passed into changeTaskStatusAtIndex is always 1 lesser than
            // what is provided in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into changeTaskStatusAtIndex
            assertEquals("The task index that you have provided (11) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }

        // negative index
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .changeTaskStatusAtIndex(-3, true)
                    .toString());

            fail();
        } catch (Exception ex) {
            // the argument passed into changeTaskStatusAtIndex is always 1 lesser than
            // what is provided in the user input (due to difference between 0 and 1-indexing).
            // we want to show 1-indexing for the user, hence the index in the error
            // message is 1 greater than the argument passed into changeTaskStatusAtIndex
            assertEquals("The task index that you have provided (-2) does not exist. "
                    + "There are currently 3 task(s) in the list.", ex.getMessage());
        }
    }
}
