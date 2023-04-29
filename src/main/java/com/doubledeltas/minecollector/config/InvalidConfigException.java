package com.doubledeltas.minecollector.config;

public class InvalidConfigException extends Exception {
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
