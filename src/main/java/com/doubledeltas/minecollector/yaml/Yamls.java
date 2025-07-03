package com.doubledeltas.minecollector.yaml;

import com.doubledeltas.minecollector.config.McolConfig;
import com.doubledeltas.minecollector.crew.Crew;
import com.doubledeltas.minecollector.crew.CrewMemberProfile;
import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.representer.Representer;

import java.util.UUID;

public final class Yamls {
    @Getter
    private static final Yaml dataYaml = createDataYaml();
    @Getter
    private static final MultiVersionYaml configYaml = createConfigYaml();
    @Getter
    private static final Yaml crewYaml = createCrewYaml();

    private Yamls() {}


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

    private static Yaml createCrewYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();

        DumperOptions dumperOptions = new DumperOptions();

        Constructor constructor = new Constructor(Crew.class, loaderOptions);
        TypeDescription crewDescription = new TypeDescription(Crew.class);
        crewDescription.addPropertyParameters("memberProfileMap", UUID.class, CrewMemberProfile.class);
        constructor.addTypeDescription(crewDescription);
        constructor.addTypeDescription(new TypeDescription(CrewMemberProfile.class));

        Representer representer = new Representer(dumperOptions);

        return new Yaml(constructor, representer);
    }
}
