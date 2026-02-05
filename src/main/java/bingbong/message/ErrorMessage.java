package bingbong.message;

/**
 * Represents a message produced by the chatbot
 * when something has gone wrong during the running of the app.
 */
public class ErrorMessage extends Message {
    /**
     * Initialises an <code>ErrorMessage</code>.
     *
     * @param msg Text to be contained in the message.
     */
    public ErrorMessage(String msg) {
        super(msg);
    }
}
