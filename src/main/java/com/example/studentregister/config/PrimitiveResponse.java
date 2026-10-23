package com.example.studentregister.config;

import lombok.Data;

@Data
public class PrimitiveResponse {

    private boolean success;
    private String message;

    public PrimitiveResponse() {
    }

    public PrimitiveResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public PrimitiveResponse(Boolean aTrue) {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

