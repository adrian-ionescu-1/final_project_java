package com.final_project_java.utils;

import lombok.Builder;
import org.springframework.http.HttpStatus;

//@Builder
public class ApiResponse {
    private int status;
    private String message;
    private Object data;

    private ApiResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public static ApiResponse success(String message, Object data) {
        return new Builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse error(int status, String message) {
        return new Builder()
                .status(status)
                .message(message)
                .build();
    }

    public static class Builder {
        private int status;
        private String message;
        private Object data;

        public Builder() {
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }
}
