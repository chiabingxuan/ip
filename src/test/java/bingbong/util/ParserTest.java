package bingbong.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parse_correctFormat_success() throws Exception {
        // todo provided in correct format
        assertEquals("add command: [T][ ] borrow book",
                Parser.parse("todo borrow book").toString());

        // deadline provided in correct format
        assertEquals("add command: [D][ ] do laundry (by: 1 Mar 2025, 11:00 am)",
                Parser.parse("deadline do laundry /by 1/3/2025 11:00").toString());

        // event provided in correct format
        assertEquals("add command: [E][ ] chess training "
                        + "(from: 1 Mar 2025, 3:00 pm to: 1 Mar 2025, 6:00 pm)",
                Parser.parse("event chess training /from 1/3/2025 15:00 "
                        + "/to 1/3/2025 18:00").toString());

        // list provided in correct format
        assertEquals("list command",
                Parser.parse("list").toString());

        // list provided in correct format with whitespace
        assertEquals("list command",
                Parser.parse("    list  ").toString());

        // bye provided in correct format
        assertEquals("bye command",
                Parser.parse("bye").toString());

        // list provided in correct format with whitespace
        assertEquals("bye command",
                Parser.parse("    bye  ").toString());

        // mark provided in correct format
        assertEquals("mark command: 0",
                Parser.parse("mark 1").toString());

        // unmark provided in correct format
        assertEquals("unmark command: 2",
                Parser.parse("unmark 3").toString());

        // delete provided in correct format
        assertEquals("delete command: 1",
                Parser.parse("delete 2").toString());
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        // invalid command
        try {
            assertEquals("", Parser.parse("random command").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("I have no idea what that "
                    + "means. You could try:\n"
                    + "[MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, LIST, BYE]", ex.getMessage());
        }
    }

    @Test
    public void parse_extraArgsAfterListOrBye_exceptionThrown() {
        // extra arg after list
        try {
            assertEquals("", Parser.parse("list something").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("LIST or BYE command was given with other arguments - invalid",
                    ex.getMessage());
        }

        // extra arg after bye
        try {
            assertEquals("", Parser.parse("bye hope to see you again").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("LIST or BYE command was given with other arguments - invalid",
                    ex.getMessage());
        }
    }

    @Test
    public void parse_invalidIndex_exceptionThrown() {
        // invalid index for mark
        try {
            assertEquals("", Parser.parse("mark something").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"mark\" command."
                    + "\nEg. "
                    + "\"mark 1\" to mark the first task as completed", ex.getMessage());
        }

        // invalid index for unmark
        try {
            assertEquals("", Parser.parse("unmark something").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"unmark\" command."
                    + "\nEg. "
                    + "\"unmark 1\" to mark the first task as incomplete", ex.getMessage());
        }

        // invalid index for delete
        try {
            assertEquals("", Parser.parse("delete something").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"delete\" command."
                    + "\nEg. "
                    + "\"delete 1\" to delete the first task", ex.getMessage());
        }
    }

    @Test
    public void parse_missingIndex_exceptionThrown() {
        // missing index for mark
        try {
            assertEquals("", Parser.parse("mark   ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is missing. Make sure you have added a "
                    + "task number after the \"mark\" command."
                    + "\nEg. "
                    + "\"mark 1\" to mark the first task as completed", ex.getMessage());
        }

        // missing index for unmark
        try {
            assertEquals("", Parser.parse("unmark   ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is missing. Make sure you have added a "
                    + "task number after the \"unmark\" command."
                    + "\nEg. "
                    + "\"unmark 1\" to mark the first task as incomplete", ex.getMessage());
        }

        // missing index for delete
        try {
            assertEquals("", Parser.parse("delete  ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("Task number is missing. Make sure you have added a "
                    + "task number after the \"delete\" command."
                    + "\nEg. "
                    + "\"delete 1\" to delete the first task", ex.getMessage());
        }
    }

    @Test
    public void parse_todoMissingFields_exceptionThrown() {
        // missing todo name
        try {
            assertEquals("", Parser.parse("todo   ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The description of a todo cannot be empty. "
                    + "Add a task name after the \"todo\" command."
                    + "\nEg. "
                    + "\"todo go grocery shopping\"", ex.getMessage());
        }
    }

    @Test
    public void parse_deadlineMissingFields_exceptionThrown() {
        // missing deadline name
        try {
            assertEquals("", Parser.parse("deadline   ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The description of a deadline cannot be empty. "
                    + "Add a task name after the \"deadline\" command."
                    + "\nEg. "
                    + "\"deadline finish homework /by 2/1/2003 21:00\"", ex.getMessage());
        }

        // missing deadline name
        try {
            assertEquals("", Parser.parse("deadline /by 3/3/2025 15:00").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For deadlines, the \"/by\" delimiter "
                    + "must be placed between the task description and the chosen date."
                    + "\nEg. "
                    + "\"deadline finish homework /by 2/1/2003 21:00\"", ex.getMessage());
        }

        // missing deadline date
        try {
            assertEquals("", Parser.parse("deadline do some stuff /by").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For deadlines, the \"/by\" delimiter "
                    + "must be placed between the task description and the chosen date."
                    + "\nEg. "
                    + "\"deadline finish homework /by 2/1/2003 21:00\"", ex.getMessage());
        }
    }

    @Test
    public void parse_eventMissingFields_exceptionThrown() {
        // missing event name
        try {
            assertEquals("", Parser.parse("event   ").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The description of an event cannot be empty. "
                            + "Add a task name after the \"event\" command."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event name
        try {
            assertEquals("", Parser.parse("event /from 2/3/2025 16:00 "
                    + "/to 2/3/2025 17:00").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/from\" delimiter "
                            + "must be placed between the task description and the chosen start time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event start time
        try {
            assertEquals("",
                    Parser.parse("event go hiking /from").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/from\" delimiter "
                            + "must be placed between the task description and the chosen start time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event start time
        try {
            assertEquals("", Parser.parse("event go cycling /to 1/3/2025 18:00")
                    .toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/from\" delimiter "
                            + "must be placed between the task description and the chosen start time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event end time
        try {
            assertEquals("",
                    Parser.parse("event touch grass /from 2/3/2025 16:00 /to").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/to\" delimiter "
                            + "must be placed between the chosen start time and the chosen end time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event start time
        try {
            assertEquals("", Parser.parse("event go skating /from   /to 2/3/2025 15:00")
                    .toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/to\" delimiter "
                            + "must be placed between the chosen start time and the chosen end time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }

        // missing event start time and end time
        try {
            assertEquals("", Parser.parse("event go cycling    /from /to")
                    .toString());
            fail();
        } catch (Exception ex) {
            assertEquals("For events, the \"/to\" delimiter "
                            + "must be placed between the chosen start time and the chosen end time."
                            + "\nEg. "
                            + "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"",
                    ex.getMessage());
        }
    }

    @Test
    public void parse_invalidDate_exceptionThrown() {
        // dashes instead of slashes
        try {
            assertEquals("", Parser.parse("deadline do some stuff /by 3-3-2025 14:00")
                    .toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The date "
                    + "that you have provided cannot be parsed: "
                    + "Text '3-3-2025 14:00' could not be parsed at index 1"
                    + "\nPlease use the correct format for dates. For example: "
                    + "\"2/1/2003 13:18\" which means 2 Jan 2003, 1:18 pm", ex.getMessage());
        }

        // wrong order of day/month/year
        try {
            assertEquals("", Parser.parse("event chess training /from 2025/9/11 15:00 "
                    + "/to 1/3/2025 18:00").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The date "
                    + "that you have provided cannot be parsed: "
                    + "Text '2025/9/11 15:00' could not be parsed at index 7"
                    + "\nPlease use the correct format for dates. For example: "
                    + "\"2/1/2003 13:18\" which means 2 Jan 2003, 1:18 pm", ex.getMessage());
        }

        // 12h clock instead of 24h clock (HH:MM)
        try {
            assertEquals("", Parser.parse("event chess training /from 1/3/2025 15:00 "
                    + "/to 1/3/2025 6 pm").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The date "
                    + "that you have provided cannot be parsed: "
                    + "Text '1/3/2025 6 pm' could not be parsed at index 9"
                    + "\nPlease use the correct format for dates. For example: "
                    + "\"2/1/2003 13:18\" which means 2 Jan 2003, 1:18 pm", ex.getMessage());
        }

        // spelling out the date in words instead of day/month/year
        try {
            assertEquals("", Parser.parse("event chess training /from 1/3/2025 15:00 "
                    + "/to 1 Mar 2025 18:00").toString());
            fail();
        } catch (Exception ex) {
            assertEquals("The date "
                    + "that you have provided cannot be parsed: "
                    + "Text '1 Mar 2025 18:00' could not be parsed at index 1"
                    + "\nPlease use the correct format for dates. For example: "
                    + "\"2/1/2003 13:18\" which means 2 Jan 2003, 1:18 pm", ex.getMessage());
        }
    }
}
