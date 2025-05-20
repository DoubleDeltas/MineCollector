package com.doubledeltas.minecollector.yaml;

import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

class SpaceToCamelPropertyUtils extends PropertyUtils {
    /**
     * @link PropertyUtils#getProperty(Class, String)}를 하이재킹하여 카멜 케이스를 인식시키도록 합니다.
     */
    @Override
    public Property getProperty(Class<? extends Object> type, String name, BeanAccess bAccess) {
        return super.getProperty(type, getCamelCased(name), bAccess);
    }

    private String getCamelCased(String spacedString) {
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