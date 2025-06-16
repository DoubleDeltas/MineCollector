package com.doubledeltas.minecollector.config;

public class InvalidConfigException extends RuntimeException {
    public InvalidConfigException() {
        super();
    }

    public InvalidConfigException(String msg) {
        super(msg);
    }

    public InvalidConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
