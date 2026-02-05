package bingbong.message;

/**
 * Represents a message produced by the chatbot
 * (eg. when a command has been executed).
 */
public class Message {
    private final String msg;
    private final boolean isTerminalMsg;

    /**
     * Initialises a message.
     *
     * @param msg Text to be contained in the message.
     */
    public Message(String msg) {
        this.msg = msg;
        this.isTerminalMsg = false;
    }

    protected Message(String msg, boolean isTerminalMsg) {
        this.msg = msg;
        this.isTerminalMsg = isTerminalMsg;
    }

    /**
     * Returns the text within this message.
     *
     * @return The text contained in this message.
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * Returns whether this message should trigger
     * the termination of the app (ie. closing message of the chatbot).
     *
     * @return Whether this message should be the last message before closing the app.
     */
    public boolean isTerminalMsg() {
        return this.isTerminalMsg;
    }
}
