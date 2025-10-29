package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.crew.Crew;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

@UtilityClass
public final class Yamls {
    public static final Yaml DATA = createDataYaml();
    public static final MultiVersionYaml CONFIG = createConfigYaml();
    public static final MultiVersionYaml GENERAL = createGeneralYaml();

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

        Constructor constructor = new Constructor(McolConfig.class, loaderOptions);
        constructor.setPropertyUtils(new SpaceToCamelPropertyUtils());

        Representer representer = new Representer(new DumperOptions());

        MultiVersionYaml yaml = new MultiVersionYaml(constructor, representer);
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }

    private static MultiVersionYaml createGeneralYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        DumperOptions dumperOptions = new DumperOptions();
        Constructor constructor = new Constructor(Crew.class, loaderOptions);
        Representer representer = new Representer(dumperOptions);
        return new MultiVersionYaml(constructor, representer);
    }
}
