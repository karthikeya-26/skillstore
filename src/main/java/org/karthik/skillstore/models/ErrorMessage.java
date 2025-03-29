package org.karthik.skillstore.models;

public class ErrorMessage {
    private String message;
    private Integer code;

    public ErrorMessage(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
