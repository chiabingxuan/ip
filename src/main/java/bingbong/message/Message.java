package bingbong.message;

public class Message {
    private final String msg;
    private boolean isTerminalMsg;

    public Message(String msg) {
        this.msg = msg;
        this.isTerminalMsg = false;
    }

    protected Message(String msg, boolean isTerminalMsg) {
        this.msg = msg;
        this.isTerminalMsg = isTerminalMsg;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isTerminalMsg() {
        return this.isTerminalMsg;
    }
}
