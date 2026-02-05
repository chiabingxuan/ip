package bingbong.message;

/**
 * Represents a message produced by the chatbot
 * when something unexpected has occurred, but the app can still
 * function normally.
 */
public class WarningMessage extends Message {
    /**
     * Initialises a <code>WarningMessage</code>.
     *
     * @param msg Text to be contained in the message.
     */
    public WarningMessage(String msg) {
        super(msg);
    }
}
