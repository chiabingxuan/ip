package bingbong;

import java.util.Optional;

import bingbong.command.Command;
import bingbong.util.MessageFormatter;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    // if a wait is needed, this is how long we wait for
    private static final int WAITING_TIME_SECONDS = 1;

    // list of controls
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    // instance of the chatbot
    private BingBong bot;

    // images to be used in the message display
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image bingBongImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the BingBong instance */
    @FXML
    public void setBingBong(BingBong b) {
        bot = b;

        // add the message from bot when it was loaded, if any.
        // show as a message bubble
        Optional<String> loadedMessage = b.getLoadedMessage();
        loadedMessage.ifPresent(msg -> dialogContainer.getChildren().add(
                DialogBox.getBingBongDialog(msg, bingBongImage)
        ));

        // add welcome message bubble
        dialogContainer.getChildren().add(
                DialogBox.getBingBongDialog(MessageFormatter.getOpeningMessage(), bingBongImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing BingBong's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bot.getResponse(input);

        // add user message bubble
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        // get parsed command. if unknown, this is empty
        Optional<Command> chosenCommand = bot.getAndResetChosenCommand();

        // if command is known, format the bubble according to command name.
        // otherwise, use normal formatting
        chosenCommand.map(command -> command.getClass().getSimpleName()) // get specific name of command
                .ifPresentOrElse(name -> dialogContainer.getChildren()
                .add(DialogBox.getBingBongResponseDialog(response, bingBongImage, name)), () ->
                        dialogContainer.getChildren().add(DialogBox.getBingBongDialog(response, bingBongImage)));

        // terminate app if bye command is issued
        chosenCommand.filter(command -> command.isExit())
                .ifPresent(name -> {
                    // wait for a while before closing the app
                    PauseTransition pause =
                            new PauseTransition(Duration.seconds(WAITING_TIME_SECONDS));
                    pause.setOnFinished(event -> Platform.exit());

                    // run the transition
                    pause.play();
                });

        userInput.clear();
    }
}
