package com.doubledeltas.minecollector.crew;

public class DuplicatedIdException extends RuntimeException {
    public DuplicatedIdException() {
        super();
    }

    public DuplicatedIdException(String msg) {
        super(msg);
    }

    public DuplicatedIdException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
