package bingbong.util;

/**
 * Represents an exception thrown when the chatbot cannot
 * function properly.
 */
public class BingBongException extends Exception {
    /**
     * Initialises a new <code>BingBongException</code>.
     *
     * @param message Error message to be shown in the exception.
     */
    public BingBongException(String message) {
        super(message);
    }
}
