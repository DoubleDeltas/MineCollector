package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema;
import com.doubledeltas.minecollector.config.schema.McolConfigSchema1_3;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

public final class Yamls {
    private static Yaml dataYaml;
    private static MultiVersionYaml configYaml;

    private Yamls() {}

    public static Yaml getDataYaml() {
        if (dataYaml != null) return dataYaml;
        return dataYaml = createDataYaml();
    }

    public static MultiVersionYaml getConfigYaml() {
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

    private static MultiVersionYaml createConfigYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setEnumCaseSensitive(false);

        Constructor constructor = new SchematicConstructor(McolConfigSchema.getLatestDefault(), loaderOptions);
        constructor.setPropertyUtils(new SpaceToCamelPropertyUtils());

        Representer representer = new Representer(new DumperOptions());

        MultiVersionYaml yaml = new MultiVersionYaml(constructor, representer);
        yaml.setBeanAccess(BeanAccess.PROPERTY);

        return yaml;
    }
}
