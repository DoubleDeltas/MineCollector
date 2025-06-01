package com.doubledeltas.minecollector.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringUtil {
    public String getCamelCased(String spacedString) {
        String[] words = spacedString.split("\\s+");
        if (words.length < 2)
            return spacedString;

        StringBuilder builder = new StringBuilder();
        builder.append(words[0]);
        for (int i=1; i<words.length; i++) {
            builder.append(Character.toUpperCase(words[i].charAt(0)));
            builder.append(words[i].substring(1));
        }
        return builder.toString();
    }
}
