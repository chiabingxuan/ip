package bingbong.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskTrackerTest {
    private static final ArrayList<Task> TASKS =
            new ArrayList<>(List.of(new Todo("finish some stuff"),
            new Deadline(new Deadline("finish ip",
                    LocalDateTime.of(2026, 2, 14, 14, 0)),
                    true),
            new Event("play basketball with friends",
                    LocalDateTime.of(2026, 2, 17, 15, 0),
                    LocalDateTime.of(2026, 2, 17, 17, 0))));

    @Test
    public void getTask_indexInRange_success() throws Exception {
        // get first task
        assertEquals("[T][ ] finish some stuff",
                new TaskTracker(TASKS).getTask(0).toString());

        // get last task
        assertEquals("[E][ ] play basketball with friends "
                + "(from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
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
        // edit first task
        assertEquals("[T][ ] go jogging\n"
                + "[D][X] finish ip (by: 14 Feb 2026, 2:00 pm)\n"
                + "[E][ ] play basketball with friends (from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
                new TaskTracker(TASKS).editTask(0, new Todo("go jogging")).toString());

        // edit second task from marked to unmarked
        assertEquals("[T][ ] finish some stuff\n"
                + "[D][ ] finish ip (by: 14 Feb 2026, 2:00 pm)\n"
                + "[E][ ] play basketball with friends (from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
                new TaskTracker(TASKS).editTask(1, new Deadline("finish ip",
                        LocalDateTime.of(2026, 2, 14, 14, 0)))
                        .toString());
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
        // add new task
        assertEquals("[T][ ] finish some stuff\n"
                + "[D][X] finish ip (by: 14 Feb 2026, 2:00 pm)\n"
                + "[E][ ] play basketball with friends (from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)\n"
                + "[T][ ] go jogging",
                new TaskTracker(TASKS).addTask(new Todo("go jogging")).toString());
    }

    @Test
    public void deleteTask_indexInRange_success() throws Exception {
        // delete first task
        assertEquals("[D][X] finish ip (by: 14 Feb 2026, 2:00 pm)\n"
                + "[E][ ] play basketball with friends (from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
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
        assertEquals("1. [T][ ] finish some stuff\n"
                + "2. [D][X] finish ip (by: 14 Feb 2026, 2:00 pm)\n"
                + "3. [E][ ] play basketball with friends (from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
                new TaskTracker(TASKS).listTasks());
    }

    @Test
    public void findTasks_success() {
        // find tasks containing substring "finish" (first and second tasks)
        assertEquals("1. [T][ ] finish some stuff\n"
                + "2. [D][X] finish ip (by: 14 Feb 2026, 2:00 pm)",
                new TaskTracker(TASKS).findTasks("finish"));

        // find tasks containing substring "basket" (third task)
        assertEquals("1. [E][ ] play basketball with friends "
                + "(from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
                new TaskTracker(TASKS).findTasks("basket"));
    }

    @Test
    public void changeTaskStatusAtIndex_indexInRange_success() throws Exception {
        // get the completed version of the third task
        assertEquals("[E][X] play basketball with friends "
                + "(from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)", new TaskTracker(TASKS)
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

    @Test
    public void remindImpendingTasks_success() {
        LocalDateTime windowStartTime = LocalDateTime.of(2026, 2, 12, 15, 0);

        // find tasks happening in 4 days (2nd task is already done so it will not show up)
        assertEquals("",
                new TaskTracker(TASKS).remindImpendingTasks(windowStartTime, 4));

        // find tasks happening in 5 days (3rd task just lies beyond the window)
        assertEquals("",
                new TaskTracker(TASKS).remindImpendingTasks(windowStartTime, 5));

        // find tasks happening in 6 days (only 3rd task is outstanding and will be included)
        assertEquals("1. [E][ ] play basketball with friends "
                + "(from: 17 Feb 2026, 3:00 pm to: 17 Feb 2026, 5:00 pm)",
                new TaskTracker(TASKS).remindImpendingTasks(windowStartTime, 6));
    }
}
