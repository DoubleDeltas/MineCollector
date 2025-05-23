package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.MineCollector;
import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.version.Version;
import com.doubledeltas.minecollector.version.VersionSystem;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

import java.util.Map;
import java.util.stream.Collectors;

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

        YamlSerializer<Version<?>> versionSerializer = new ParsingSerializer<>(Version.Parser.INSTANCE);
        Map<Class<?>, YamlSerializer<?>> serializerMap = MineCollector.getInstance().getVersionManager()
                .getVersionSystemRegistry().stream()
                .collect(Collectors.toMap(
                        VersionSystem::getVersionClass,
                        vs -> new ParsingSerializer<>(vs.getParser())
                ));

        Constructor constructor = new CustomConstructor(McolConfig.class, loaderOptions)
                .registerSerializer(Version.class, versionSerializer)
                .registerSerializers(serializerMap);
        constructor.setPropertyUtils(new SpaceToCamelPropertyUtils());

        Representer representer = new CustomRepresenter(new DumperOptions())
                .registerSerializer(Version.class, versionSerializer)
                .registerSerializers(serializerMap);

        Yaml yaml = new Yaml(constructor, representer);
        yaml.setBeanAccess(BeanAccess.FIELD);

        return yaml;
    }
}
