package bingbong;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import bingbong.message.ErrorMessage;
import bingbong.message.Message;
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
    private static final int WAITING_TIME_SECONDS = 3;

    // image paths to be used in the message display
    private static final String USER_IMAGE_PATH = "/images/user.png";
    private static final String BINGBONG_IMAGE_PATH = "/images/robot.png";

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

    private Image getImage(String path) {
        InputStream imageStream = this.getClass().getResourceAsStream(path);
        assert imageStream != null : "Image stream cannot be null";
        return new Image(imageStream);
    }

    /**
     * Closes the application.
     */
    @FXML
    void terminate() {
        // wait for a while before closing the app
        PauseTransition pause = new PauseTransition(Duration.seconds(WAITING_TIME_SECONDS));
        pause.setOnFinished(event -> Platform.exit());

        // run the transition
        pause.play();
    }

    /**
     * Injects the <code>BingBong</code> instance.
     */
    @FXML
    public void setBingBong(BingBong b) {
        bot = b;

        // add the message from bot when it was loaded, if any.
        // show as a message bubble
        Optional<Message> loadedMessage = b.getLoadedMessage();
        loadedMessage.ifPresent(msg -> dialogContainer.getChildren().add(
                DialogBox.getBingBongDialog(msg, this.getImage(BINGBONG_IMAGE_PATH))
        ));

        // check whether loaded message is an error message
        loadedMessage.filter(msg -> msg instanceof ErrorMessage)
                // if there is error message, situation is fatal.
                // need to terminate the app
                .ifPresentOrElse(msg -> this.terminate(), () -> {
                    // add welcome message bubble if there is no error message
                    Message welcomeMessage = new Message(MessageFormatter.getOpeningMessage());
                    dialogContainer.getChildren()
                            .add(DialogBox.getBingBongDialog(welcomeMessage, this.getImage(BINGBONG_IMAGE_PATH)));
                });
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * <code>BingBong</code>'s reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        List<Message> responses = bot.getResponses(input);

        // add user message bubble
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, this.getImage(USER_IMAGE_PATH)));

        // for each bot response, add and format the bubble
        // according to the corresponding type of message produced by the bot
        responses.forEach(msg -> {
            DialogBox formattedDb = DialogBox.getBingBongDialog(msg, this.getImage(BINGBONG_IMAGE_PATH));
            dialogContainer.getChildren().add(formattedDb);
        });

        // terminate app if the last message from the bot is a bye message
        Message lastMessage = responses.get(responses.size() - 1);
        if (lastMessage.isTerminalMsg()) {
            this.terminate();
        }

        // clear all text in the input box
        userInput.clear();

        // autoscroll downwards after the new bubbles have been added
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}
