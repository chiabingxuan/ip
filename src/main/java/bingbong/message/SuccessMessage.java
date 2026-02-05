package bingbong.message;

/**
 * Represents a message produced by the chatbot
 * when a command has been completed successfully.
 */
public class SuccessMessage extends Message {
    /**
     * Initialises a <code>SuccessMessage</code>.
     *
     * @param msg Text to be contained in the message.
     */
    public SuccessMessage(String msg) {
        super(msg);
    }
}
