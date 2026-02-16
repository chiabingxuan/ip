package bingbong;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// used GPT-5.0 to improve existing JavaDoc comments, as well as
// add JavaDoc for non-public methods

/**
 * A GUI for BingBong using FXML.
 */
public class Main extends Application {
    private BingBong bot = new BingBong("./data", "tasks.txt");

    /**
     * Starts the JavaFX application.
     * This method initialises the primary stage, loads the main window layout
     * from the FXML file, injects the <code>BingBong</code> instance into the
     * controller, and displays the application window.
     *
     * @param stage The primary stage provided by the JavaFX runtime.
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("BingBong");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // inject the BingBong instance
            fxmlLoader.<MainWindow>getController().setBingBong(bot);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
