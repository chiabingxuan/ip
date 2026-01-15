class GoodbyeMessage extends Message {
    GoodbyeMessage(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + Message.HORIZONTAL_LINE;
    }
}
