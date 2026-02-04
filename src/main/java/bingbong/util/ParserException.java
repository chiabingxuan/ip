package bingbong.util;

/**
 * Represents an exception thrown when the parser cannot
 * parse an input successfully.
 */
public class ParserException extends BingBongException {
    /**
     * Initialises a new <code>ParserException</code>.
     *
     * @param message Error message to be shown in the exception.
     */
    public ParserException(String message) {
        super(message);
    }
}
