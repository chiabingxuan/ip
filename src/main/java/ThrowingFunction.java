interface ThrowingFunction<T, R> {
    R apply(T t) throws BingBongException;
}