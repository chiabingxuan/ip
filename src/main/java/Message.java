class Message {
    protected static final String HORIZONTAL_LINE = "______________________________________";
    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return HORIZONTAL_LINE + "\n" + this.message + "\n" + HORIZONTAL_LINE;
    }
}
