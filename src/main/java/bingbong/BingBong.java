package bingbong;

import bingbong.command.Command;
import bingbong.util.BingBongException;
import bingbong.util.Parser;
import bingbong.util.Storage;
import bingbong.util.Ui;
import bingbong.task.TaskTracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BingBong {
    private Storage storage;
    private TaskTracker taskTracker;
    private final Ui ui;
    private boolean isInitSuccessful;

    public BingBong(String dataFolderPath, String tasksFilename) {
        ui = new Ui();

        try {
            storage = new Storage(dataFolderPath, tasksFilename);
            taskTracker = storage.loadSavedTasks();
            isInitSuccessful = true;
        } catch (FileNotFoundException ex) {
            ui.printWarning("No existing task file detected. "
                    + "An empty task list will be initialised.");
            taskTracker = new TaskTracker();
            isInitSuccessful = true;
        } catch (BingBongException ex) {
            ui.printExceptionMessage(ex.getMessage());
            taskTracker = new TaskTracker();
            isInitSuccessful = true;
        } catch (IOException ex) {
            ui.printExceptionMessage("Something went wrong when "
                    + "initialising task storage: "
                    + ex.getMessage()
                    + "\nPlease fix the problem and try again.");
            isInitSuccessful = false;
        }
    }

    public void run() {
        ui.showLine();
        ui.greet();
        ui.showLine();

        // main processing loop for input
        boolean isExit = false;
        Scanner sc = new Scanner(System.in);
        while (!isExit) {
            try {
                // get command requested by user
                String inputLine = sc.nextLine();
                ui.showLine();
                Command chosenCommand = Parser.parse(inputLine);
                taskTracker = chosenCommand.execute(taskTracker, ui, storage);
                isExit = chosenCommand.isExit();
            } catch (BingBongException ex) {
                ui.printExceptionMessage(ex.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        BingBong bot = new BingBong("./data", "tasks.txt");

        if (bot.isInitSuccessful) {
            bot.run();
        }
    }
}