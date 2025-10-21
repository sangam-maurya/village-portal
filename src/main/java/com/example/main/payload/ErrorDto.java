package com.example.main.payload;

import java.time.LocalDateTime;
import java.util.Date;

public class ErrorDto {
    private String msg;
    private String description;
    private Date timestamp;

    public ErrorDto(String msg, String description, Date timestamp) {
        this.msg = msg;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
