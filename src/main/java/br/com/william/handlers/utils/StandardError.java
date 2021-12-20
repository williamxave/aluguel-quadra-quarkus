package br.com.william.handlers.utils;

public class StandardError {

    private Integer status;
    private String message;
    private long timeStamp;

    public StandardError(Integer status,
                         String message,
                         long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
