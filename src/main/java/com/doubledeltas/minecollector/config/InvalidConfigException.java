package com.doubledeltas.minecollector.config;

import java.util.List;

public class InvalidConfigException extends Exception {
    private List<String> causes;

    public InvalidConfigException(List<String> errorMessages) {
        this.causes = errorMessages;
    }

    public List<String> getCauses() {
        return causes;
    }
}
