package bingbong.message;

/**
 * Represents the closing message produced by the chatbot,
 * right before the app is terminated.
 */
public class ByeMessage extends Message {
    /**
     * Initialises a <code>ByeMessage</code>.
     *
     * @param msg Text to be contained in the message.
     */
    public ByeMessage(String msg) {
        super(msg, true);
    }
}
