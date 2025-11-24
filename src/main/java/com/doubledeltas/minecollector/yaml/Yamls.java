package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.config.McolConfig;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.time.LocalDateTime;
import java.util.*;

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

        Constructor constructor = new CustomConstructor(loaderOptions);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setExplicitStart(false);
        dumperOptions.setExplicitEnd(false);

        Representer representer = new CustomRepresenter(dumperOptions);
        representer.setPropertyUtils(new DeclOrderingPropertyUtils());
        representer.getPropertyUtils().setBeanAccess(BeanAccess.FIELD);

        return new MultiVersionYaml(constructor, representer);
    }
}
