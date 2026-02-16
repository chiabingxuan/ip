package bingbong.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;

import bingbong.command.AddCommand;
import bingbong.command.ByeCommand;
import bingbong.command.Command;
import bingbong.command.CommandType;
import bingbong.command.DeleteCommand;
import bingbong.command.FindCommand;
import bingbong.command.ListCommand;
import bingbong.command.MarkCommand;
import bingbong.command.RemindCommand;
import bingbong.command.UnmarkCommand;
import bingbong.task.Deadline;
import bingbong.task.Event;
import bingbong.task.Todo;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

/**
 * Processes and parses input provided by the user. Identifies the type of
 * command that is to be executed, whilst creating the operation to be applied
 * when the command is executed. Also parses correctly formatted dates into
 * <code>LocalDateTime</code> objects.
 */
public class Parser {
    // for parsing of dates
    private static final String DATE_FORMAT = "d/M/yyyy HH:mm";

    // examples to be shown in error messages
    private static final String MARK_EXAMPLE =
            "\"mark 1\" to mark the first task as completed";
    private static final String UNMARK_EXAMPLE =
            "\"unmark 1\" to mark the first task as incomplete";
    private static final String DELETE_EXAMPLE =
            "\"delete 1\" to delete the first task";
    private static final String FIND_EXAMPLE =
            "\"find exercise\" to find all the tasks containing the substring \"exercise\"";
    private static final String TODO_EXAMPLE =
            "\"todo go grocery shopping\"";
    private static final String DEADLINE_EXAMPLE =
            "\"deadline finish homework /by 2/1/2003 21:00\"";
    private static final String EVENT_EXAMPLE =
            "\"event go for a jog /from 2/1/2003 09:00 /to 2/1/2003 10:00\"";
    private static final String REMIND_EXAMPLE =
            "\"remind 3\" to be reminded of all the tasks taking place, within 3 days from now";
    private static final String DATE_FORMATTING_EXAMPLE =
            "\"2/1/2003 13:18\" which means 2 Jan 2003, 1:18 pm";

    // maps different command types to a function that gives us the correct Command object
    private static final HashMap<CommandType,
            ThrowingFunction<String, Command>> typesToCommands = new HashMap<>();

    /**
     * Parses and returns a <code>MarkCommand</code> from the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>MarkCommand</code> representing the operation to be executed.
     * @throws ParserException If the task index is missing or invalid.
     */
    private static MarkCommand getMarkCommand(String inputLine) throws ParserException {
        try {
            String[] inputTokens = inputLine.split("\\s+");
            int indexToMark = Integer.parseInt(inputTokens[1]) - 1;
            return new MarkCommand(indexToMark);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParserException("Task number is missing. Make sure you have added a "
                    + "task number after the \"mark\" command."
                    + "\nEg. "
                    + MARK_EXAMPLE);
        } catch (NumberFormatException ex) {
            throw new ParserException("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"mark\" command."
                    + "\nEg. "
                    + MARK_EXAMPLE);
        }
    }

    /**
     * Parses and returns a <code>UnmarkCommand</code> from the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>UnmarkCommand</code> representing the operation to be executed.
     * @throws ParserException If the task index is missing or invalid.
     */
    private static UnmarkCommand getUnmarkCommand(String inputLine) throws ParserException {
        try {
            String[] inputTokens = inputLine.split("\\s+");
            int indexToUnmark = Integer.parseInt(inputTokens[1]) - 1;
            return new UnmarkCommand(indexToUnmark);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParserException("Task number is missing. Make sure you have added a "
                    + "task number after the \"unmark\" command."
                    + "\nEg. "
                    + UNMARK_EXAMPLE);
        } catch (NumberFormatException ex) {
            throw new ParserException("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"unmark\" command."
                    + "\nEg. "
                    + UNMARK_EXAMPLE);
        }
    }

    /**
     * Parses and returns a <code>DeleteCommand</code> from the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>DeleteCommand</code> representing the operation to be executed.
     * @throws ParserException If the task index is missing or invalid.
     */
    private static DeleteCommand getDeleteCommand(String inputLine) throws ParserException {
        try {
            String[] inputTokens = inputLine.split("\\s+");
            int indexToDelete = Integer.parseInt(inputTokens[1]) - 1;
            return new DeleteCommand(indexToDelete);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParserException("Task number is missing. Make sure you have added a "
                    + "task number after the \"delete\" command."
                    + "\nEg. "
                    + DELETE_EXAMPLE);
        } catch (NumberFormatException ex) {
            throw new ParserException("Task number is invalid. Make sure you have added the "
                    + "correct task number after the \"delete\" command."
                    + "\nEg. "
                    + DELETE_EXAMPLE);
        }
    }

    /**
     * Parses and returns a <code>FindCommand</code> from the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>FindCommand</code> containing the substring to search for.
     * @throws ParserException If the search substring is missing.
     */
    private static FindCommand getFindCommand(String inputLine) throws ParserException {
        try {
            String[] inputTokens = inputLine.split("\\s+", 2);
            String substring = inputTokens[1];
            return new FindCommand(substring);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParserException("Substring is missing. After the \"find\" command, "
                    + "make sure you have added a substring to search for."
                    + "\nEg. "
                    + FIND_EXAMPLE);
        }
    }

    /**
     * Parses and returns an <code>AddCommand</code> from the user input,
     * where we wish to add a todo.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>AddCommand</code> wrapping a newly created todo.
     * @throws ParserException If the task description is missing.
     */
    private static AddCommand getTodoAddCommand(String inputLine) throws ParserException {
        String[] detailsAfterSplit = inputLine.split("todo\\s+", 2);

        if (detailsAfterSplit.length < 2) {
            throw new ParserException("The description of a todo cannot be empty. "
                    + "Add a task name after the \"todo\" command."
                    + "\nEg. "
                    + TODO_EXAMPLE);
        }

        String todoName = detailsAfterSplit[1];
        Todo newTodo = new Todo(todoName);

        return new AddCommand(newTodo);

    }

    /**
     * Parses and returns an <code>AddCommand</code> from the user input,
     * where we wish to add a deadline.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>AddCommand</code> wrapping a newly created deadline.
     * @throws ParserException If the description or required delimiters are missing,
     *                         or if the date cannot be parsed.
     */
    private static AddCommand getDeadlineAddCommand(String inputLine) throws ParserException {
        String[] detailsAfterSplittingCommand = inputLine.split("deadline\\s+", 2);
        if (detailsAfterSplittingCommand.length < 2) {
            throw new ParserException("The description of a deadline cannot be empty. "
                    + "Add a task name after the \"deadline\" command."
                    + "\nEg. "
                    + DEADLINE_EXAMPLE);
        }

        String[] deadlineDetails = detailsAfterSplittingCommand[1]
                .split("\\s+/by\\s+", 2);
        if (deadlineDetails.length < 2) {
            throw new ParserException("For deadlines, the \"/by\" delimiter "
                    + "must be placed between the task description and the chosen date."
                    + "\nEg. "
                    + DEADLINE_EXAMPLE);
        }

        String deadlineName = deadlineDetails[0];
        LocalDateTime byWhen = parseDate(deadlineDetails[1]);
        Deadline newDeadline = new Deadline(deadlineName, byWhen);

        return new AddCommand(newDeadline);
    }

    /**
     * Parses and returns an <code>AddCommand</code> from the user input,
     * where we wish to add an event.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>AddCommand</code> wrapping a newly created event.
     * @throws ParserException If required delimiters are missing or
     *                         if the start or end dates cannot be parsed.
     */
    private static AddCommand getEventAddCommand(String inputLine) throws ParserException {
        String[] detailsAfterSplittingCommand = inputLine.split("event\\s+", 2);
        if (detailsAfterSplittingCommand.length < 2) {
            throw new ParserException("The description of an event cannot be empty. "
                    + "Add a task name after the \"event\" command."
                    + "\nEg. "
                    + EVENT_EXAMPLE);
        }

        String[] detailsAfterSplittingFrom = detailsAfterSplittingCommand[1]
                .split("\\s+/from\\s+", 2);
        if (detailsAfterSplittingFrom.length < 2) {
            throw new ParserException("For events, the \"/from\" delimiter "
                    + "must be placed between the task description and the chosen start time."
                    + "\nEg. "
                    + EVENT_EXAMPLE);
        }

        String eventName = detailsAfterSplittingFrom[0];
        String[] detailsAfterSplittingTo = detailsAfterSplittingFrom[1]
                .split("\\s+/to\\s+", 2);
        if (detailsAfterSplittingTo.length < 2) {
            throw new ParserException("For events, the \"/to\" delimiter "
                    + "must be placed between the chosen start time and the chosen end time."
                    + "\nEg. "
                    + EVENT_EXAMPLE);
        }

        LocalDateTime startTime = parseDate(detailsAfterSplittingTo[0]);
        LocalDateTime endTime = parseDate(detailsAfterSplittingTo[1]);
        Event newEvent = new Event(eventName, startTime, endTime);

        return new AddCommand(newEvent);
    }

    /**
     * Creates and returns a <code>ListCommand</code>.
     *
     * @return <code>ListCommand</code> representing a request to list all tasks.
     */
    private static ListCommand getListCommand() {
        return new ListCommand();
    }

    /**
     * Parses and returns a <code>RemindCommand</code> from the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return <code>RemindCommand</code> representing the reminder operation.
     * @throws ParserException If the time window is missing or invalid.
     */
    private static RemindCommand getRemindCommand(String inputLine) throws ParserException {
        try {
            String[] inputTokens = inputLine.split("\\s+");
            int daysFromNow = Integer.parseInt(inputTokens[1]);
            return new RemindCommand(daysFromNow);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new ParserException("Time window to check for (in days) is missing. "
                    + "Make sure you have added it after the \"remind\" command."
                    + "\nEg. "
                    + REMIND_EXAMPLE);
        } catch (NumberFormatException ex) {
            throw new ParserException("Time window is invalid. Make sure you have added a "
                    + "valid time window (in days) after the \"remind\" command."
                    + "\nEg. "
                    + REMIND_EXAMPLE);
        }
    }

    /**
     * Creates and returns a <code>ByeCommand</code>.
     *
     * @return <code>ByeCommand</code> representing program termination.
     */
    private static ByeCommand getByeCommand() {
        return new ByeCommand();
    }

    /**
     * Populates the internal mapping from <code>CommandType</code> to the
     * corresponding command-construction function. This method must be called
     * before attempting to retrieve commands from the mapping.
     */
    private static void setupMapping() {
        typesToCommands.put(CommandType.MARK, inputLine -> getMarkCommand(inputLine));
        typesToCommands.put(CommandType.UNMARK, inputLine -> getUnmarkCommand(inputLine));
        typesToCommands.put(CommandType.DELETE, inputLine -> getDeleteCommand(inputLine));
        typesToCommands.put(CommandType.FIND, inputLine -> getFindCommand(inputLine));
        typesToCommands.put(CommandType.TODO, inputLine -> getTodoAddCommand(inputLine));
        typesToCommands.put(CommandType.DEADLINE, inputLine -> getDeadlineAddCommand(inputLine));
        typesToCommands.put(CommandType.EVENT, inputLine -> getEventAddCommand(inputLine));
        typesToCommands.put(CommandType.LIST, inputLine -> getListCommand());
        typesToCommands.put(CommandType.REMIND, inputLine -> getRemindCommand(inputLine));
        typesToCommands.put(CommandType.BYE, inputLine -> getByeCommand());
    }

    /**
     * Determines and returns the <code>CommandType</code> represented by the user input.
     *
     * @param inputLine Raw input string from the user.
     * @return Identified <code>CommandType</code>.
     * @throws ParserException If the command type is unknown, or if the input is invalid.
     */
    private static CommandType getCommandType(String inputLine) throws ParserException {
        CommandType chosenCommand;

        try {
            // attempt to convert the whole of the input to a command (i.e. LIST or BYE)
            chosenCommand = CommandType.valueOf(inputLine.toUpperCase());
        } catch (IllegalArgumentException notListOrByeEx) {
            // not LIST or BYE - get the first token and try to convert to a valid command
            try {
                String[] inputTokens = inputLine.split("\\s+");
                String inputCommand = inputTokens[0];
                chosenCommand = CommandType.valueOf(inputCommand.toUpperCase());
                if (chosenCommand.equals(CommandType.LIST) || chosenCommand.equals(CommandType.BYE)) {
                    throw new ParserException("LIST or BYE command was given with other arguments - invalid");
                }
            } catch (IllegalArgumentException notValidCommandEx) {
                throw new ParserException("I have no idea what that "
                        + "means. You could try:\n"
                        + Arrays.toString(CommandType.values()));
            }
        }

        return chosenCommand;
    }

    /**
     * Returns a <code>LocalDateTime</code> object based on the
     * date provided in <code>String</code>.
     *
     * @param dateString Date provided in <code>String</code>.
     * @return Date parsed.
     * @throws ParserException If date cannot be parsed.
     */
    static LocalDateTime parseDate(String dateString) throws ParserException {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException ex) {
            throw new ParserException("The date "
                    + "that you have provided cannot be parsed: "
                    + ex.getMessage()
                    + "\nPlease use the correct format for dates. For example: "
                    + DATE_FORMATTING_EXAMPLE);
        }
    }

    /**
     * Returns the command that is to be executed,
     * based on the user input provided.
     *
     * @param inputLine Input given by the user.
     * @return Command to be executed.
     * @throws ParserException If at least one of the following occurs:
     *                         <ul>
     *                         <li> Type of command in user input is unknown.
     *                         <li> Input arguments are invalid for the command type identified.
     *                         <li> Dates in the input cannot be parsed.
     *                         </ul>
     */
    public static Command parse(String inputLine) throws ParserException {
        setupMapping();

        // first remove the whitespace in the input
        inputLine = inputLine.strip();

        // identify what type of command is in the input
        CommandType chosenCommandType = getCommandType(inputLine);

        // create a class that can be used to carry out the operation,
        // based on the command type
        assert !typesToCommands.isEmpty() : "Mapping of command types to command functions cannot be empty";
        ThrowingFunction<String, Command> getCommandFunction = typesToCommands.get(chosenCommandType);
        return getCommandFunction.apply(inputLine);
    }
}
