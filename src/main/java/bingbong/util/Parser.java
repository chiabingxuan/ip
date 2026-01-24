package bingbong.util;

import bingbong.command.Command;
import bingbong.command.CommandType;
import bingbong.command.AddCommand;
import bingbong.command.DeleteCommand;
import bingbong.command.ByeCommand;
import bingbong.command.ListCommand;
import bingbong.command.MarkCommand;
import bingbong.command.UnmarkCommand;
import bingbong.task.Deadline;
import bingbong.task.Event;
import bingbong.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {
    // for parsing of dates
    private static final String DATE_FORMAT = "d/M/yyyy HH:mm";

    // examples to be shown in error messages
    private static final String MARK_EXAMPLE = "\"mark 1\" to mark the first task as completed";
    private static final String UNMARK_EXAMPLE = "\"unmark 1\" to mark the first task as incomplete";
    private static final String DELETE_EXAMPLE = "\"delete 1\" to delete the first task";
    private static final String TODO_EXAMPLE = "\"todo go grocery shopping\"";
    private static final String DEADLINE_EXAMPLE = "\"deadline finish homework /by 9pm\"";
    private static final String EVENT_EXAMPLE = "\"event go for a jog /from 9am /to 10am\"";
    private static final String DATE_FORMATTING_EXAMPLE = "\"2/1/2003 13:18\" which means "
            + "2 Jan 2003, 1:18 pm";

    // maps different command types to a function that gives us the correct Command object
    private static final HashMap<CommandType,
            ThrowingFunction<String, Command>> typesToCommands = new HashMap<>();

    // init typesToCommands mapping
    // populate mapping of command types to the ThrowingFunction to be called
    private static void setupMapping() {
        // mark chosen task done
        typesToCommands.put(CommandType.MARK, inputLine -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToMark = Integer.parseInt(inputTokens[1]) - 1;
                return new MarkCommand(indexToMark);
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"mark\" command."
                        + "\nEg. "
                        + MARK_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"mark\" command."
                        + "\nEg. "
                        + MARK_EXAMPLE);
            }
        });

        // mark chosen task as not done
        typesToCommands.put(CommandType.UNMARK, inputLine -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToUnmark = Integer.parseInt(inputTokens[1]) - 1;
                return new UnmarkCommand(indexToUnmark);
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"unmark\" command."
                        + "\nEg. "
                        + UNMARK_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"unmark\" command."
                        + "\nEg. "
                        + UNMARK_EXAMPLE);
            }
        });

        // delete chosen task
        typesToCommands.put(CommandType.DELETE, inputLine -> {
            try {
                String[] inputTokens = inputLine.split("\\s+");
                int indexToDelete = Integer.parseInt(inputTokens[1]) - 1;
                return new DeleteCommand(indexToDelete);
            } catch (ArrayIndexOutOfBoundsException ex) {
                throw new BingBongException("Task number is missing. Make sure you have added a "
                        + "task number after the \"delete\" command."
                        + "\nEg. "
                        + DELETE_EXAMPLE);
            } catch (NumberFormatException ex) {
                throw new BingBongException("Task number is invalid. Make sure you have added the "
                        + "correct task number after the \"delete\" command."
                        + "\nEg. "
                        + DELETE_EXAMPLE);
            }
        });

        // add a todo
        typesToCommands.put(CommandType.TODO, inputLine -> {
            String[] detailsAfterSplit = inputLine.split("todo\\s+", 2);

            if (detailsAfterSplit.length < 2) {
                throw new BingBongException("The description of a todo cannot be empty. "
                        + "Add a task name after the \"todo\" command."
                        + "\nEg. "
                        + TODO_EXAMPLE);
            }

            String todoName = detailsAfterSplit[1];
            Todo newTodo = new Todo(todoName);

            return new AddCommand(newTodo, DATE_FORMAT);
        });

        // add a deadline
        typesToCommands.put(CommandType.DEADLINE, inputLine -> {
            String[] detailsAfterSplittingCommand = inputLine.split("deadline\\s+", 2);
            if (detailsAfterSplittingCommand.length < 2) {
                throw new BingBongException("The description of a deadline cannot be empty. "
                        + "Add a task name after the \"deadline\" command."
                        + "\nEg. "
                        + DEADLINE_EXAMPLE);
            }

            String[] deadlineDetails = detailsAfterSplittingCommand[1]
                    .split("\\s+/by\\s+", 2);
            if (deadlineDetails.length < 2) {
                throw new BingBongException("For deadlines, the \"/by\" delimiter "
                        + "must be placed between the task description and the chosen date."
                        + "\nEg. "
                        + DEADLINE_EXAMPLE);
            }

            String deadlineName = deadlineDetails[0];
            LocalDateTime byWhen = parseDate(deadlineDetails[1]);
            Deadline newDeadline = new Deadline(deadlineName, byWhen);

            return new AddCommand(newDeadline, DATE_FORMAT);
        });

        // add an event
        typesToCommands.put(CommandType.EVENT, inputLine -> {
            String[] detailsAfterSplittingCommand = inputLine.split("event\\s+", 2);
            if (detailsAfterSplittingCommand.length < 2) {
                throw new BingBongException("The description of an event cannot be empty. "
                        + "Add a task name after the \"event\" command."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            String[] detailsAfterSplittingFrom = detailsAfterSplittingCommand[1]
                    .split("\\s+/from\\s+", 2);
            if (detailsAfterSplittingFrom.length < 2) {
                throw new BingBongException("For events, the \"/from\" delimiter "
                        + "must be placed between the task description and the chosen start time."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            String eventName = detailsAfterSplittingFrom[0];
            String[] detailsAfterSplittingTo = detailsAfterSplittingFrom[1]
                    .split("\\s+/to\\s+", 2);
            if (detailsAfterSplittingTo.length < 2) {
                throw new BingBongException("For events, the \"/to\" delimiter "
                        + "must be placed between the chosen start time and the chosen end time."
                        + "\nEg. "
                        + EVENT_EXAMPLE);
            }

            LocalDateTime startTime = parseDate(detailsAfterSplittingTo[0]);
            LocalDateTime endTime = parseDate(detailsAfterSplittingTo[1]);
            Event newEvent = new Event(eventName, startTime, endTime);

            return new AddCommand(newEvent, DATE_FORMAT);
        });

        // list all tasks
        typesToCommands.put(CommandType.LIST, inputLine -> new ListCommand());

        // quit the app
        typesToCommands.put(CommandType.BYE, inputLine -> new ByeCommand());
    }

    // get the correct command from the input
    private static CommandType getCommandType(String inputLine) throws BingBongException {
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
                    throw new IllegalArgumentException("LIST or BYE command was given with other arguments - invalid");
                }
            } catch (IllegalArgumentException notValidCommandEx) {
                throw new BingBongException("I have no idea what that "
                        + "means. You could try:\n"
                        + Arrays.toString(CommandType.values()));
            }
        }

        return chosenCommand;
    }

    // parse dates that are in String
    static LocalDateTime parseDate(String dateString) throws BingBongException {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException ex) {
            throw new BingBongException("The date "
                    + "that you have provided cannot be parsed: "
                    + ex.getMessage()
                    + "\nPlease use the correct format for dates. For example: "
                    + DATE_FORMATTING_EXAMPLE);
        }

    }

    public static Command parse(String inputLine) throws BingBongException {
        setupMapping();

        // first identify what type of command is in the input
        CommandType chosenCommandType = getCommandType(inputLine);

        // create a class that can be used to carry out the operation,
        // based on the command type
        ThrowingFunction<String, Command> getCommandFunction = typesToCommands.get(chosenCommandType);
        return getCommandFunction.apply(inputLine);
    }
}
