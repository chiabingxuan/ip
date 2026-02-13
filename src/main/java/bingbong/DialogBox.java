package bingbong;

import java.io.IOException;
import java.util.Collections;

import bingbong.message.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    private void changeDialogStyle(String messageType) {
        switch (messageType) {
        case "SuccessMessage":
            dialog.getStyleClass().add("success-label");
            break;
        case "WarningMessage":
            dialog.getStyleClass().add("warning-label");
            break;
        case "ErrorMessage":
            dialog.getStyleClass().add("error-label");
            break;
        default:
            // do nothing
        }
    }

    /**
     * Returns a dialog box corresponding to the user's chosen input.
     *
     * @param text The user's input.
     * @param img The user icon to be placed beside his / her message bubble.
     * @return A dialog box with the selected input and user icon.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a dialog box corresponding to a message produced by the chatbot.
     *
     * @param msg The message from the chatbot.
     * @param img The chatbot icon to be placed beside its message bubble.
     * @return A dialog box with the selected text and chatbot icon.
     */
    public static DialogBox getBingBongDialog(Message msg, Image img) {
        String msgText = msg.getMsg();

        // get class name of message, which can be used to determine the message type
        String msgType = msg.getClass().getSimpleName();

        var db = new DialogBox(msgText, img);
        db.flip();
        db.changeDialogStyle(msgType);
        return db;
    }
}
