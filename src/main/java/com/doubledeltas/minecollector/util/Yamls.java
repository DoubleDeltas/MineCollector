package com.doubledeltas.minecollector.util;

import com.doubledeltas.minecollector.config.McolConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

public final class Yamls {
    private static Yaml dataYaml;
    private static Yaml configYaml;

    private Yamls() {}

    public static Yaml getDataYaml() {
        if (dataYaml != null) return dataYaml;
        return dataYaml = createDataYaml();
    }

    public static Yaml getConfigYaml() {
        if (configYaml != null) return configYaml;
        return configYaml = createConfigYaml();
    }

    private static Yaml createDataYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setCanonical(false);
        dumperOptions.setExplicitStart(false);
        dumperOptions.setExplicitEnd(false);

        return new Yaml(dumperOptions);
    }

    private static Yaml createConfigYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setEnumCaseSensitive(false);

        Constructor constructor = new Constructor(McolConfig.class, loaderOptions);
        constructor.setPropertyUtils(new SpaceToCamelPropertyUtils());

        Yaml yaml = new Yaml(constructor);
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }

    private static class SpaceToCamelPropertyUtils extends PropertyUtils {
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
}
