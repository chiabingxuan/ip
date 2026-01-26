package bingbong.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskTrackerTest {
    private static final ArrayList<Task> TASKS = new ArrayList<>(List.of(new Todo("task 1"),
            new Todo("task 2"), new Todo("task 3")));

    @Test
    public void getTask_indexInRange_success() throws Exception {
        // get first task "task 1"
        assertEquals("[T][ ] task 1",
                new TaskTracker(TASKS).getTask(0).toString());

        // get last task "task 3"
        assertEquals("[T][ ] task 3",
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
        // edit second task from "task 1" to "task 4"
        assertEquals("[T][ ] task 1\n[T][ ] task 4\n[T][ ] task 3",
                new TaskTracker(TASKS).editTask(1, new Todo("task 4")).toString());

        // edit second task from unmarked to marked
        assertEquals("[T][ ] task 1\n[T][X] task 2\n[T][ ] task 3",
                new TaskTracker(TASKS).editTask(1,
                        new Todo(new Todo("task 2"), true)).toString());
    }

    @Test
    public void editTask_indexOutOfRange_exceptionThrown() {
        // index exceeds length of list - 1
        try {
            assertEquals("", new TaskTracker(TASKS)
                    .editTask(10, new Todo("task 4"))
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
                    .editTask(-3, new Todo("task 4"))
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
        // add new task "task 4"
        assertEquals("[T][ ] task 1\n[T][ ] task 2\n[T][ ] task 3\n[T][ ] task 4",
                new TaskTracker(TASKS).addTask(new Todo("task 4")).toString());
    }

    @Test
    public void deleteTask_indexInRange_success() throws Exception {
        // delete first task "task 1"
        assertEquals("[T][ ] task 2\n[T][ ] task 3",
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
    public void listTask_success() {
        // list tasks
        assertEquals("1. [T][ ] task 1\n2. [T][ ] task 2\n3. [T][ ] task 3",
                new TaskTracker(TASKS).listTasks());
    }

    @Test
    public void changeTaskStatusAtIndex_indexInRange_success() throws Exception {
        // get the completed version of the third task, "task 3"
        assertEquals("[T][X] task 3", new TaskTracker(TASKS)
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
