package bingbong.util;

/**
 * Represents a <code>Function</code> that can throw a <code>BingBongException</code>.
 */
interface ThrowingFunction<T, R> {
    R apply(T t) throws BingBongException;
}