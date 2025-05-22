package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.config.McolConfigYaml;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

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

        Constructor constructor = new Constructor(McolConfigYaml.class, loaderOptions);
        constructor.setPropertyUtils(new SpaceToCamelPropertyUtils());

        Representer representer = new Representer(new DumperOptions());

        Yaml yaml = new Yaml(constructor, representer);
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }
}
