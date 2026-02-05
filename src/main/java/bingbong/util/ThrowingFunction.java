package bingbong.util;

/**
 * Represents a <code>Function</code> that can throw a <code>ParserException</code>.
 */
interface ThrowingFunction<T, R> {
    R apply(T t) throws ParserException;
}
