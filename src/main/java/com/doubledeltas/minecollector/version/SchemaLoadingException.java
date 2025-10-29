package com.doubledeltas.minecollector.version;

public class SchemaLoadingException extends RuntimeException {
    public SchemaLoadingException() {
        super();
    }

    public SchemaLoadingException(String msg) {
        super(msg);
    }

    public SchemaLoadingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
