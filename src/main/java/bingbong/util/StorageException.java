package bingbong.util;

/**
 * Represents an exception thrown when the storage
 * cannot be read from or written to correctly.
 */
public class StorageException extends BingBongException {
    /**
     * Initialises a new <code>StorageException</code>.
     *
     * @param message Error message to be shown in the exception.
     */
    public StorageException(String message) {
        super(message);
    }
}
